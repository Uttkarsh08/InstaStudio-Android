package com.uttkarsh.InstaStudio.domain.model.dto.auth

data class TokenRefreshResponseDTO(
    val accessToken: String,
    val refreshToken: String
)
