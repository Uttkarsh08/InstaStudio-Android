package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApiService {

    @POST("api/auth/v1/login")
    suspend fun login(@Body request: LoginRequestDTO): ApiResponse<LoginResponseDTO>

    @POST("/api/auth/v1/refresh-token")
    suspend fun refreshToken(@Body request: TokenRefreshRequestDTO): ApiResponse<TokenRefreshResponseDTO>

}
