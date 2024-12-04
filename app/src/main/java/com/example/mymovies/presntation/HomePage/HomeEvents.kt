package com.example.mymovies.presntation.HomePage

sealed class HomeEvents {
    data class OnSorting(val sortId:Int) : HomeEvents()
    data object OnSortError:HomeEvents()
    data class OnError(val error:String):HomeEvents()
    data class OnLogin(val toCancel:Boolean = false):HomeEvents()
}