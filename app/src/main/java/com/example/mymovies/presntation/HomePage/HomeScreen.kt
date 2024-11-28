package com.example.mymovies.presntation.HomePage

import MovieItem
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.mymovies.domain.models.MovieListItem
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    onItemNav: (String) -> Unit,
    homeStatesAndEvents: HomeScreenStateAndEvents
) {
    val context = LocalContext.current
    val moviesData = homeStatesAndEvents.uiState.dataList?.collectAsLazyPagingItems()

    // Handle error states
    LaunchedEffect(key1 = moviesData?.loadState) {
        if (moviesData?.loadState?.refresh is LoadState.Error) {
            homeStatesAndEvents.onSortingFail()
            Toast.makeText(
                context,
                "Error: ${(moviesData.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Column for the top bar, sorting menu, and LazyColumn for the movie list
        Column(modifier = Modifier.fillMaxSize()) {
            // TopAppBar (fixed at the top)
            HomeTopBar(
                userName = homeStatesAndEvents.uiState.userName,
                isLoggedIn = homeStatesAndEvents.uiState.userName.isNotEmpty(),
                onLoginClick = { /* Handle login action */ }
            )

            // Sorting Menu (fixed at the top)
            SortMenu(
                selectedOption = homeStatesAndEvents.uiState.sortingOption,
                onSortSelected = { homeStatesAndEvents.onSorting(it) }
            )

            // Movie List (scrollable independently below the top bar)
            MovieList(moviesData = moviesData, onItemNav = onItemNav)
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    userName: String,
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = if (isLoggedIn) "Hello, $userName" else "Hey guest, please log in",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            if (!isLoggedIn) {
                TextButton(onClick = onLoginClick) {
                    Text("Login", style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,  // Transparent to use gradient background
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
    )
}

@Composable
fun SortMenu(
    selectedOption: Int,
    onSortSelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val sortOptions = listOf("My Favorites", "Popular", "Currently Broadcast")
    var focusedIndex by remember { mutableIntStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.DirectionRight -> {
                            if (focusedIndex < sortOptions.lastIndex) {
                                focusedIndex++
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value + 100)
                                }
                            }
                            true
                        }
                        Key.DirectionLeft -> {
                            if (focusedIndex > 0) {
                                focusedIndex--
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value - 100)
                                }
                            }
                            true
                        }
                        Key.Enter -> {
                            onSortSelected(focusedIndex)
                            true
                        }
                        else -> false
                    }
                } else false
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sortOptions.forEachIndexed { index, option ->
            ElevatedButton(
                onClick = { onSortSelected(index) },
                modifier = if (index == focusedIndex){Modifier.padding(4.dp).background(Color.Red)}else{Modifier.padding(4.dp)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (index == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (index == selectedOption) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
