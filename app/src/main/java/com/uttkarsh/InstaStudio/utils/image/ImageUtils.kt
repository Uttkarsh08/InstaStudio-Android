package com.uttkarsh.InstaStudio.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import android.util.Log
import androidx.core.graphics.scale

object ImageUtils{

    private const val TAG = "ImageUtils"

    suspend fun uriToBase64(context: Context, imageUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)

            if (inputStream == null) {
                Log.e(TAG, "ContentResolver returned null InputStream for URI: $imageUri")
                return@withContext null
            }

            inputStream.use { stream ->
                val originalBitmap = BitmapFactory.decodeStream(stream)
                if (originalBitmap == null) {
                    Log.e(TAG, "BitmapFactory.decodeStream returned null.")
                    return@withContext null
                }

                Log.d(TAG, "Original Bitmap - Width: ${originalBitmap.width}, Height: ${originalBitmap.height}")

                val maxSize = 800
                val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
                val (newWidth, newHeight) = if (aspectRatio > 1) {
                    maxSize to (maxSize / aspectRatio).toInt()
                } else {
                    (maxSize * aspectRatio).toInt() to maxSize
                }

                val resizedBitmap = originalBitmap.scale(newWidth, newHeight)
                Log.d(TAG, "Resized Bitmap - Width: $newWidth, Height: $newHeight")

                val outputStream = ByteArrayOutputStream()

                val success = resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
                if (!success) {
                    Log.e(TAG, "Bitmap.compress returned false. Failed to compress bitmap.")
                    return@withContext null
                }

                val byteArray = outputStream.toByteArray()
                Log.d(TAG, "Compressed ByteArray Size: ${byteArray.size}")

                val base64String = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                Log.d(TAG, "Base64 Length: ${base64String.length}, First 100 chars: ${base64String.take(100)}")

                base64String
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in uriToBase64 for URI: $imageUri", e)
            null
        }
    }


    fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.NO_WRAP) // Match the encoding flag
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Error decoding Base64 string to Bitmap (in base64ToBitmap)", e)
            null
        }
    }
}