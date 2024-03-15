package com.example.exploremarks.data.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream


class Converter {
    companion object {
        fun encodeToByteArray(image: ImageBitmap): ByteArray {
            val byteArrayStream = ByteArrayOutputStream()
            image.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayStream)
            return byteArrayStream.toByteArray()
        }
    }

}