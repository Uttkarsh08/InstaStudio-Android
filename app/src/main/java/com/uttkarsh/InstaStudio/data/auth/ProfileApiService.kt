package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProfileApiService {


    @POST("/api/v1/register/adminProfileSetup")
    suspend fun adminProfileSetup(@Body request: AdminProfileSetupRequestDTO): ApiResponse<UserProfileResponseDTO>

    @GET("/api/v1/image/{studioId}")
    suspend fun getStudioImage(@Path("studioId") studioId: Long): ApiResponse<String>


}
