package com.plcoding.composepaging3caching.presentation.movieItemPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.presntation.MovieItemPage.MovieItemEvents
import com.example.mymovies.presntation.MovieItemPage.MovieItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieItemViewModel@Inject constructor(
    private val moviesRepo : MoviesRepository
):ViewModel() {

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        MovieItemUIState(
            movieModuleItem = MovieModule(-1, ",njkbk","2fddsf",8f,"dsfdsf","","")
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    fun onEvent(event: MovieItemEvents){
        when(event){
            MovieItemEvents.OnFavorite -> TODO()
            MovieItemEvents.OnNavBack -> TODO()
            is MovieItemEvents.InitItem -> {
                //get the item from our local db
                viewModelScope.launch {
                     val theObj = moviesRepo.getMovieById(event.id.toInt())
                     _uiState.update { it.copy(movieModuleItem = theObj)
                    }
                }
            }
        }
    }

}