package com.example.mymovies.domain.models

data class MovieModule(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Float,
    val releaseDate:String,
    val mainImgUrl : String,
    val backgroundImgUrl: String
)
