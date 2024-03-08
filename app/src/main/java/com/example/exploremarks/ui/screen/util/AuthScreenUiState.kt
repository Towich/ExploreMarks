package com.example.exploremarks.ui.screen.util

sealed interface AuthScreenUiState{
    data object Initial: AuthScreenUiState
    data object Loading: AuthScreenUiState
    data object Success : AuthScreenUiState
    data class Error(val message: String): AuthScreenUiState
}