package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.data.auth.GoogleSignInManager
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.usecase.auth.AuthUseCases
import com.uttkarsh.InstaStudio.utils.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val googleSignInManager: GoogleSignInManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _loginType = MutableStateFlow<UserType>(UserType.ADMIN)
    val loginType: StateFlow<UserType> = _loginType.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isRegistered = MutableStateFlow<Boolean>(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered.asStateFlow()

    private val _isOnBoardingShown = MutableStateFlow<Boolean>(false)
    val isOnBoardingShown: StateFlow<Boolean> = _isOnBoardingShown.asStateFlow()

    init {
        observeOnboardingStatus()
        observeRegistrationStatus()
        checkIfUserIsLoggedIn()
    }
    private fun observeOnboardingStatus() {
        viewModelScope.launch {
            authUseCases.observeOnboardingShown().collectLatest {
                _isOnBoardingShown.value = it
                Log.d("AuthViewModel", "Onboarding shown: $it")
            }
        }
    }

    private fun observeRegistrationStatus() {
        viewModelScope.launch {
            authUseCases.observeRegistrationStatus().collectLatest {
                _isRegistered.value = it
                Log.d("AuthViewModel", "Registration status: $it")
            }
        }
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val loggedIn = authUseCases.checkUserLoggedIn()
            _isLoggedIn.value = loggedIn
            Log.d("AuthViewModel", "User logged in: $loggedIn")
        }
    }

    fun setLoginType(type: UserType) {
        _loginType.value = type
    }

    fun signInWithGoogle(activity: Activity) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val token = googleSignInManager.signInWithGoogle(activity)
                if (token != null) {
                    _authState.value = AuthState.Success(token)
                } else {
                    _authState.value = AuthState.Error("Sign-in failed")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-in error", e)
                _authState.value = AuthState.Error(e.localizedMessage ?: "Sign-in failed.")
            }
        }
    }


    fun onFirebaseLoginSuccess(requestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authUseCases.validateFirebaseToken(requestDTO)
                _authState.value = AuthState.BackendSuccess(response)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Validate token error", e)
                _authState.value = AuthState.Error(e.localizedMessage ?: "Token validation failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authUseCases.logout()
                _authState.value = AuthState.Idle
                _isLoggedIn.value = false
                _isRegistered.value = false
                Log.d("AuthViewModel", "Logged out successfully")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Logout failed", e)
                _authState.value = AuthState.Error(e.localizedMessage ?: "Logout failed")
            }
        }
    }

    fun setOnBoardingShown() {
        viewModelScope.launch {
            try {
                authUseCases.setOnboardingShown()
                Log.d("AuthViewModel", "Onboarding marked as shown")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Failed to set onboarding shown", e)
            }
        }
    }

}
