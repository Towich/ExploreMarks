package com.example.exploremarks.network

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult


interface ApiService {
    suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable>
    suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable>
    suspend fun getMarks(): List<MarkUIModel>?
}