package com.example.exploremarks.ui.screen.map


sealed interface MarkUiState{
    data object Initial: MarkUiState
    data object Loading: MarkUiState
    data class Success<out T>(val data: T): MarkUiState
    data class Error(val message: String): MarkUiState
}