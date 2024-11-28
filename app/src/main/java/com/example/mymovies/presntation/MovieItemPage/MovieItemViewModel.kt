package com.example.mymovies.presntation.MovieItemPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.useCases.GetItemFavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieItemViewModel@Inject constructor(
    private val moviesRepo : MoviesRepository,
    private val getItemFavoriteState: GetItemFavoriteState
):ViewModel() {

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        MovieItemUIState(favoriteStatus = false,
            movieModuleItem = MovieModule(-1, ",njkbk","2fddsf",8f,"dsfdsf","","")
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    fun onEvent(event: MovieItemEvents) {
        when (event) {
            MovieItemEvents.OnFavorite -> {
                viewModelScope.launch {
                    val setFavStatues = moviesRepo.setFavorItemStatuesById(uiState.value.movieModuleItem.id,(!uiState.value.favoriteStatus))
                    when(setFavStatues){
                        is Result.Success->{
                            Log.i("AAA3","updated to ${(!uiState.value.favoriteStatus)}")
                           _uiState.update { it.copy(favoriteStatus = (!uiState.value.favoriteStatus)) }
                        }

                        is Result.Error->{
                            //define some UI evetn to notice the user the process has been fail
                        }
                    }
                }
            }
            MovieItemEvents.OnNavBack -> TODO()
            is MovieItemEvents.InitItem -> {
                //get the item from our local db
                viewModelScope.launch {
                    val theObj = moviesRepo.getMovieById(event.id.toInt())
                    val favoriteStatus = getItemFavoriteState(event.id.toInt())
                    _uiState.update { it.copy(movieModuleItem = theObj ,favoriteStatus=favoriteStatus) }
                }

            }

        }
    }
}











