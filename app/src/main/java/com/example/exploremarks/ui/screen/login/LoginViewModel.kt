package com.example.exploremarks.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.domain.LoginUseCase
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.ui.screen.util.AuthorizationScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthorizationScreenUiState>(AuthorizationScreenUiState.Initial)
    val uiState: StateFlow<AuthorizationScreenUiState> = _uiState

    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthorizationScreenUiState.Loading

            when (val loginResult = login(username = username, password = password)) {
                is ApiResult.Success -> {
                    _uiState.value = AuthorizationScreenUiState.Success
                }

                is ApiResult.Error -> {
                    _uiState.value = AuthorizationScreenUiState.Error(loginResult.error)
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