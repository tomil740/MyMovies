package com.example.mymovies.presntation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymovies.presntation.HomePage.HomeEvents
import com.example.mymovies.presntation.HomePage.HomeScreen
import com.example.mymovies.presntation.HomePage.HomeScreenStateAndEvents
import com.example.mymovies.presntation.HomePage.HomeViewmodel
import com.example.mymovies.presntation.MovieItemPage.MovieItemEvents
import com.example.mymovies.presntation.MovieItemPage.MovieItemScreen
import com.example.mymovies.presntation.MovieItemPage.MovieItemStateAndEvents
import com.example.mymovies.presntation.MovieItemPage.MovieItemViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()

    // Utility function for handling "singleTop" navigation (to avoid duplicate screens in back stack)
    fun NavHostController.navigateSingleTop(route: String) {
        this.navigate(route) {
            launchSingleTop = true
            restoreState = true // Keeps the state if navigating to the same screen
            popUpTo(route) { inclusive = true } // Prevents multiple instances in back stack
        }
    }

    // Utility function for handling item detail navigation
    fun NavHostController.navigateToItem(itemId: String) {
        this.navigate("itemPage/$itemId")
    }

    // Start NavHost
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Screen
        composable(route = "home") {
            val viewModel = hiltViewModel<HomeViewmodel>()
            val uiState = viewModel.uiState.collectAsState()
            val homeScreenStateAndEvents = HomeScreenStateAndEvents(
                uiState = uiState.value,
                onSorting = { viewModel.onEvent(HomeEvents.OnSorting(it)) },
                onSortingFail = { viewModel.onEvent(HomeEvents.OnSortError) },
                navToItem = { navController.navigateToItem(it) },
                onError = {viewModel.onEvent(HomeEvents.OnError(it))}
            )
            HomeScreen(homeStatesAndEvents = homeScreenStateAndEvents, onItemNav = { navController.navigateToItem(it) })
        }

        // Movie Item Page (Details Page)
        composable(
            route = "itemPage/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "-1"
            val viewModel = hiltViewModel<MovieItemViewModel>()
            viewModel.onEvent(MovieItemEvents.InitItem(itemId))

            val uiState = viewModel.uiState.collectAsState().value
            val movieItemStateAndEvents = MovieItemStateAndEvents(
                uiState = uiState,
                onFavorite = { viewModel.onEvent(MovieItemEvents.OnFavorite) },
                onNavBack = { navController.navigateSingleTop("home") } // Back to home
            )

            MovieItemScreen(movieItemStateAndEvents = movieItemStateAndEvents)
        }
    }
}
