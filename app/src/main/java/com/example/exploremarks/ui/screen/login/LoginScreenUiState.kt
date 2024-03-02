package com.example.exploremarks.ui.screen.login

sealed interface LoginScreenUiState{
    data object Initial: LoginScreenUiState
    data object Loading: LoginScreenUiState
    data object Success: LoginScreenUiState
    data class Error(val message: String): LoginScreenUiState
}