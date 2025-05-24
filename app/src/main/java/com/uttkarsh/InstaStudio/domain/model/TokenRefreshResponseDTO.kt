package com.uttkarsh.InstaStudio.domain.model

data class TokenRefreshResponseDTO(
    val accessToken: String,
    val refreshToken: String
)
