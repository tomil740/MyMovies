package com.example.mymovies.data.remote.factory

import com.example.mymovies.data.util.ApiKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

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

        val retryInterceptor = Interceptor { chain ->
            var attempt = 0
            val maxRetries = 3
            var response: okhttp3.Response? = null
            var exception: IOException? = null

            while (attempt < maxRetries) {
                try {
                    response = chain.proceed(chain.request())
                    if (response.isSuccessful) {
                        return@Interceptor response
                    }
                } catch (e: IOException) {
                    exception = e
                }
                attempt++
            }

            // If retries are exhausted, throw the last encountered exception
            throw exception ?: IOException("Failed after $maxRetries retries")
        }
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(15, TimeUnit.SECONDS)   // Read timeout
            .writeTimeout(15, TimeUnit.SECONDS)  // Write timeout
            .addInterceptor(loggingInterceptor)
            .addInterceptor(retryInterceptor)
            .addInterceptor(authInterceptor) // Add the auth interceptor here
            .build()
    }
}