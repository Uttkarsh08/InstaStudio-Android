package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.TokenStore
import com.uttkarsh.InstaStudio.utils.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _loginType = MutableStateFlow<UserType>(UserType.ADMIN)
    val loginType: StateFlow<UserType> = _loginType.asStateFlow()

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val token = authRepository.signInWithGoogle(context)
            _authState.value = if (token != null) {
                AuthState.Success(token)
            } else {
                AuthState.Error("Sign-in failed")
            }
        }
    }

    fun logout() {
        authRepository.signOut()
        _authState.value = AuthState.Idle
    }

    fun onFirebaseLoginSuccess(requestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.validateFirebaseToken(requestDTO)

                tokenStore.saveTokens(response.accessToken, response.refreshToken)
                tokenStore.saveUserInfo(response.userName, response.userEmail, response.firebaseId, response.userType)

                _authState.value = AuthState.BackendSuccess(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Backend login failed")
            }
        }
    }

}
