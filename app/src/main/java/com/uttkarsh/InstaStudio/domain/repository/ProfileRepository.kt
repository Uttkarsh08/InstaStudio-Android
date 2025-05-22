package com.uttkarsh.InstaStudio.domain.repository

import com.uttkarsh.InstaStudio.data.auth.ProfileApiService
import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupResponseDTO
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApiService,

){

    suspend fun adminProfileSetup(requestDTO: AdminProfileSetupRequestDTO): ApiResponse<AdminProfileSetupResponseDTO>{
        return profileApi.adminProfileSetup(requestDTO)

    }
}