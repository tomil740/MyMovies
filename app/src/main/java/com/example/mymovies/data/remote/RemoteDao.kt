package com.example.mymovies.data.remote

import android.util.Log
import com.google.gson.Gson
import com.example.mymovies.data.remote.dtoModels.ResponseDto
import com.example.mymovies.data.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
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
                Log.i("AAA","the json resposnse ${json}")
                val gson = Gson()
                val movieResponse = gson.fromJson(json, ResponseDto::class.java)
                Log.i("AAA","the page ${movieResponse.page} the first item name ${movieResponse.results.size}")
                Result.Success(movieResponse)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.i("AAA","httprequest error$e")
            Result.Error(e)
        }

    }

    suspend  fun getCurrentlyBroadcastMovies(page:Int = 1,  perPage:Int=2): Result<ResponseDto> {

        // Construct the URL with the appropriate query parameters
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=${page}&per_page=${perPage}")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NzJmZTY5MTk3MWUyNjMyOTJhMTJmZjQ1NDllNWVhMSIsIm5iZiI6MTczMjY0MjMwNS4zNjc1MjU4LCJzdWIiOiI2NzQ2MDRiNzA4MDUyYjk2MGMwNDFmZmYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.DPBGAGP1rl9DgMaewKnyphe9xx8H7pAcyHNukblhFQ8")
            .build()

        return try {
            val response: Response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            if (response.isSuccessful) {

                val json = response.body?.string() ?: ""
                Log.i("AAA2","the json resposnse ${json}")
                val gson = Gson()
                val movieResponse = gson.fromJson(json, ResponseDto::class.java)
                Log.i("AAA2","the page ${movieResponse.page} the first item name ${movieResponse.results.size}")
                Result.Success(movieResponse)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.i("AAA2","httprequest error$e")
            Result.Error(e)
        }

    }



}