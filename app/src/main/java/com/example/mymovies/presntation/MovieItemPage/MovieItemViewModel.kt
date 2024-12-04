package com.example.mymovies.presntation.MovieItemPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.models.MovieModule
import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.useCases.GetItemFavoriteState
import com.example.mymovies.domain.useCases.OnLoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieItemViewModel@Inject constructor(
    private val moviesRepo : MoviesRepository,
    private val getItemFavoriteState: GetItemFavoriteState,
    private val  onLoginEvent: OnLoginEvent
):ViewModel() {

    private val _errorState = MutableSharedFlow<String>(replay = 1)
    val errorState: SharedFlow<String> get() = _errorState

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        MovieItemUIState(favoriteStatus = false,
            movieModuleItem = MovieModule(-1, ",njkbk","2fddsf",8f,
                "dsfdsf","",""),
            userState = UserStateUi(-1,"liam",false,""),
            errorObserver = errorState
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)



    init {
        viewModelScope.launch {
            moviesRepo.getAuthenticationStateFlow().collectLatest { authState ->
                val theState = onLoginEvent.invoke(authState)
                _uiState.update { it.copy(userState = theState) }
            }
        }
    }


    fun onEvent(event: MovieItemEvents) {
        when (event) {
            MovieItemEvents.OnFavorite -> {
                viewModelScope.launch {
                    if (uiState.value.userState.id!=-1) {
                        val setFavStatues = moviesRepo.setFavorItemStatuesById(
                            uiState.value.movieModuleItem.id,
                            (!uiState.value.favoriteStatus),
                            accountId = uiState.value.userState.id,
                            accountKey = uiState.value.userState.key
                        )
                        when (setFavStatues) {
                            is Result.Success -> {
                                _uiState.update { it.copy(favoriteStatus = (!uiState.value.favoriteStatus)) }
                            }

                            is Result.Error -> {
                                //define some UI evetn to notice the user the process has been fail
                            }
                        }
                    }else{
                        _errorState.emit("Cant track favorites,User isnt login")
                    }
                }
            }
            MovieItemEvents.OnNavBack -> TODO()
            is MovieItemEvents.InitItem -> {
                //get the item from our local db
                viewModelScope.launch {
                    val theObj = moviesRepo.getMovieById(event.id.toInt())
                    val favoriteStatus = getItemFavoriteState(event.id.toInt(), account = uiState.value.userState)
                    _uiState.update { it.copy(movieModuleItem = theObj ,favoriteStatus=favoriteStatus) }
                }

            }

        }
    }
}











