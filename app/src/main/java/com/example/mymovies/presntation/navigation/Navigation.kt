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

    //set the nav controller for this app navigation
    val navController = rememberNavController()

    fun NavHostController.navigateSingleTopTo(route: String) {
        if (route == "home") {
            this.navigate("home") {
                launchSingleTop = true
            }
        } else {
            this.navigate("itemPage")
        }
    }
    fun NavHostController.navigateSingleTopToItem(itemId: String = "-1") {

            this.navigate("itemPage/${itemId}")

    }


    //set the navHost which will be the current route to present to the user
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            val viewModel = hiltViewModel<HomeViewmodel>()
            val uiState = viewModel.uiState.collectAsState()
            val beersStateAndEvent = HomeScreenStateAndEvents(uiState = uiState.value,
                onSorting = { viewModel.onEvent(HomeEvents.OnSorting(it)) },
                onSortingFail = {viewModel.onEvent(HomeEvents.OnSortError)},
                navToItem = { navController.navigateSingleTopToItem(it) }
            )
            HomeScreen(homeStatesAndEvents = beersStateAndEvent, onItemNav = {navController.navigateSingleTopToItem(it)})
        }

        composable(
            route = "itemPage/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })

        ) {
            val theArg = it.arguments?.getString(("itemId")) ?: "-1"
            val viewModel = hiltViewModel<MovieItemViewModel>()
            //todo check if the value is beset practice....
            viewModel.onEvent(MovieItemEvents.InitItem(theArg))
            val uiState = viewModel.uiState.collectAsState().value
            val movieItemStateAndEvents = MovieItemStateAndEvents(uiState = uiState, onFavorite = {viewModel.onEvent(MovieItemEvents.OnFavorite)},
                onNavBack = {navController.navigateSingleTopTo("home")})

            MovieItemScreen(movieItemStateAndEvents = movieItemStateAndEvents)


        }

    }
}