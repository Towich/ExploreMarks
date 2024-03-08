package com.example.exploremarks.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.domain.RegisterUseCase
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.ui.screen.util.AuthScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenUiState>(AuthScreenUiState.Initial)
    val uiState: StateFlow<AuthScreenUiState> = _uiState

    fun performRegister(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthScreenUiState.Loading

            when (val registerResult = register(username = username, password = password)) {
                is ApiResult.Success -> {
                    _uiState.value = AuthScreenUiState.Success
                }

                is ApiResult.Error -> {
                    _uiState.value = AuthScreenUiState.Error(registerResult.error)
                }

                else -> {

                }
            }
        }
    }

    fun changeUiState(newState: AuthScreenUiState) {
        _uiState.value = newState
    }
}