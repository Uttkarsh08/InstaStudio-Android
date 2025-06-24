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
import com.uttkarsh.InstaStudio.domain.usecase.profile.ProfileUseCases
import com.uttkarsh.InstaStudio.utils.image.ImageUtils
import com.uttkarsh.InstaStudio.utils.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
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
            profileUseCases.fetchLatestEmail().collectLatest {
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
        viewModelScope.launch(Dispatchers.IO) {
            _profileState.value = ProfileState.Loading
            try {
                val response = profileUseCases.saveAdminProfile(
                    phoneNumber = phoneNumber,
                    studioName = studioName,
                    address = address,
                    city = city,
                    state = state,
                    pinCode = pinCode,
                    selectedStudioImageBase64 = selectedStudioImageBase64
                )
                _profileState.value = ProfileState.Success(response.studioId, response.userId)

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Unexpected error in saveAdminProfile", e)
                _profileState.value = ProfileState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }

        }
    }

    fun getStudioImage(){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val base64 = profileUseCases.getStudioImage()
                StudioImageBitMap = ImageUtils.base64ToBitmap(base64)
                _profileState.value = ProfileState.Idle

            }catch (e: Exception) {
                _profileState.value = ProfileState.Error(e.localizedMessage ?: "Failed to load image")
            }

        }
    }

}
