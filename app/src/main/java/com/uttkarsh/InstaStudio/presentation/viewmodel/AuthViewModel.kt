package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isAuthRefreshed = MutableStateFlow(false)
    val isAuthRefreshed: StateFlow<Boolean> = _isAuthRefreshed.asStateFlow()


    fun setLoginType(type: UserType) {
        _loginType.value = type
    }

    fun refreshAuthState() {
        viewModelScope.launch {
            _isAuthRefreshed.value = false

            _isLoggedIn.value = isUserLoggedIn()
            _isRegistered.value = isUserRegistered()
            _isOnBoardingShown.value = isOnboardingShown()

            _isAuthRefreshed.value = true
        }
    }

    fun resetAuthRefreshFlag() {
        _isAuthRefreshed.value = false
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
        authRepository.signOut()
        sessionStore.clear()
        _isLoggedIn.value = false
        _isRegistered.value = false
        _authState.value = AuthState.Idle
    }

    fun onFirebaseLoginSuccess(requestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.validateFirebaseToken(requestDTO)

                withContext(Dispatchers.IO) { sessionStore.saveTokens(response.accessToken, response.refreshToken) }
                withContext(Dispatchers.IO) { sessionStore.saveUserInfo(response.userName, response.userEmail, response.firebaseId, response.userType, response.isRegistered) }
                Log.d("SessionStore", "Saving email: ${response.userEmail}")

                _authState.value = AuthState.BackendSuccess(response)
                refreshAuthState()
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Backend login failed")
            }
        }
    }

    suspend fun isUserLoggedIn(): Boolean = withContext(Dispatchers.IO) { firebaseAuth.currentUser != null }

    suspend fun isUserRegistered(): Boolean = withContext(Dispatchers.IO) { sessionStore.getIsRegistered() }

    suspend fun isOnboardingShown(): Boolean = withContext(Dispatchers.IO) { onboardingStore.isOnboardingShown() }

    fun setOnboardingShown() = onboardingStore.setOnboardingShown()


}
