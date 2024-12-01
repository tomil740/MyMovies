package com.example.mymovies.presntation.HomePage

import androidx.paging.PagingData
import com.example.mymovies.domain.models.MovieListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

data class HomeUiState(
    val userName:String,
    val sortingOption: Int,
    val errorObserver: SharedFlow<String>,
    val dataList: Flow<PagingData<MovieListItem>>?
)