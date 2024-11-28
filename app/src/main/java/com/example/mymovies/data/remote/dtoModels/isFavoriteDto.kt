package com.example.mymovies.data.remote.dtoModels

data class IsFavoriteDto(
    val media_id: Int,
    val favorite: Boolean = false 
)
