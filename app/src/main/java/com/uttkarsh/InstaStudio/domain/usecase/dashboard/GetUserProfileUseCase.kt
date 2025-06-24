package com.uttkarsh.InstaStudio.domain.usecase.dashboard

import android.util.Log
import com.uttkarsh.InstaStudio.domain.model.dto.user.UserProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): UserProfileResponseDTO = withContext(Dispatchers.IO) {
        try {
            val response = profileRepository.getUserProfile()

            if (response.data == null) {
                val errorMsg = (response.error.message + ": " + response.error.subErrors.joinToString())
                throw Exception(errorMsg)
            }

            sessionManager.updateStudioAndUserId(
                response.data.studioId,
                response.data.userId
            )

            return@withContext response.data

        } catch (e: HttpException) {
            val msg = "API Error: ${e.message()}"
            Log.e("GetUserProfileUseCase", msg)
            throw Exception(msg)
        } catch (e: Exception) {
            Log.e("GetUserProfileUseCase", "Unexpected error", e)
            throw e
        }
    }
}
