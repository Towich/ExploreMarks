package com.example.exploremarks.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.domain.LoginUseCase
import com.example.exploremarks.domain.RegisterUseCase
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.ui.screen.util.AuthorizationScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthorizationScreenUiState>(AuthorizationScreenUiState.Initial)
    val uiState: StateFlow<AuthorizationScreenUiState> = _uiState

    fun performRegister(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthorizationScreenUiState.Loading

            when (val registerResult = register(username = username, password = password)) {
                is ApiResult.Success -> {
                    _uiState.value = AuthorizationScreenUiState.Success
                }

                is ApiResult.Error -> {
                    _uiState.value = AuthorizationScreenUiState.Error(registerResult.error)
                }

                else -> {

                }
            }
        }
    }

    fun changeUiState(newState: AuthorizationScreenUiState) {
        _uiState.value = newState
    }
}