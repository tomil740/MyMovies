package com.example.mymovies.presntation.HomePage

import androidx.paging.PagingData
import com.example.mymovies.domain.models.MovieListItem
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val userName:String,
    val sortingOption: Int,
    val dataList: Flow<PagingData<MovieListItem>>?
)