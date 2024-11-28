package com.example.mymovies.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.mymovies.data.local.MoviesDatabase
import com.example.mymovies.data.local.MovieEntity
import com.example.mymovies.data.mapers.toMovieEntity
import com.example.mymovies.data.remote.dtoModels.ResponseDto
import com.example.mymovies.domain.util.Result
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val movieDb: MoviesDatabase,
    private val apiFun:  suspend (Int, Int) -> Result<ResponseDto>
): RemoteMediator<Int, MovieEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                // TODO: need to be improved...
                LoadType.APPEND -> {
                    //dose note work
                    val lastItem = state.lastItemOrNull()

                    if(lastItem == null) {
                        1
                    } else {
                        lastItem.page+1
                    }
                }
            }

            val apiResponse =
               apiFun.invoke(
                loadKey,
                state.config.pageSize
            )

            when(apiResponse){
                is Result.Error -> {
                    MediatorResult.Error(apiResponse.exception)
                }
                is Result.Success -> {
                    movieDb.withTransaction {
                        if(loadType == LoadType.REFRESH) {
                            movieDb.dao.clearAll()
                        }
                        //map the resultes to db entity
                        Log.i("working","${apiResponse.data.page} and the name of ${apiResponse.data.results}")
                       val movieEntities = apiResponse.data.results.map { it.toMovieEntity(apiResponse.data.page) }
                        movieDb.dao.upsertAll(movieEntities)
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = apiResponse.data.results.isEmpty()
                    )
                }
            }

        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}