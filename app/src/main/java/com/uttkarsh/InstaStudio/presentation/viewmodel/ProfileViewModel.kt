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
import com.uttkarsh.InstaStudio.domain.model.AdminProfileSetupRequestDTO
import com.uttkarsh.InstaStudio.domain.model.ProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.StudioRequestDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.image.ImageUtils
import com.uttkarsh.InstaStudio.utils.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sessionStore: SessionStore
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState = _profileState.asStateFlow()

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail = _userEmail.asStateFlow()

    private val _isAuthRefreshed = MutableStateFlow(false)
    val isAuthRefreshed: StateFlow<Boolean> = _isAuthRefreshed.asStateFlow()

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
            val email = sessionStore.getEmail().orEmpty()
            Log.d("ProfileViewModel", "Fetched email: $email")
            _userEmail.value = email
        }
    }

    fun resetAuthRefreshFlag() {
        _isAuthRefreshed.value = false
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
                val profileRequest = ProfileRequestDTO(
                    firebaseId = sessionStore.getFirebaseId().toString(),
                    userName = sessionStore.getName().toString(),
                    userPhoneNo = phoneNumber,
                    userEmail = sessionStore.getEmail().toString(),
                    userType = sessionStore.getUserType()
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

                val response = withContext(Dispatchers.IO) {
                    repository.adminProfileSetup(adminProfileSetupRequestDTO)
                }
                if (response.data == null) {
                    _profileState.value = ProfileState.Error(
                        (response.error.message + ": " + response.error.subErrors.joinToString())
                    )
                    return@launch
                }

                _profileState.value = ProfileState.Success(response.data.studioId, response.data.userId)
                Log.d("Profile Saved", response.data.studioId.toString())
                Log.d("Studio Saved", response.data.userId.toString())

                _isAuthRefreshed.value = false

                withContext(Dispatchers.IO) {
                    sessionStore.updateIsRegistered()
                    sessionStore.saveStudioId(response.data.studioId)
                    sessionStore.saveUserId(response.data.userId)
                }

                _isAuthRefreshed.value = true
                Log.d("isRegistered Changed", sessionStore.getIsRegistered().toString())

            } catch (e: HttpException) {
                val errorMessage = ApiErrorExtractor.extractMessage(e)
                Log.e("ProfileViewModel", "API Error: $errorMessage")

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
                _profileState.value = ProfileState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }

        }
    }

    fun resetProfileState() {
        _profileState.value = ProfileState.Idle
    }

    fun getStudioImage(){
        viewModelScope.launch {
            try {
                val studioId = sessionStore.getStudioId()
                val response = repository.getStudioImage(studioId)
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
