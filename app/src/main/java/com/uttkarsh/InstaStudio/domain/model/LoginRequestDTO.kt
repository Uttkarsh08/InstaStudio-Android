package com.uttkarsh.InstaStudio.domain.model

data class LoginRequestDTO(
        val firebaseToken: String,
        val loginType: UserType
)