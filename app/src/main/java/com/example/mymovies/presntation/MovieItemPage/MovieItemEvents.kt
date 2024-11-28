package com.example.mymovies.presntation.MovieItemPage


sealed class MovieItemEvents {
    object OnFavorite : MovieItemEvents()
    object OnNavBack : MovieItemEvents()
    data class InitItem(val id:String) : MovieItemEvents()
}