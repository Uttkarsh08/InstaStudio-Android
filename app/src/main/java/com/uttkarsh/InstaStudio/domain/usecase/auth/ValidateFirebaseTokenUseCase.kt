package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateFirebaseTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) {
    suspend operator fun invoke(requestDTO: LoginRequestDTO): LoginResponseDTO = withContext(Dispatchers.IO) {
        val response = authRepository.validateFirebaseToken(requestDTO)

        sessionStore.saveTokens(response.accessToken, response.refreshToken)
        sessionStore.saveUserInfo(
            response.userName,
            response.userEmail,
            response.firebaseId,
            response.userType,
            response.isRegistered
        )
        return@withContext response
    }
}
