package com.example.mymovies.data.local.factory

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mymovies.domain.util.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class AuthDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "auth_prefs")


    private val sessionIdKey = stringPreferencesKey("session_id")


    val sessionId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[sessionIdKey]
    }

    // MutableStateFlow to allow manual updates
    private val _authState = MutableStateFlow<AuthState>(AuthState.Guest)

    // Flow derived from the datastore
    private val _authStateFromDataStore: Flow<AuthState> = sessionId.map { sessionId ->
        if (sessionId.isNullOrEmpty()) AuthState.Guest else AuthState.Authenticated(sessionId)
    }

    // Combined StateFlow
    val authState: StateFlow<AuthState> = combine(
        _authStateFromDataStore,
        _authState
    ) { dataStoreState, manualState ->
        // Manual state takes precedence if it's not Guest; otherwise, use datastore state
        if (manualState != AuthState.Guest) manualState else dataStoreState
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Default), // Adjust scope as needed
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AuthState.Guest
    )

    suspend fun getAuthStateFlow(): StateFlow<AuthState> {
        return authState
    }

    suspend fun setAuthStateFlow(theState:AuthState) {
        return _authState.update { theState }
    }

    suspend fun saveSessionId(sessionId: String) {
        context.dataStore.edit { preferences ->
            preferences[sessionIdKey] = sessionId
        }
    }

    suspend fun clearSessionId() {
        context.dataStore.edit { preferences ->
            preferences.remove(sessionIdKey)
        }
    }
}