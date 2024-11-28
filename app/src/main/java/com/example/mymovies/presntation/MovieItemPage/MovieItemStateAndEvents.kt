package com.plcoding.composepaging3caching.presentation.movieItemPage

import com.example.mymovies.presntation.MovieItemPage.MovieItemUIState

data class MovieItemStateAndEvents(
    val uiState: MovieItemUIState,
    val onFavorite : ()->Unit,
    val onNavBack : ()->Unit,
)