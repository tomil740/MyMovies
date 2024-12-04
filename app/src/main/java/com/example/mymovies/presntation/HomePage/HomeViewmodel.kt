package com.example.mymovies.presntation.HomePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.useCases.OnLoginEvent
import com.example.mymovies.domain.util.AuthState
import com.example.mymovies.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel@Inject constructor(
    private val movieRepo : MoviesRepository,
    private val  onLoginEvent: OnLoginEvent
) :ViewModel(){

    private var prevSortId:Int = 1

    private val _errorState = MutableSharedFlow<String>(replay = 1)
    val errorState: SharedFlow<String> get() = _errorState

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        HomeUiState(
            sortingOption = 1,
            dataList = null,
            userState =UserStateUi(-1,"guest",true,""),
            errorObserver = errorState
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
        viewModelScope.launch {
            movieRepo.getAuthenticationStateFlow().collectLatest {  authState->
                val theState = onLoginEvent.invoke(authState)
                _uiState.update { it.copy(userState = theState) }
            }
        }

        //the default initialization
        onEvent(HomeEvents.OnSorting(1))
    }

     fun onEvent(event: HomeEvents){
        when(event){
             is HomeEvents.OnSorting -> {
                 viewModelScope.launch {
                     //not demanding authentication
                     if (uiState.value.userState.id!=-1 || event.sortId != 0) {
                         val theData = movieRepo.getMoviePaging(
                             event.sortId,
                             errorObserver = _errorState,
                             accountKey = uiState.value.userState.key,
                             accoundId = uiState.value.userState.id
                         )
                         prevSortId = uiState.value.sortingOption
                         _uiState.update {
                             it.copy(
                                 sortingOption = event.sortId,
                                 dataList = theData
                                     .cachedIn(viewModelScope)
                             )
                         }
                     }else{
                         _errorState.emit("Please login,to apply matched action")
                     }
                }
            }

             HomeEvents.OnSortError ->{
                _uiState.update {
                    it.copy(sortingOption = prevSortId)
                }
            }

            is HomeEvents.OnError -> {
                viewModelScope.launch {
                    _errorState.emit(event.error)
                }
            }

            is HomeEvents.OnLogin -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        //on Loading cancelation
                        if(event.toCancel){
                            movieRepo.setAuthenticationStateFlow(AuthState.Guest)
                        }else {
                            movieRepo.setAuthenticationStateFlow(AuthState.Loading)
                            if (uiState.value.userState.id != -1) {
                                //logOut
                                movieRepo.setAuthenticationStateFlow(AuthState.Guest)
                            } else {
                                try {
                                    withTimeout(5000) { // 5 seconds timeout
                                        ///Login
                                        movieRepo.initializeAuthentication()
                                    }
                                } catch (e: TimeoutCancellationException) {
                                    movieRepo.setAuthenticationStateFlow(AuthState.Guest)
                                }
                            }
                        }
                    }
                }
            }
        }

    }



}