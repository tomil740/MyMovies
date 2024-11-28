package com.example.mymovies.presntation.MovieItemPage

import com.example.mymovies.domain.models.MovieModule

data class MovieItemUIState(
    val movieModuleItem : MovieModule,
    val favoriteStatus : Boolean
)
