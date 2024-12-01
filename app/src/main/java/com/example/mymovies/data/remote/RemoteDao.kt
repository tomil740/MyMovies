package com.example.mymovies.data.remote

import android.util.Log
import com.example.mymovies.data.remote.dtoModels.AllMoviesIdDto
import com.example.mymovies.data.remote.dtoModels.IsFavoriteDto
import com.google.gson.Gson
import com.example.mymovies.data.remote.dtoModels.ResponseDto
import com.example.mymovies.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

/*
    The RemoteDao will supply all of our needs from the remote API , in practice:
    * make HTTP requests + handle the responses
    * fetch the data into matched kotlin data object
    * return the result ...
*/
class RemoteDao(private val okHttpClient: OkHttpClient) {

    private val apiKey = "YOUR_API_KEY" // Replace with your actual API key
    private val baseUrl = "https://api.themoviedb.org/3"

    // General network request function that returns Result<ResponseDto>
    private suspend fun <T> fetchData(endpoint: String, page: Int): Result<ResponseDto> {
        val request = Request.Builder()
            .url("$baseUrl/$endpoint?language=en-US&page=$page&api_key=$apiKey")
            .get()
            .build()

        return try {
            val response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {
                parseJson<ResponseDto>(response)
            } else {
                val errorResponse = response.body?.string() ?: "Unknown error"
                Log.e("RemoteDao", "API error: $errorResponse")
                Result.Error(Exception("API call failed with code: ${response.code}, $errorResponse"))
            }
        } catch (e: Exception) {
            Log.e("RemoteDao", "Error fetching data", e)
            Result.Error(e)
        }
    }

    // Parse JSON response and map to Result
    private inline fun <reified T> parseJson(response: Response): Result<T> {
        return try {
            val json = response.body?.string() ?: ""
            val gson = Gson()
            val parsedResponse = gson.fromJson(json, T::class.java)
            Result.Success(parsedResponse)
        } catch (e: Exception) {
            Log.e("RemoteDao", "Error parsing response", e)
            Result.Error(e)
        }
    }

    // Get popular movies
    suspend fun getPopularMovies(page: Int): Result<ResponseDto> {
        return fetchData<Any>("movie/popular", page)
    }

    // Get currently playing movies
    suspend fun getCurrentlyBroadcastMovies(page: Int): Result<ResponseDto> {
        return fetchData<Any>("movie/now_playing", page) // Use parameters as required
    }

    // Get favorite movies for an account
    suspend fun getAccountFavoritesMovies(page: Int): Result<ResponseDto> {
        return fetchData<Any>("account/21654105/favorite/movies", page)
    }

    // Set movie as favorite
    suspend fun setFavoriteStateById(itemId: Int, status: Boolean): Result<Boolean> {
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, """
            {
                "media_type": "movie",
                "media_id": $itemId, 
                "favorite": $status
            }
        """.trimIndent())

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/account/21654105/favorite")  // Use correct account ID
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("api_key", apiKey)
            .build()

        return try {
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }

            if (response.isSuccessful) {
                val json = response.body?.string() ?: ""
                val gson = Gson()
                val isFavoriteRes = gson.fromJson(json, IsFavoriteDto::class.java)
                Result.Success(isFavoriteRes.success)
            } else {
                val errorResponse = response.body?.string() ?: "Unknown error"
                Log.e("RemoteDao", "Error setting favorite: $errorResponse")
                Result.Error(Exception("API call failed with code: ${response.code}, $errorResponse"))
            }
        } catch (e: Exception) {
            Log.e("RemoteDao", "Error setting favorite", e)
            Result.Error(e)
        }
    }

    suspend fun getAccountAllFavoritesMoviesIds(accountId: Int): Result<List<Int>> {
        val result = fetchData<AllMoviesIdDto>("account/$accountId/favorite/movies", 1)
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.results.map { it.id })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }
}



