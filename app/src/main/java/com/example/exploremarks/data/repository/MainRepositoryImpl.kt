package com.example.exploremarks.data.repository

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.SessionMode
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.data.source.AppContentProvider
import com.example.exploremarks.data.source.CacheSession
import com.example.exploremarks.data.source.SharedPref
import com.example.exploremarks.data.util.Converter
import com.example.exploremarks.network.ApiService
import com.example.exploremarks.network.util.ApiResult
import java.io.ByteArrayOutputStream
import java.util.UUID


class MainRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService,
    private val cacheSession: CacheSession,
    private val sharedPref: SharedPref,
    private val appContentProvider: AppContentProvider
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

    override suspend fun createMark(
        newMark: MarkUIModel,
        imageMark: ImageBitmap?
    ): ApiResult<MarkUIModel> {
        var byteArray: ByteArray? = null

        if(imageMark != null){
            byteArray = Converter.encodeToByteArray(image = imageMark)
        }

        return apiService.createMark(
            newMark = newMark,
            imageByteArray = byteArray,
            accessToken = cacheSession.userData.accessToken,
            tokenType = cacheSession.userData.tokenType
        )
    }

    override suspend fun deleteMark(markId: UUID): ApiResult<Boolean> {
        return apiService.deleteMark(
            markId = markId,
            accessToken = cacheSession.userData.accessToken,
            tokenType = cacheSession.userData.tokenType
        )
    }

    override fun getImageBitmapByUri(uri: Uri): ImageBitmap {
        return appContentProvider.getImageBitmapByUri(uri = uri)
    }
}