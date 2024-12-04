package com.example.mymovies.presntation.HomePage


data class HomeScreenStateAndEvents (
    val uiState: HomeUiState,
    val onSorting:(Int)->Unit,
    val onError:(String)->Unit,
    val onSortingFail:()->Unit,
    val navToItem:(String)->Unit,
    val onLogIn:(Boolean)->Unit
)

