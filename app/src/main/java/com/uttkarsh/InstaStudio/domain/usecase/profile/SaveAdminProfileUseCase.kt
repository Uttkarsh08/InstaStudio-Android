package com.uttkarsh.InstaStudio.domain.usecase.profile

import android.util.Log
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.model.dto.admin.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.studio.StudioRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.user.ProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.user.UserProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class SaveAdminProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(
        phoneNumber: String,
        studioName: String,
        address: String,
        city: String,
        state: String,
        pinCode: String,
        selectedStudioImageBase64: String?
    ): UserProfileResponseDTO {
        return withContext(Dispatchers.IO) {
            try {
                val firebaseId = sessionStore.firebaseIdFlow.firstOrNull().orEmpty()
                val userName = sessionStore.nameFlow.firstOrNull().orEmpty()
                val userEmail = sessionStore.emailFlow.firstOrNull().orEmpty()
                val userType = sessionStore.userTypeFlow.firstOrNull() ?: UserType.ADMIN

                val profileRequest = ProfileRequestDTO(
                    firebaseId = firebaseId,
                    userName = userName,
                    userPhoneNo = phoneNumber,
                    userEmail = userEmail,
                    userType = userType
                )

                profileRequest.validate()?.let { error ->
                    throw IllegalArgumentException(error)
                }

                val studioRequest = StudioRequestDTO(
                    studioName = studioName,
                    studioAddress = address,
                    studioCity = city,
                    studioState = state,
                    studioPinCode = pinCode,
                    imageDataBase64 = selectedStudioImageBase64
                )

                studioRequest.validate()?.let { error ->
                    throw IllegalArgumentException(error)
                }

                val request = AdminProfileSetupRequestDTO(
                    user = profileRequest,
                    studio = studioRequest
                )

                val profileResponse = profileRepository.adminProfileSetup(request)
                val data = profileResponse.data
                    ?: throw Exception("${profileResponse.error.message}: ${profileResponse.error.subErrors.joinToString()}")

                val refreshToken = sessionStore.refreshTokenFlow.firstOrNull().orEmpty()
                val networkResponse = authRepository.refreshToken(TokenRefreshRequestDTO(refreshToken))

                if (networkResponse.data != null) {
                    sessionStore.saveTokens(networkResponse.data.accessToken, networkResponse.data.refreshToken)
                    Log.d("SaveAdminProfileUseCase", "New tokens saved")
                }

                sessionStore.updateIsRegistered()
                sessionManager.updateStudioAndUserId(data.studioId, data.userId)

                return@withContext data
            }catch (e: HttpException) {
                val msg = "API Error: ${e.message()}"
                Log.e("SaveAdminProfileProfileUseCase", msg)
                throw Exception(msg)
            } catch (e: Exception) {
                Log.e("SaveAdminProfileProfileUseCase", "Unexpected error", e)
                throw e
            }
        }
    }
}
