package com.example.exploremarks.ui.screen.util

sealed interface AuthorizationScreenUiState{
    data object Initial: AuthorizationScreenUiState
    data object Loading: AuthorizationScreenUiState
    data object Success: AuthorizationScreenUiState
    data class Error(val message: String): AuthorizationScreenUiState
}