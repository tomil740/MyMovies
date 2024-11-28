package com.example.mymovies.presntation.MovieItemPage

import com.example.mymovies.presntation.MovieItemPage.MovieItemUIState

data class MovieItemStateAndEvents(
    val uiState: MovieItemUIState,
    val onFavorite : ()->Unit,
    val onNavBack : ()->Unit,
)