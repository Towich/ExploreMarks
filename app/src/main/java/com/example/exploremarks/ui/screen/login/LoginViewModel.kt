package com.example.exploremarks.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploremarks.domain.ClearUserDataUseCase
import com.example.exploremarks.domain.GetUserDataUseCase
import com.example.exploremarks.domain.LoginUseCase
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.ui.screen.util.AuthScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val getUserData: GetUserDataUseCase,
    private val clearUserData: ClearUserDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenUiState>(AuthScreenUiState.Initial)
    val uiState: StateFlow<AuthScreenUiState> = _uiState

    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthScreenUiState.Loading

            when (val loginResult = login(username = username, password = password)) {
                is ApiResult.Success -> {
                    _uiState.value = AuthScreenUiState.Success
                }

                is ApiResult.Error -> {
                    _uiState.value = AuthScreenUiState.Error(loginResult.error)
                }

                else -> {

                }
            }
        }
    }

    fun getUsername(): String? {
        return getUserData().username
    }

    fun getAuthorized(): Boolean {
        return getUserData().accessToken != null
    }

    fun changeUiState(newState: AuthScreenUiState) {
        _uiState.value = newState
    }

    fun performClearUserData(){
        clearUserData()
    }
}