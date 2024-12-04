package com.example.mymovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.compose.MoviesTheme
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.presntation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repo: MoviesRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Navigation()
                }
            }
        }
    }
    override fun onNewIntent(intent: Intent) {

        super.onNewIntent(intent)
        intent?.data?.let { uri ->

            if (uri.scheme == "mymovies" && uri.host == "callback") {

                val requestToken = uri.getQueryParameter("request_token")
                if (requestToken != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        repo.authenticationCallBack(requestToken = requestToken)

                    }
                    } else {
                        // Handle error, no request token found
                    }

            }
        }
    }


}