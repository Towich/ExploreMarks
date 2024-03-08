package com.example.exploremarks.data.repository

import android.app.Application
import com.example.exploremarks.data.SessionMode
import com.example.exploremarks.data.model.CacheSession
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.SharedPref
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.network.ApiService
import com.example.exploremarks.network.util.ApiResult
import java.util.UUID

class MainRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService,
    private val cacheSession: CacheSession,
    private val sharedPref: SharedPref
) : MainRepository {

    override suspend fun getMarks(): ApiResult<List<MarkUIModel>> {
        return apiService.getMarks(
            accessToken = cacheSession.userData.accessToken,
            tokenType = cacheSession.userData.tokenType
        )
    }

    override fun getUserData(): UserData {
        return cacheSession.userData
    }

    override fun clearUserData() {
        cacheSession.userData.accessToken = null
        cacheSession.userData.tokenType = null
        cacheSession.sessionMode = SessionMode.GUEST

        sharedPref.removePreference(SharedPref.ACCESS_TOKEN)
        sharedPref.removePreference(SharedPref.TOKEN_TYPE)
    }

    override suspend fun likeMark(markId: UUID): ApiResult<MarkUIModel> {
        return apiService.likeMark(
            accessToken = cacheSession.userData.accessToken,
            tokenType = cacheSession.userData.tokenType,
            markId = markId
        )
    }

    override suspend fun dislikeMark(markId: UUID): ApiResult<Boolean> {
        return apiService.dislikeMark(
            accessToken = cacheSession.userData.accessToken,
            tokenType = cacheSession.userData.tokenType,
            markId = markId
        )
    }

}