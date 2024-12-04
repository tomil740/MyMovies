package com.example.mymovies.presntation.HomePage

import androidx.paging.PagingData
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.util.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

data class HomeUiState(
    val userState: UserStateUi,
    val sortingOption: Int,
    val errorObserver: SharedFlow<String>,
    val dataList: Flow<PagingData<MovieListItem>>?
)