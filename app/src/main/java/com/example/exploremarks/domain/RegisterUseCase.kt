package com.example.exploremarks.domain

import com.example.exploremarks.data.repository.AuthorizationRepository
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult

class RegisterUseCase(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(username: String, password: String): ApiResult<RegisterResponseSerializable> {
        return repository.register(RegisterRequestSerializable(username = username, password = password))
    }
}