package com.uttkarsh.InstaStudio.domain.repository

import com.uttkarsh.InstaStudio.data.auth.ProfileApiService
import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApiService,

    ){

    suspend fun adminProfileSetup(requestDTO: AdminProfileSetupRequestDTO): ApiResponse<UserProfileResponseDTO>{
        return profileApi.adminProfileSetup(requestDTO)

    }

    suspend fun getUserProfile(): ApiResponse<UserProfileResponseDTO>{
        return profileApi.getUserProfile()
    }

    suspend fun getStudioImage(studioId: Long): ApiResponse<String>{
        return profileApi.getStudioImage(studioId)

    }

}