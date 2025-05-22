package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupResponseDTO
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ProfileApiService {


    @POST("api/v1/register/adminProfileSetup")
    suspend fun adminProfileSetup(@Body request: AdminProfileSetupRequestDTO): ApiResponse<AdminProfileSetupResponseDTO>
}