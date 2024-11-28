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
    //per page query just dosnt work...
     suspend  fun getPopularMovies(page:Int = 1,  perPage:Int=2): Result<ResponseDto> {

    // Construct the URL with the appropriate query parameters
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/popular?language=en-US&page=${page}&per_page=${perPage}")
            .get()
            .build()

        return try {
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {

                val json = response.body?.string() ?: ""
              val gson = Gson()
                val movieResponse = gson.fromJson(json, ResponseDto::class.java)
                Result.Success(movieResponse)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    suspend  fun getCurrentlyBroadcastMovies(page:Int = 1,  perPage:Int=2): Result<ResponseDto> {

        // Construct the URL with the appropriate query parameters
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=${page}&per_page=${perPage}")
            .get()
            .build()

        return try {
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {

                val json = response.body?.string() ?: ""
                val gson = Gson()
                val movieResponse = gson.fromJson(json, ResponseDto::class.java)
                Result.Success(movieResponse)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    suspend fun getAccountFavoritesMovies(page:Int = 1,  perPage:Int=2): Result<ResponseDto> {

        // Construct the URL with the appropriate query parameters
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/account/21654105/favorite/movies?language=en-US&${page}=1&sort_by=created_at.asc")
            .get()
            .build()

        return try {

            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {

                val json = response.body?.string() ?: ""
                val gson = Gson()
                val movieResponse = gson.fromJson(json, ResponseDto::class.java)
                Result.Success(movieResponse)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun setFavoriteStateById(itemId:Int,status:Boolean): Result<Boolean> {
        Log.i("AAA3","myFavo staers")

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, """
            {
                "media_type": "movie",
                "media_id": ${itemId}, 
                "favorite": $status
            }
        """.trimIndent())

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/account/21654105/favorite")  // Add API key and session ID to the URL
            .post(body)
            .addHeader("content-type", "application/json")
            .build()

        return try {
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            Log.i("AAA3","myFavo tries")

            if (response.isSuccessful) {

                val json = response.body?.string() ?: ""
                Log.i("AAA3","the json resposnse ${json}")
                val gson = Gson()
                val isFavoriteRes = gson.fromJson(json, IsFavoriteDto::class.java)
                Result.Success(true)
            } else {
                Log.i("AAA3","Api call fail with ${response.code}")
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.i("AAA3","httprequest error$e")
            Result.Error(e)
        }
    }

    suspend fun getAccountAllFavoritesMoviesIds(): Result<List<Int>> {

        // Construct the URL with the appropriate query parameters
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/account/21654105/favorite/movies?language=en-US&page=1&sort_by=created_at.asc")
            .get()
            .build()

        return try {
            Log.i("AAA4","Try")
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {
                val json = response.body?.string() ?: ""
                Log.i("AAA4","good,${json}")
                val gson = Gson()
                val movieResponse = gson.fromJson(json, AllMoviesIdDto::class.java)
                Result.Success(movieResponse.results.map { it.id })
            } else {
                Log.i("AAA4","fail ${response.code}")

                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.i("AAA4","fail ${e}")
            Result.Error(e)
        }
    }



}