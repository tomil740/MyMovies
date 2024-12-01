package com.example.mymovies.presntation.HomePage.uiComponents

import MovieItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.mymovies.domain.models.MovieListItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun MovieList(
    moviesData: LazyPagingItems<MovieListItem>?,
    onItemNav: (String) -> Unit,
    errorMe : SharedFlow<String>
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    var focusedIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Observe error flow
    LaunchedEffect(Unit) {
        errorMe.collect { message ->
            errorMessage = message
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Display loading spinner during refresh or append
        if (moviesData?.loadState?.refresh is LoadState.Loading ||
            moviesData?.loadState?.append is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }

        // Display LazyColumn for movie list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown) {
                        when (event.key) {
                            Key.DirectionDown -> {
                                if (focusedIndex < (moviesData?.itemCount ?: 1) - 1) {
                                    focusedIndex++
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(focusedIndex)
                                    }
                                }
                                true
                            }
                            Key.DirectionUp -> {
                                if (focusedIndex > 0) {
                                    focusedIndex--
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(focusedIndex)
                                    }
                                }
                                true
                            }
                            Key.Enter -> {
                                moviesData?.get(focusedIndex)?.let { onItemNav(it.id.toString()) }
                                true
                            }
                            Key.Escape -> {
                                focusManager.clearFocus()
                                true
                            }
                            else -> false
                        }
                    } else false
                },
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (moviesData != null) {
                itemsIndexed(moviesData) { index, movie ->
                    if (movie != null) {
                        val isFocused = focusedIndex == index
                        MovieItem(
                            movie = movie,
                            onFavoriteClick = { onItemNav(movie.id.toString()) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isFocused) Color.LightGray else Color.Transparent)
                                .focusable()
                                .clickable { onItemNav(movie.id.toString()) }
                        )
                    }
                }
            }
        }

        // Show retry button if there's an error
        errorMessage?.let { error ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = error, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { moviesData?.retry() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}
