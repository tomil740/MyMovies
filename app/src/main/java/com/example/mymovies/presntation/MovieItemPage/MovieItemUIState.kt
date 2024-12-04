package com.example.mymovies.presntation.MovieItemPage

import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.models.UserStateUi
import kotlinx.coroutines.flow.SharedFlow

data class MovieItemUIState(
    val movieModuleItem : MovieModule,
    val favoriteStatus : Boolean,
    val userState: UserStateUi,
    val errorObserver: SharedFlow<String>,
    )
