package com.example.mymovies.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymovies.data.local.MoviesDatabase
import com.example.mymovies.data.local.factory.AuthDataStore
import com.example.mymovies.data.mapers.toMovie
import com.example.mymovies.data.mapers.toMovieListItem
import com.example.mymovies.data.mapers.toUserStateUi
import com.example.mymovies.data.remote.MoviesPagingSource
import com.example.mymovies.data.remote.RemoteDao
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.models.MovieListItem
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.util.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient


class MoviesRepositoryImpl(private val moviesDb: MoviesDatabase, private val remoteDao: RemoteDao,private val appContext: Context,private val authDataStore: AuthDataStore):
    MoviesRepository {

    override suspend fun getAuthenticationStateFlow(): Flow<AuthState> {
       return authDataStore.getAuthStateFlow()
    }

    override suspend fun setAuthenticationStateFlow(theState: AuthState) {
        authDataStore.setAuthStateFlow(theState)
    }

    override suspend fun getAccountObj(accountKey: String):Result<UserStateUi> {

        val a = remoteDao.getUserData(accountKey)

        return  when(a){
            is Result.Success-> {
                Result.Success(a.data.toUserStateUi(accountKey))
            }
            is Result.Error -> {
                Result.Error(a.exception)
            }
        }
    }


    override suspend fun initializeAuthentication(): Result<String> {
        val apiKey = "YOUR_API_KEY" // Replace with your actual API key
        return try {
            // Step 1: Create a request token
            val requestToken = remoteDao.createRequestToken()
                ?: return Result.Error(Exception("Failed to create request token"))

            // Step 2: Redirect the user for authentication
            remoteDao.redirectToAuthorization(requestToken, appContext)

            // Return the request token for later use
            Result.Success(requestToken)
        } catch (e: Exception) {
            // Handle any unexpected exceptions
            Log.e("Authentication", "Error initializing authentication: ${e.message}", e)
            Result.Error(e)
        }
    }

    override suspend fun authenticationCallBack(
        requestToken: String,
    ): Result<AuthState> {
        val remoteDao = RemoteDao(OkHttpClient())

        return try {
            // Step 3: Create a session ID
            val sessionId = remoteDao.createSessionId(requestToken)
                ?: return Result.Error(Exception("Failed to create session ID"))

            // Step 4: Fetch user data
            val userData = remoteDao.getUserData(sessionId)
                ?: return Result.Error(Exception("Failed to retrieve user data"))

            Log.i("Error","fetchedUserData ${userData.toString()}")
            setAuthenticationStateFlow(AuthState.Authenticated(sessionId))
            // Return success with user authentication state
            Result.Success(AuthState.Authenticated(sessionId))
        } catch (e: Exception) {
            // Handle any unexpected exceptions
            Log.e("Authentication", "Error completing authentication: ${e.message}", e)
            Result.Error(e)
        }
    }

    /*
     getBeerPaging is a little on the edge for repo function but , its make sense because we must use
     the data sources in the paging mangement lib for maxmimum effecency so there is now way around it.
     */
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMoviePaging(sortingId: Int,accountKey:String,accountId:Int,errorObserver:MutableSharedFlow<String>): Flow<PagingData<MovieListItem>> {
        // Get the corresponding API function based on the sortingId
        val apiFun = when (sortingId) {
            1 -> remoteDao::getPopularMovies
            2 -> remoteDao::getCurrentlyBroadcastMovies
            else -> remoteDao::getPopularMovies
        }

        val a = remoteDao::getAccountFavoritesMovies


        // Create and return the Pager
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                // Use the MoviesPagingSource with the selected API function and sortingId
                MoviesPagingSource(apiFun= apiFun, sortingId= sortingId, movieDao = moviesDb, onErrorFlow = errorObserver,accountKey=accountKey
                    , apiOnFavoriteFun = a, accountId = accountId)
            }
        )
            .flow
            .map { pagingData ->
                // Transform the MovieEntity to your UI model
                pagingData.map { it.toMovieListItem() }
            }
    }

    override suspend fun getMovieById(id: Int): MovieModule {
        return moviesDb.dao.getMovieById(id).toMovie()
    }

    override suspend fun setFavorItemStatuesById(id: Int, statues:Boolean,accountKey: String,accountId: Int): Result<Boolean> {
        return remoteDao.setFavoriteStateById(itemId = id, status = statues, accountId = accountId, accountKey = accountKey)
    }

    override suspend fun getAllFavoriteItemsId(accountKey:String, accountId:Int, page:Int): Result<List<Int>> {
        return remoteDao.getAccountAllFavoritesMoviesIds(accountKey =accountKey , accountId = accountId, page = page)
    }






}

