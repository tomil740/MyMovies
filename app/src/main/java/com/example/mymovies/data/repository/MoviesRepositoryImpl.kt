package com.example.mymovies.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymovies.data.local.MoviesDatabase
import com.example.mymovies.data.mapers.toMovie
import com.example.mymovies.data.mapers.toMovieListItem
import com.example.mymovies.data.remote.BeerRemoteMediator
import com.example.mymovies.data.remote.RemoteDao
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class MoviesRepositoryImpl(private val moviesDb: MoviesDatabase, private val remoteDao: RemoteDao):
    MoviesRepository {

    /*
     getBeerPaging is a little on the edge for repo function but , its make sense because we must use
     the data sources in the paging mangement lib for maxmimum effecency so there is now way around it.
     */
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMoviePaging(sortingId:Int): Flow<PagingData<MovieListItem>> {


        //get the matched sorting method from the remote dao...Todo
        Log.i("AA","work withj ${sortingId}")
        val apiFun =when(sortingId){
            2->remoteDao::getPopularMovies
            else -> {remoteDao::getCurrentlyBroadcastMovies}
        }

        return Pager(
            config = PagingConfig(pageSize = sortingId),
            remoteMediator = BeerRemoteMediator(
                movieDb = moviesDb,
                apiFun = apiFun
            ),
            pagingSourceFactory = {
                //need to be from anthoer palce(in gneereal not vm job)should cahnge query accoridng to the id
                moviesDb.dao.pagingSource()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toMovieListItem() }
            }
    }

    override suspend fun getMovieById(id: Int): MovieModule {
        return moviesDb.dao.getMovieById(id).toMovie()
    }
}

