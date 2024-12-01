package com.example.mymovies.data.mapers

import com.example.mymovies.data.local.MovieEntity
import com.example.mymovies.data.remote.dtoModels.MovieDto
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.MovieModule

fun MovieDto.toMovieEntity(pageIndex:Int): MovieEntity {
    return MovieEntity(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        genreIds = this.genre_ids.joinToString("-"), // List<Int>
        id = this.id,
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path,
        releaseDate = this.release_date,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        page = pageIndex,
        fetchedAt = System.currentTimeMillis() // Set the current time
    )
}

fun MovieEntity.toMovie(): MovieModule {
    return MovieModule(
        id = id,
        title = title,
        overview = overview,
        backgroundImgUrl = if(backdropPath!=null){"https://image.tmdb.org/t/p/w600_and_h900_bestv2/${backdropPath}"}else{ "https://dubaitickets.tours/wp-content/uploads/2023/03/img-worlds-of-adventure-dubai-ticket-9-1.jpg"},
        mainImgUrl = if(posterPath!=null){"https://image.tmdb.org/t/p/w600_and_h900_bestv2/${posterPath}"}else{ "https://dubaitickets.tours/wp-content/uploads/2023/03/img-worlds-of-adventure-dubai-ticket-9-1.jpg"},
        releaseDate = releaseDate,
        voteAverage = voteAverage.toFloat()
    )
}

fun MovieEntity.toMovieListItem(): MovieListItem {
    return MovieListItem(
        id = id,
        title = title,
        overview = overview,
        page = page,
        posterImg = if(posterPath!=null){"https://image.tmdb.org/t/p/w600_and_h900_bestv2/${posterPath}"}else{ "https://dubaitickets.tours/wp-content/uploads/2023/03/img-worlds-of-adventure-dubai-ticket-9-1.jpg"},
        releaseDate = releaseDate,
        voteAverage = voteAverage.toFloat()
    )
}