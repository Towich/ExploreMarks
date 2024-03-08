package com.example.exploremarks.data.repository

import android.app.Application
import com.example.exploremarks.data.model.CacheSession
import com.example.exploremarks.data.model.SharedPref
import com.example.exploremarks.network.ApiService
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult

class AuthorizationRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService,
    private val sharedPref: SharedPref,
    private val cacheSession: CacheSession
): AuthorizationRepository  {
    override suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable> {
        val apiResult = apiService.register(userData)
        if(apiResult is ApiResult.Success){
            sharedPref.savePreferencesString(SharedPref.USER_ID, apiResult.data.id.toString())
            sharedPref.savePreferencesString(SharedPref.USERNAME, apiResult.data.username)

            cacheSession.userData.userId = apiResult.data.id
            cacheSession.userData.username = apiResult.data.username
        }

        return apiResult
    }

    override suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable> {
        val apiResult = apiService.login(userData)
        if(apiResult is ApiResult.Success){
            sharedPref.savePreferencesString(SharedPref.ACCESS_TOKEN, apiResult.data.access_token)
            sharedPref.savePreferencesString(SharedPref.TOKEN_TYPE, apiResult.data.token_type)

            cacheSession.userData.accessToken = apiResult.data.access_token
            cacheSession.userData.tokenType = apiResult.data.token_type
        }

        return apiResult
    }
}