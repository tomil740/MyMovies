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

class MoviesPagingSource(
    private val apiFun: suspend (Int) -> Result<ResponseDto>, // API function for fetching movies (expects a page number)
    private val sortingId: Int, // Sorting identifier (used to associate the movies with a specific sorting type)
    private val movieDao: MoviesDatabase, // DAO to interact with the local database
) : PagingSource<Int, MovieEntity>() {

    // This method is responsible for loading data for each page requested
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val currentPage = params.key ?: 1
        return try {
            // Threshold for data expiration (e.g., 7 days ago)
            val EXPIRY_THRESHOLD = 7L * 24 * 60 * 60 * 1000 // 7 days in milliseconds
            // Clean old data before loading new data
            movieDao.dao.deleteOldMoviesAndMappings(EXPIRY_THRESHOLD)

            // Call the API function to fetch data
            val apiResponse = apiFun.invoke(currentPage)

            when (apiResponse) {
                is Result.Success -> {
                    val results = apiResponse.data.results

                    // Cache the fetched data into the local database
                    movieDao.withTransaction {
                        val movies = results.map { it.toMovieEntity(apiResponse.data.page) }
                        movieDao.dao.upsertAll(movies)

                        // Insert the sorting mappings to associate movies with the sortingId
                        val mappings = results.map {
                            SortingMovieMappingEntity(sortingId = sortingId, movieId = it.id)
                        }
                        movieDao.dao.insertSortingMappings(mappings)
                    }

                    val movies = results.map { it.toMovieEntity(apiResponse.data.page) }

                    // Check if we've reached the end of the pagination
                    val isEndOfPagination = results.isEmpty() || apiResponse.data.page >= apiResponse.data.total_pages

                    // Return the paginated result
                    LoadResult.Page(
                        data = movies,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (isEndOfPagination) null else currentPage + 1
                    )
                }

                is Result.Error -> {
                    Log.i("error" ,"no connection ${apiResponse.exception}")
                    movieDao.withTransaction {
                        // If API fails, load from local database
                        val movies = movieDao.dao.getMoviesForSorting(sortingId)
                        if (movies.isNotEmpty()) {
                            LoadResult.Page(
                                data = movies,
                                prevKey = null,
                                nextKey = null
                            )
                        } else {
                            LoadResult.Error(apiResponse.exception)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.i("error" ,"no connection and local db $e")
            movieDao.withTransaction {
                // Handle exceptions (e.g., network errors) and fallback to local data
                val movies = movieDao.dao.getMoviesForSorting(sortingId)
                if (movies.isNotEmpty()) {
                    LoadResult.Page(
                        data = movies,
                        prevKey = null,
                        nextKey = null
                    )
                } else {
                    Log.i("error" ,"no connection and local db error2 $e")
                    LoadResult.Error(e)
                }
            }
        }
    }

    // Provide a refresh key to determine where to start when refreshing the data
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
