package com.example.exploremarks.data.repository

import android.app.Application
import com.example.exploremarks.network.ApiService
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult

class AuthorizationRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService
): AuthorizationRepository  {
    override suspend fun register(userData: RegisterRequestSerializable): RegisterResponseSerializable? {
        TODO("Not yet implemented")
    }

    override suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable> {
       return apiService.login(userData)
    }
}