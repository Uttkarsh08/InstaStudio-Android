package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.model.dto.admin.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.user.ProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.studio.StudioRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.image.ImageUtils
import com.uttkarsh.InstaStudio.utils.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState = _profileState.asStateFlow()

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail = _userEmail.asStateFlow()

    var studioName by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var address by mutableStateOf("")
        private set

    var city by mutableStateOf("")
        private set

    var state by mutableStateOf("")
        private set

    var pinCode by mutableStateOf("")
        private set

    var selectedStudioImageUri by mutableStateOf<Uri?>(null)
        private set

    var selectedStudioImageBase64 by mutableStateOf<String?>(null)
        private set

    var StudioImageBitMap by mutableStateOf<Bitmap?>(null)
        private set

    fun updateStudioName(name: String) {
        studioName = name
    }

    fun updatePhoneNumber(phone: String) {
        phoneNumber = phone
    }

    fun updateAddress(newAddress: String) {
        address = newAddress
    }

    fun updateCity(newCity: String) {
        city = newCity
    }

    fun updateState(newState: String) {
        state = newState
    }

    fun updatePinCode(newPinCode: String) {
        pinCode = newPinCode
    }

    fun fetchLatestEmail() {
        viewModelScope.launch {
            sessionStore.emailFlow.collectLatest {
                _userEmail.value = it.toString()
            }

        }
    }

    fun resetProfileState() {
        _profileState.value = ProfileState.Idle
        StudioImageBitMap = null
        selectedStudioImageUri = null
        studioName = ""
        phoneNumber = ""
        pinCode = ""
        state = ""
        city = ""
        address = ""
    }

    fun onImageSelected(context: Context, uri: Uri) {
        selectedStudioImageUri = uri
        viewModelScope.launch {
            val base64Image = ImageUtils.uriToBase64(context, uri)
            if (base64Image != null) {
                selectedStudioImageBase64 = base64Image
                Log.d("EncodedImage", base64Image)
            } else {
                _profileState.value = ProfileState.Error("Image encoding failed")
            }
        }
    }
    fun saveAdminProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {

                val firebaseId = sessionStore.firebaseIdFlow.firstOrNull().orEmpty()
                val userName = sessionStore.nameFlow.firstOrNull().orEmpty()
                val userEmail = sessionStore.emailFlow.firstOrNull().orEmpty()
                val userType = sessionStore.userTypeFlow.firstOrNull() ?: UserType.ADMIN

                val profileRequest = ProfileRequestDTO(
                    firebaseId = firebaseId,
                    userName = userName,
                    userPhoneNo = phoneNumber,
                    userEmail = userEmail,
                    userType = userType
                )

                val profileError = profileRequest.validate()
                if (profileError != null) {
                    _profileState.value = ProfileState.Error(profileError)
                    return@launch
                }

                val studioRequest = StudioRequestDTO(
                    studioName = studioName,
                    studioAddress = address,
                    studioCity = city,
                    studioState = state,
                    studioPinCode = pinCode,
                    imageDataBase64 = selectedStudioImageBase64
                )

                val studioError = studioRequest.validate()
                if (studioError != null) {
                    _profileState.value = ProfileState.Error(studioError)
                    return@launch
                }

                val adminProfileSetupRequestDTO = AdminProfileSetupRequestDTO(
                    user = profileRequest,
                    studio = studioRequest
                )

                val profileResponse = withContext(Dispatchers.IO) {
                    profileRepository.adminProfileSetup(adminProfileSetupRequestDTO)
                }
                if (profileResponse.data == null) {
                    _profileState.value = ProfileState.Error(
                        (profileResponse.error.message + ": " + profileResponse.error.subErrors.joinToString())
                    )
                    return@launch
                }
                val refreshToken = sessionStore.refreshTokenFlow.firstOrNull().orEmpty()
                val requestDTO = TokenRefreshRequestDTO(refreshToken)

                val networkResponse = withContext(Dispatchers.IO) {
                    authRepository.refreshToken(requestDTO)
                }
                if(networkResponse.data != null){
                    sessionStore.saveTokens(networkResponse.data.accessToken, networkResponse.data.refreshToken)
                    Log.d("ProfileViewModel", "New Tokens Saved")
                }

                _profileState.value = ProfileState.Success(profileResponse.data.studioId, profileResponse.data.userId)
                Log.d("Profile Saved", profileResponse.data.studioId.toString())
                Log.d("Studio Saved", profileResponse.data.userId.toString())

                sessionStore.updateIsRegistered()
                sessionStore.saveStudioId(profileResponse.data.studioId)
                sessionStore.saveUserId(profileResponse.data.userId)

                Log.d("isRegistered Changed", sessionStore.isRegisteredFlow.firstOrNull().toString())

            } catch (e: HttpException) {
                val errorMessage = ApiErrorExtractor.extractMessage(e)
                Log.e("ProfileViewModel", "API Error: $errorMessage")

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
                _profileState.value = ProfileState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }

        }
    }

    fun getStudioImage(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studioId = sessionStore.studioIdFlow.first()

                val response = profileRepository.getStudioImage(studioId)
                if(response.data != null) {
                    StudioImageBitMap = ImageUtils.base64ToBitmap(response.data)
                }

            } catch (e: HttpException) {
                val errorMessage = ApiErrorExtractor.extractMessage(e)
                Log.e("ProfileViewModel", "API Error: $errorMessage")

            } catch (e: Exception){
                Log.e("ProfileViewModel", "Unexpected error in getStudioImage", e)
            }

        }
    }

}
