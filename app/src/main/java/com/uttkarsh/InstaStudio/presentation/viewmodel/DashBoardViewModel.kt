package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import com.uttkarsh.InstaStudio.utils.states.DashBoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _dashBoardState = MutableStateFlow<DashBoardState>(DashBoardState.Idle)
    val dashBoardState = _dashBoardState.asStateFlow()

    var hasLoadedUserProfile by mutableStateOf(false)
        private set

    fun loadUserProfileIfNeeded() {
        if (!hasLoadedUserProfile) {
            hasLoadedUserProfile = true
            getUserProfile()
        }
    }

    fun resetHasLoadedUserProfile() { // use when Sign out
        hasLoadedUserProfile = false
    }

    fun resetDashBoardState(){
        _dashBoardState.value = DashBoardState.Idle

    }
    fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            _dashBoardState.value = DashBoardState.Loading
            try {
                val response = profileRepository.getUserProfile()

                if (response.data == null) {
                    _dashBoardState.value = DashBoardState.Error(
                        (response.error.message + ": " + response.error.subErrors.joinToString())
                    )
                    return@launch
                }
                _dashBoardState.value = DashBoardState.Success(response.data.studioId, response.data.userId)

                sessionManager.updateStudioAndUserId(
                    response.data.studioId,
                    response.data.userId
                )

            } catch (e: HttpException) {
                val errorMessage = ApiErrorExtractor.extractMessage(e)
                Log.e("ProfileViewModel", "API Error: $errorMessage")

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
            }
        }

    }

}