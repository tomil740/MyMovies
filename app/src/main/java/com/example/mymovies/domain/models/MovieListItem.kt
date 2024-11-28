package com.example.mymovies.domain.models

data class MovieListItem(
    val page: Int,
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Float,
    val releaseDate:String,
    val posterImg : String
)
