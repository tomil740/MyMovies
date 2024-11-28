package com.example.mymovies.presntation.HomePage

sealed class HomeEvents {
    data class OnSorting(val sortId:Int) : HomeEvents()
}