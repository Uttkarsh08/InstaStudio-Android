package com.uttkarsh.InstaStudio.domain.model.dto.auth

import com.uttkarsh.InstaStudio.domain.model.UserType

data class LoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean,
    val userName: String,
    val userEmail: String,
    val firebaseId: String,
    val userType: UserType
)