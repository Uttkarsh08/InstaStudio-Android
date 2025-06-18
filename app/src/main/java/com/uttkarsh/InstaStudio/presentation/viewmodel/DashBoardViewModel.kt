package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
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
    private val sessionStore: SessionStore

) : ViewModel(){

    private val _dashBoardState = MutableStateFlow<DashBoardState>(DashBoardState.Idle)
    val dashBoardState = _dashBoardState.asStateFlow()

    fun resetDashBoardState(){
        _dashBoardState.value = DashBoardState.Idle

    }
    fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            val currentStudioId = sessionStore.studioIdFlow.first()
            val currentUserId = sessionStore.userIdFlow.first()
            Log.d("DashBoardViewModel", "getUserProfile called. StudioID from session: $currentStudioId, UserID from session: $currentUserId")

            try {
                val response = withContext(Dispatchers.IO) {
                    profileRepository.getUserProfile()
                }
                if (response.data == null) {
                    _dashBoardState.value = DashBoardState.Error(
                        (response.error.message + ": " + response.error.subErrors.joinToString())
                    )
                    return@launch
                }
                _dashBoardState.value = DashBoardState.Success(response.data.studioId, response.data.userId)

                withContext(Dispatchers.IO) {

                    sessionStore.saveStudioId(response.data.studioId)
                    sessionStore.saveUserId(response.data.userId)
                }

            } catch (e: HttpException) {
                val errorMessage = ApiErrorExtractor.extractMessage(e)
                Log.e("ProfileViewModel", "API Error: $errorMessage")

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
            }
        }

    }

}