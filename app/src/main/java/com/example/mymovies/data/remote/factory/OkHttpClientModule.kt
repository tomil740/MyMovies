package com.example.mymovies.data.remote.factory

import com.example.mymovies.data.util.ApiKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientModule {

    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mySshKey = ApiKeys.sshKey
        if (mySshKey.isEmpty()) {
            throw IllegalStateException("SSH key is not set. Please configure it in ApiKeys.")
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $mySshKey") // Replace YOUR_TOKEN_HERE with your token
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor) // Add the auth interceptor here
            .build()
    }
}