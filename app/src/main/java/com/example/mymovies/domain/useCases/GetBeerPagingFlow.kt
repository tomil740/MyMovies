package com.example.mymovies.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymovies.data.local.MovieEntity
import com.example.mymovies.data.mapers.toMovie
import com.example.mymovies.domain.models.MovieModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetMoviePagingFlow (private val beerPager:Pager<Int, MovieEntity>) {
    operator fun invoke(): Flow<PagingData<MovieModule>> {
        return beerPager.flow
            .map { pagingData ->
                pagingData.map { it.toMovie() }
            }

    }
}

