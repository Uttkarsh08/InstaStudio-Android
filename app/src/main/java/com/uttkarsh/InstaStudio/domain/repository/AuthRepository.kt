package com.uttkarsh.InstaStudio.domain.repository

import android.content.Context
import com.uttkarsh.InstaStudio.data.auth.AuthApiService
import com.uttkarsh.InstaStudio.data.auth.GoogleSignInManager
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshResponseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val googleSignInManager: GoogleSignInManager,
    private val authApi: AuthApiService
) {

    suspend fun validateFirebaseToken(requestDTO: LoginRequestDTO): LoginResponseDTO {
        val resp = authApi.login(requestDTO)
        if (resp.data != null) {
            return resp.data
        } else {
            throw Exception("API error: ${resp.error}")
        }
    }

    suspend fun refreshToken(request: TokenRefreshRequestDTO): ApiResponse<TokenRefreshResponseDTO>{
        return authApi.refreshToken(request);
    }

    fun signOut() = googleSignInManager.signOut()
}