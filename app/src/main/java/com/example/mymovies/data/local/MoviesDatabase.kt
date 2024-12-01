package com.example.mymovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymovies.data.local.MovieDao
import com.example.mymovies.data.local.MovieEntity

@Database(
    entities = [MovieEntity::class,SortingMovieMappingEntity::class],
    version = 7
)
abstract class MoviesDatabase: RoomDatabase() {

    abstract val dao: MovieDao
}