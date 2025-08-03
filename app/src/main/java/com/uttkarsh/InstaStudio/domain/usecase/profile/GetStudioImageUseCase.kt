package com.uttkarsh.InstaStudio.domain.usecase.profile

import android.graphics.Bitmap
import android.util.Log
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.image.ImageUtils
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetStudioImageUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): String {
        return withContext(Dispatchers.IO) {
            try {
                val studioId = sessionManager.getStudioId()
                val response = profileRepository.getStudioImage(studioId)

                val data = response.data
                ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

                return@withContext data

            }catch (e: Exception) {
                val msg = "API Error: ${e.message}"
                Log.e("getStudioImageUseCase", msg)
                throw Exception(msg)
            }
        }
    }
}
