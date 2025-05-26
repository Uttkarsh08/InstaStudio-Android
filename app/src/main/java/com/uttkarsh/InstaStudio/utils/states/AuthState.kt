package com.uttkarsh.InstaStudio.utils.states

import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginResponseDTO

sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val token: String) : AuthState()
        data class BackendSuccess(val response: LoginResponseDTO) : AuthState()
        data class Error(val message: String) : AuthState()
}