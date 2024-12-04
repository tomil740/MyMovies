package com.example.mymovies.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.mymovies.data.local.MoviesDatabase
import com.example.mymovies.data.local.MovieEntity
import com.example.mymovies.data.local.SortingMovieMappingEntity
import com.example.mymovies.data.mapers.toMovieEntity
import com.example.mymovies.data.remote.dtoModels.ResponseDto
import com.example.mymovies.domain.util.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.net.SocketTimeoutException

class MoviesPagingSource(
    private val apiFun: suspend (Int) -> Result<ResponseDto>, // API function
    private val sortingId: Int, // Sorting identifier
    private val movieDao: MoviesDatabase, // DAO for local DB operations
    private val onErrorFlow: MutableSharedFlow<String>, // SharedFlow to emit error messages
    private val apiOnFavoriteFun:suspend (Int,String,Int)->Result<ResponseDto>,
    private val accountKey:String,
    private val accountId:Int,
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val currentPage = params.key ?: 1
        var apiFailed = false
        var apiSucceeded = false

        try {
            // Attempt to fetch data from the API
            val apiResponse =  if(sortingId != 0){
                apiFun.invoke(currentPage)
            }else {
                apiOnFavoriteFun.invoke(currentPage,accountKey,accountId)
            }

            if (apiResponse is Result.Success) {
                val results = apiResponse.data.results
                apiSucceeded = true

                // Store the fetched data in the local DB
                movieDao.withTransaction {
                    val movies = results.map { it.toMovieEntity(apiResponse.data.page) }
                    movieDao.dao.upsertAll(movies)

                    val mappings = results.map {
                        SortingMovieMappingEntity(sortingId = sortingId, movieId = it.id)
                    }
                    movieDao.dao.insertSortingMappings(mappings)
                }

                // Prepare the data to be displayed in the UI
                val movies = results.map { it.toMovieEntity(apiResponse.data.page) }
                val isEndOfPagination = results.isEmpty() || apiResponse.data.page >= apiResponse.data.total_pages

                // Return the paginated data
                return LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (isEndOfPagination) null else currentPage + 1
                )
            } else if (apiResponse is Result.Error) {
                apiFailed = true
                throw apiResponse.exception
            }
        } catch (e: SocketTimeoutException) {
            // Specific handling for timeout errors
            apiFailed = true
            onErrorFlow.emit("Timeout error. Please check your connection and retry.")
        } catch (e: Exception) {
            // Generic error handling
            apiFailed = true
            onErrorFlow.emit("API request failed: ${e.message}")
        }

        // Fallback to local data if the API call fails
        val movies = movieDao.withTransaction { movieDao.dao.getMoviesForSorting(sortingId) }

        // Check if we've reached the end of local data
        val isLastLocalPage = movies.size < params.loadSize

        if (apiFailed) {
            if (isLastLocalPage) {
                // Emit error message that no connection is available and end of cached data reached
                onErrorFlow.emit("No internet connection and you've reached the end of cached data.")
            } else {
                // Emit error message for API failure
                onErrorFlow.emit("API request failed, please try again.")
            }
        }

        return if (movies.isNotEmpty()) {
            // Return the local cached data if available
            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (isLastLocalPage) null else currentPage + 1
            )
        } else {
            // If no local data available and API failed
            LoadResult.Error(Exception("No data available"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

