package com.example.mymovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface MovieDao {

    // Insert or update all movies, replacing existing ones if necessary
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    // Fetch a movie by its ID
    @Query("SELECT * FROM movieentity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    // Insert sorting mappings (associating movies with a sorting ID)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSortingMappings(mappings: List<SortingMovieMappingEntity>)

    // Clear the sorting mappings for a specific sorting ID (use to refresh data on sorting change)
    @Query("""
        DELETE FROM sorting_movie_mapping 
        WHERE sortingId = :sortingId
    """)
    suspend fun clearMappingsForSortingId(sortingId: Int)

    // Fetch movies based on a specific sorting ID, joining with sorting_movie_mapping table
    @Query("""
        SELECT * FROM movieentity 
        INNER JOIN sorting_movie_mapping 
        ON movieentity.id = sorting_movie_mapping.movieId
        WHERE sorting_movie_mapping.sortingId = :sortingId
        ORDER BY movieentity.releaseDate DESC
    """)
    fun getMoviesForSorting(sortingId: Int): List<MovieEntity>

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()

    // Delete old movies and their related sorting mappings
    @Transaction
    suspend fun deleteOldMoviesAndMappings(expiryTime: Long) {
        // Delete old movies
        deleteOldMovies(expiryTime)
        // Delete related sorting mappings
        deleteRelatedMappings(expiryTime)
    }

    @Query("""
        DELETE FROM movieentity 
        WHERE fetchedAt < :expiryTime
    """)
    suspend fun deleteOldMovies(expiryTime: Long)

    @Query("""
        DELETE FROM sorting_movie_mapping
        WHERE movieId IN (SELECT id FROM movieentity WHERE fetchedAt < :expiryTime)
    """)
    suspend fun deleteRelatedMappings(expiryTime: Long)
}

