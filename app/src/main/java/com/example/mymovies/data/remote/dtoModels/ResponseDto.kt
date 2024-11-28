package com.example.mymovies.data.remote.dtoModels

import com.example.mymovies.data.remote.dtoModels.MovieDto


data class ResponseDto(
    val page: Int,
    val results: List<MovieDto>,
)
