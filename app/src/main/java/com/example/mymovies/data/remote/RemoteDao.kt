package com.example.mymovies.data.remote

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.mymovies.data.remote.dtoModels.AccountDto
import com.example.mymovies.data.remote.dtoModels.AllMoviesIdDto
import com.example.mymovies.data.remote.dtoModels.IsFavoriteDto
import com.google.gson.Gson
import com.example.mymovies.data.remote.dtoModels.ResponseDto
import com.example.mymovies.data.util.ApiConstants
import com.example.mymovies.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject

/*
    The RemoteDao will supply all of our needs from the remote API , in practice:
    * make HTTP requests + handle the responses
    * fetch the data into matched kotlin data object
    * return the result ...
*/
class RemoteDao(private val okHttpClient: OkHttpClient) {

    private val myApiKey = ApiConstants.API_KEY
    private val baseUrl = ApiConstants.BASE_URL

    /**
     * Creates a new request token to authenticate a user.
     * @return The request token if successful, otherwise null.
     */
    suspend fun createRequestToken(): String? {
        val url = "$baseUrl/authentication/token/new?api_key=$myApiKey"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        return try {
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)
                    jsonResponse.getString("request_token")
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Creates a session ID using the provided request token.
     * @param requestToken The request token to create the session ID.
     * @return The session ID if successful, otherwise null.
     */
    fun createSessionId(requestToken: String): String? {
        val requestBody = JSONObject().apply {
            put("request_token", requestToken)
        }.toString()

        val request = Request.Builder()
            .url("$baseUrl/authentication/session/new?api_key=$myApiKey")
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody))
            .build()

        return try {
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)
                    jsonResponse.getString("session_id")
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Fetches user account data using the provided session ID.
     * @param sessionId The session ID to retrieve user data.
     * @return A result containing the user data if successful, or an error.
     */
    suspend fun getUserData(sessionId: String): Result<AccountDto> {
        val url = "$baseUrl/account?api_key=$myApiKey&session_id=$sessionId"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        return try {
            val response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {
                parseJson<AccountDto>(response)
            } else {
                Result.Error(Exception("API call failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Redirects the user to the authentication URL using the request token.
     * @param requestToken The request token to use in the URL.
     * @param context The context to start the authentication intent.
     */
    fun redirectToAuthorization(requestToken: String, context: Context) {
        val authUrl = "https://www.themoviedb.org/authenticate/$requestToken?redirect_to=mymovies://callback"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * Fetches a list of popular movies for the given page.
     * @param page The page number to fetch movies from.
     * @return A result containing the popular movies if successful, or an error.
     */
    suspend fun getPopularMovies(page: Int): Result<ResponseDto> {
        return fetchData<Result<ResponseDto>>("movie/popular", page)
    }

    /**
     * Fetches a list of currently broadcast movies for the given page.
     * @param page The page number to fetch movies from.
     * @return A result containing the currently broadcast movies if successful, or an error.
     */
    suspend fun getCurrentlyBroadcastMovies(page: Int): Result<ResponseDto> {
        return fetchData<Result<ResponseDto>>("movie/now_playing", page)
    }

    /**
     * Fetches a list of favorite movies for the specified account and page.
     * @param page The page number to fetch movies from.
     * @param accountKey The session ID for the user.
     * @param accountId The account ID for the user.
     * @return A result containing the favorite movies if successful, or an error.
     */
    suspend fun getAccountFavoritesMovies(page: Int, accountKey: String, accountId: Int = 21654105): Result<ResponseDto> {
        return fetchData<Result<ResponseDto>>("account/$accountId/favorite/movies", page, loginEndPoint = "&session_id=$accountKey")
    }

    /**
     * Sets the favorite status of a movie for the specified account.
     * @param accountId The account ID.
     * @param accountKey The session ID for the user.
     * @param itemId The movie ID.
     * @param status The favorite status to set.
     * @return A result indicating success or failure.
     */
    suspend fun setFavoriteStateById(accountId: Int, accountKey: String, itemId: Int, status: Boolean): Result<Boolean> {
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, """
            {
                "media_type": "movie",
                "media_id": $itemId, 
                "favorite": $status
            } 
        """.trimIndent())

        val request = Request.Builder()
            .url("$baseUrl/account/$accountId/favorite?api_key=$myApiKey&session_id=$accountKey")
            .post(body)
            .addHeader("content-type", "application/json")
            .build()

        return try {
            val response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }

            if (response.isSuccessful) {
                val json = response.body?.string() ?: ""
                val gson = Gson()
                val isFavoriteRes = gson.fromJson(json, IsFavoriteDto::class.java)
                Result.Success(isFavoriteRes.success)
            } else {
                Result.Error(Exception("API call failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Fetches a list of favorite movie IDs for the specified account and page.
     * @param accountId The account ID.
     * @param accountKey The session ID for the user.
     * @param page The page number to fetch movie IDs from.
     * @return A result containing the list of favorite movie IDs.
     */
    suspend fun getAccountAllFavoritesMoviesIds(accountId: Int, accountKey: String, page: Int): Result<List<Int>> {
        val result = fetchData<AllMoviesIdDto>("account/$accountId/favorite/movies", page, loginEndPoint = "&session_id=$accountKey")
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.results.map { it.id })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }

    /**
     * Helper function to fetch data from the API.
     * @param endpoint The API endpoint to request.
     * @param page The page number for pagination.
     * @param loginEndPoint Additional login-specific parameters, such as session ID.
     * @return A result containing the response data if successful, or an error.
     */
    private suspend fun <T> fetchData(endpoint: String, page: Int, loginEndPoint: String = ""): Result<ResponseDto> {
        val url = "$baseUrl/$endpoint?api_key=$myApiKey&language=en-US&page=$page$loginEndPoint"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        return try {
            withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        parseJson<ResponseDto>(response)
                    } else {
                        Result.Error(Exception("API call failed"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Helper function to parse the JSON response into a Kotlin object.
     * @param response The HTTP response to parse.
     * @return A result containing the parsed data or an error.
     */
    private inline fun <reified T> parseJson(response: Response): Result<T> {
        return try {
            val json = response.body?.string() ?: ""
            val gson = Gson()
            val parsedResponse = gson.fromJson(json, T::class.java)
            Result.Success(parsedResponse)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}





