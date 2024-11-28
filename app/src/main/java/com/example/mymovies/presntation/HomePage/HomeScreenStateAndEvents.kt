package com.example.mymovies.presntation.HomePage

data class HomeScreenStateAndEvents (
    val uiState: HomeUiState,
    val onSorting:(Int)->Unit,
    val navToItem:(String)->Unit
)

