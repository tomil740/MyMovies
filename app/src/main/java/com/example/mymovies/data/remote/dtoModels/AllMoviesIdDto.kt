package com.example.mymovies.data.remote.dtoModels

data class AllMoviesIdDto(
    val results: List<MovieIdDto>,
    )
data class MovieIdDto(
    val id: Int
)