package com.example.mymovies.presntation.HomePage.uiComponents

import MovieItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.mymovies.domain.models.MovieListItem
import kotlinx.coroutines.launch

@Composable
fun MovieList(
    moviesData: LazyPagingItems<MovieListItem>?,
    onItemNav: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    var focusedIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()


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
}