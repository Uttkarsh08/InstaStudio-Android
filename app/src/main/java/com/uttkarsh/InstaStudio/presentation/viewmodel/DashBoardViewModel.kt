package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.DashboardUseCases
import com.uttkarsh.InstaStudio.utils.states.DashBoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases

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
                val response = dashboardUseCases.getUserProfile()
                _dashBoardState.value = DashBoardState.Success(response.studioId, response.userId)



            } catch (e: Exception) {
                _dashBoardState.value = DashBoardState.Error(e.localizedMessage ?: "An unexpected error occurred")
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
            }
        }

    }

}