package com.example.exploremarks.data.repository

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.network.util.ApiResult
import java.util.UUID

interface MainRepository {
    suspend fun getMarks(): ApiResult<List<MarkUIModel>>
    fun getUserData(): UserData
    fun clearUserData()
    suspend fun likeMark(markId: UUID): ApiResult<MarkUIModel>
    suspend fun dislikeMark(markId: UUID): ApiResult<Boolean>
    suspend fun createMark(newMark: MarkUIModel, imageMark: ImageBitmap?): ApiResult<MarkUIModel>
    suspend fun deleteMark(markId: UUID): ApiResult<Boolean>
    fun getImageBitmapByUri(uri: Uri): ImageBitmap
}