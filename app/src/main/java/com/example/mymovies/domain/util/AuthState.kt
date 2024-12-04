package com.example.mymovies.domain.util

sealed class AuthState {
    object Guest : AuthState()
    data class Authenticated(val sessionId: String) : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}