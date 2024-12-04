package com.example.mymovies.presntation.HomePage

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymovies.presntation.HomePage.uiComponents.HomeTopBar
import com.example.mymovies.presntation.HomePage.uiComponents.MovieList
import com.example.mymovies.presntation.HomePage.uiComponents.SortMenu


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onItemNav: (String) -> Unit,
    homeStatesAndEvents: HomeScreenStateAndEvents,
) {
    val moviesData = homeStatesAndEvents.uiState.dataList?.collectAsLazyPagingItems()

    // State for the Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Collect error state from the ViewModel's SharedFlow
    LaunchedEffect(key1 = homeStatesAndEvents.uiState.errorObserver) {
        homeStatesAndEvents.uiState.errorObserver.collect { error ->
            errorMessage.value = error
        }
    }

    // Handle movie data load state
    LaunchedEffect(key1 = moviesData?.loadState) {
        if (moviesData?.loadState?.refresh is LoadState.Error) {
            homeStatesAndEvents.onSortingFail()
            val errorMessage = (moviesData.loadState.refresh as LoadState.Error).error.message
            // Emit error state to show snackbar
            homeStatesAndEvents.onError("Error: $errorMessage")
        }
    }

    // Scaffold to manage the top bar, sorting menu, and snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            // Box for layout with padding and scrollable content
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // TopAppBar (fixed at the top)
                    HomeTopBar(
                        user = homeStatesAndEvents.uiState.userState,
                        onLoginClick = {homeStatesAndEvents.onLogIn(it)}
                    )

                    // Sorting Menu (fixed at the top)
                    SortMenu(
                        selectedOption = homeStatesAndEvents.uiState.sortingOption,
                        onSortSelected = { homeStatesAndEvents.onSorting(it) }
                    )

                    // Movie List (scrollable below the top bar)
                    if (moviesData != null) {
                        MovieList(moviesData = moviesData, onItemNav = onItemNav,errorMe = homeStatesAndEvents.uiState.errorObserver)
                    }
                }
            }
        }
    )

    // Show snackbar when an error message is emitted
    LaunchedEffect(errorMessage.value) {
        errorMessage.value?.let {
            snackbarHostState.showSnackbar(it)
            errorMessage.value = null // Reset after showing
        }
    }
}






