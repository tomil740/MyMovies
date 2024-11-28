package com.example.mymovies.domain.repository

import androidx.paging.PagingData
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.MovieModule
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

     suspend fun getMoviePaging(sortingId:Int):  Flow<PagingData<MovieListItem>>

     suspend fun getMovieById(id:Int): MovieModule

     suspend fun setFavorItemStatuesById(id: Int, statues:Boolean): Result<Boolean>

     suspend fun getAllFavoriteItemsId(): Result<List<Int>>


}