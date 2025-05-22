package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import com.uttkarsh.InstaStudio.utils.SharedPref.TokenStore
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
    private val tokenStore: TokenStore,
    private val onboardingStore: OnboardingStore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _loginType = MutableStateFlow<UserType>(UserType.ADMIN)
    val loginType: StateFlow<UserType> = _loginType.asStateFlow()

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination

    init {
        viewModelScope.launch {
            val shown = isOnboardingShown()
            val loggedIn = isUserLoggedIn()
            val registered = isUserRegistered()

            _startDestination.value = when {
                !shown -> Screens.OnBoardingScreen.route
                !loggedIn -> Screens.LoginTypeScreen.route
                !registered -> Screens.ProfileCompletionScreen.route
                else -> Screens.DashBoardScreen.route
            }
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
        authRepository.signOut()
        _authState.value = AuthState.Idle
    }

    fun onFirebaseLoginSuccess(requestDTO: LoginRequestDTO) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.validateFirebaseToken(requestDTO)

                withContext(Dispatchers.IO) { tokenStore.saveTokens(response.accessToken, response.refreshToken) }
                withContext(Dispatchers.IO) { tokenStore.saveUserInfo(response.userName, response.userEmail, response.firebaseId, response.userType, false) }
                Log.d("TokenStore", "Saving email: ${response.userEmail}")

                _authState.value = AuthState.BackendSuccess(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Backend login failed")
            }
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    fun isUserRegistered(): Boolean = tokenStore.getIsRegistered()

    fun isOnboardingShown(): Boolean = onboardingStore.isOnboardingShown()

    fun setOnboardingShown() = onboardingStore.setOnboardingShown()

}
