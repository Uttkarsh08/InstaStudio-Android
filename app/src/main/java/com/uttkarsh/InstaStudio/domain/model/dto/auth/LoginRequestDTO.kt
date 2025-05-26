package com.uttkarsh.InstaStudio.domain.model.dto.auth

import com.uttkarsh.InstaStudio.domain.model.UserType

data class LoginRequestDTO(
        val firebaseToken: String,
        val loginType: UserType
)