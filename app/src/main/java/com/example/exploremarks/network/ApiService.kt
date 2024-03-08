package com.example.exploremarks.network

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.MarkSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult
import java.util.UUID


interface ApiService {
    suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable>
    suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable>
    suspend fun getMarks(accessToken: String?, tokenType: String?): ApiResult<List<MarkUIModel>>
    suspend fun likeMark(accessToken: String?, tokenType: String?, markId: UUID): ApiResult<MarkUIModel>
    suspend fun dislikeMark(accessToken: String?, tokenType: String?, markId: UUID): ApiResult<Boolean>
}