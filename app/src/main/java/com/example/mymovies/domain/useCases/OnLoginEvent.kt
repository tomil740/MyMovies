package com.example.mymovies.domain.useCases

import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.util.AuthState
import com.example.mymovies.domain.util.Result
import kotlinx.coroutines.flow.update

class OnLoginEvent (private val repo: MoviesRepository) {
    operator suspend fun invoke(authState: AuthState): UserStateUi {
        var user = UserStateUi(-1,"guest",false,"")
        when(authState){
            is AuthState.Authenticated ->{
                val a = repo.getAccountObj(authState.sessionId)
                when(a){
                    is Result.Error -> {
                        user = user.copy(-1,"fail",false)
                    }
                    is Result.Success -> {
                        user = a.data
                    }
                }
            }
            is AuthState.Error -> {
                user = user.copy(-1,"fail",false)

            }
            is AuthState.Guest -> {
                user = user.copy(-1,"fail",false)

            }
            is AuthState.Loading -> {
                user = user.copy(isLoading = true)
            }
        }
        return user

    }
}