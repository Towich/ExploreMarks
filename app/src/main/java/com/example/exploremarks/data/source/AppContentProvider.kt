package com.example.exploremarks.data.source

import android.app.Application
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class AppContentProvider(
    private val appContext: Application
) {
    fun getImageBitmapByUri(uri: Uri): ImageBitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(appContext.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(appContext.contentResolver, uri)
        }
        return bitmap.asImageBitmap()
    }
}