package com.example.mymovies.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "sorting_movie_mapping",
    primaryKeys = ["sortingId", "movieId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sortingId"), Index("movieId")]
)
data class SortingMovieMappingEntity(
    val sortingId: Int, // Represents the sorting method (e.g., favorites, popular)
    val movieId: Int    // References the movie's primary key
)
