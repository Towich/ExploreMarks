package com.example.exploremarks.data.repository

import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult

interface AuthorizationRepository {
    suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable>
    suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable>
}