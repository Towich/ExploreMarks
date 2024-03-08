package com.example.exploremarks.ui.screen.map

sealed interface MapScreenUiState{
    data object Initial: MapScreenUiState
    data object Loading: MapScreenUiState
    data object Success: MapScreenUiState
    data class Error(val message: String): MapScreenUiState
}