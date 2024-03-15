package com.example.exploremarks.domain

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.example.exploremarks.data.repository.MainRepository

class GetImageBitmapByUriUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(uri: Uri): ImageBitmap {
        return repository.getImageBitmapByUri(uri)
    }
}