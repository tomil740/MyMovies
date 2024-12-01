package com.example.mymovies.presntation.HomePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.mymovies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel@Inject constructor(
    private val movieRepo : MoviesRepository
) :ViewModel(){

    private var prevSortId:Int = 1

    private val _errorState = MutableSharedFlow<String>(replay = 1)
    val errorState: SharedFlow<String> get() = _errorState

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        HomeUiState(
            sortingOption = 1,
            dataList = null,
            userName = "Tomi",
            errorObserver = errorState
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
        //the default initialization
        onEvent(HomeEvents.OnSorting(1))
    }

     fun onEvent(event: HomeEvents){
        when(event){
             is HomeEvents.OnSorting -> {
                 viewModelScope.launch {
                    val theData = movieRepo.getMoviePaging(event.sortId, errorObserver = _errorState)
                    prevSortId = uiState.value.sortingOption
                    _uiState.update {
                        it.copy(
                            sortingOption = event.sortId,
                            dataList = theData
                                .cachedIn(viewModelScope)
                        )
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
        }

    }



}