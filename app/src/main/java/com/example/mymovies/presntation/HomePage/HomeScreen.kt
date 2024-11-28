package com.example.mymovies.presntation.HomePage

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymovies.presntation.HomePage.uiComponents.HomeTopBar
import com.example.mymovies.presntation.HomePage.uiComponents.MovieList
import com.example.mymovies.presntation.HomePage.uiComponents.SortMenu


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





