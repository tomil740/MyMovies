package com.example.mymovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    val page:Int,
    val fetchedAt: Long, // Timestamp indicating when the data was fetched
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: String?,
    @PrimaryKey
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
