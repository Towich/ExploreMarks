package com.example.exploremarks.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.domain.LoginUseCase
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.ui.screen.login.LoginScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.Initial)
    val uiState: StateFlow<LoginScreenUiState> = _uiState

    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginScreenUiState.Loading

            when (val loginResult = login(username = username, password = password)) {
                is ApiResult.Success -> {
                    _uiState.value = LoginScreenUiState.Success
                }

                is ApiResult.Error -> {
                    _uiState.value = LoginScreenUiState.Error(loginResult.error)
                }

                else -> {

                }
            }
        }
    }

    fun changeUiState(newState: LoginScreenUiState) {
        _uiState.value = newState
    }
}