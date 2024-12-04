package com.example.mymovies.domain.repository

import androidx.paging.PagingData
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.util.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface MoviesRepository {

     suspend fun getMoviePaging(sortingId:Int,accountKey:String,accoundId:Int,errorObserver: MutableSharedFlow<String>):  Flow<PagingData<MovieListItem>>

     suspend fun getMovieById(id:Int): MovieModule

     suspend fun setFavorItemStatuesById(id: Int, statues:Boolean,accountKey:String,accountId:Int): Result<Boolean>

     suspend fun getAllFavoriteItemsId(accountKey:String, accountId:Int, page:Int): Result<List<Int>>

     suspend fun initializeAuthentication(): Result<String>

     suspend fun authenticationCallBack(requestToken: String, ): Result<AuthState>

     suspend fun getAuthenticationStateFlow():Flow<AuthState>

     suspend fun setAuthenticationStateFlow(theState:AuthState)

     suspend fun getAccountObj(accountKey: String):Result<UserStateUi>

}