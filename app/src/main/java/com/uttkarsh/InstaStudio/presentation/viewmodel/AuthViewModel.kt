package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore,
    private val onboardingStore: OnboardingStore,
    private val firebaseAuth: FirebaseAuth
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
        Log.d("AuthViewModel", "SessionStore collector started")
        viewModelScope.launch {
            Log.d("AuthViewModel", "SessionStore collector started for onBoarding")
            onboardingStore.isOnboardingShownFlow.collectLatest {
                _isOnBoardingShown.value = it
            }
        }

        viewModelScope.launch {
            Log.d("AuthViewModel", "SessionStore collector started for isRegistered")
            sessionStore.isRegisteredFlow.collectLatest {
                Log.d("AuthViewModel", "Read isRegistered: $it")
                _isRegistered.value = it
            }
        }

        viewModelScope.launch {
            _isLoggedIn.value = isUserLoggedIn()
        }
    }
    fun setLoginType(type: UserType) {
        _loginType.value = type
    }

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
        viewModelScope.launch {
            authRepository.signOut()
            sessionStore.clear()
            _isLoggedIn.value = false
            _isRegistered.value = false
            _authState.value = AuthState.Idle
        }
    }

    fun onFirebaseLoginSuccess(requestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.validateFirebaseToken(requestDTO)

                sessionStore.saveTokens(response.accessToken, response.refreshToken)
                sessionStore.saveUserInfo(response.userName, response.userEmail, response.firebaseId, response.userType, response.isRegistered)

                Log.d("SessionStore", "Saving email: ${response.userEmail}")
                Log.d("AuthViewModel", "Saving isRegistered: ${response.isRegistered}")

                _authState.value = AuthState.BackendSuccess(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Backend login failed")
            }
        }
    }

    suspend fun isUserLoggedIn(): Boolean = withContext(Dispatchers.IO) { firebaseAuth.currentUser != null }

    fun setOnBoardingShown() {
        viewModelScope.launch {
            try {
                onboardingStore.setOnboardingShown()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Failed to set onboarding shown", e)
            }
        }
    }




}
