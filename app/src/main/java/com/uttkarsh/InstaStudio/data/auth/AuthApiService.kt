package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.LoginResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApiService {

    @POST("api/auth/v1/login")
    suspend fun login(@Body req: LoginRequestDTO): ApiResponse<LoginResponseDTO>
}