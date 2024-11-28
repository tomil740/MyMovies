package com.example.mymovies.data.remote

import android.util.Log
import com.example.mymovies.data.remote.dtoModels.IsFavoriteDto
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

    suspend fun getFavoriteStatuesById(itemId:Int): Result<Boolean>{
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$itemId/favorite")
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
                val isFavoriteRes = gson.fromJson(json, IsFavoriteDto::class.java)
                Result.Success(isFavoriteRes.favorite)
            } else {
                Result.Error(Exception("API call failed with code: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.i("AAA2","httprequest error$e")
            Result.Error(e)
        }
    }



}