package com.example.exploremarks.domain

import com.example.exploremarks.data.repository.AuthorizationRepository
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.util.ApiResult

class LoginUseCase(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(username: String, password: String): ApiResult<LoginResponseSerializable> {
        return repository.login(LoginRequestSerializable(username = username, password = password))
    }
}