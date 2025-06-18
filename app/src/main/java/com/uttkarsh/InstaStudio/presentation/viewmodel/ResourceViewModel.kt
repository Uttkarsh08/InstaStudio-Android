package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val sessionStore: SessionStore
): ViewModel() {

    private val _resourceState = MutableStateFlow<ResourceState>(ResourceState.Idle)
    val resourceState = _resourceState.asStateFlow()

    var resourceId by mutableLongStateOf(0L)
        private set

    private val _resourceName = MutableStateFlow("")
    val resourceName: StateFlow<String> = _resourceName

    private val _resourcePrice = MutableStateFlow(0L)
    val resourcePrice: StateFlow<Long> = _resourcePrice

    fun updateResourceName(newName: String) {
        Log.d("VIEWMODEL", "Name updating to: $newName")
        _resourceName.value = newName
        Log.d("VIEWMODEL", "Name updated to: ${_resourceName.value}")
    }

    fun updateResourcePrice(newPrice: Long) {
        _resourcePrice.value = newPrice
    }


    fun updateResourceId(newId: Long){
        resourceId = newId
    }


    init {
        getAllResources()
    }

    fun getAllResources(){
        viewModelScope.launch(Dispatchers.IO) {
            _resourceState.value = ResourceState.Loading
            val id = sessionStore.studioIdFlow.first()

            val response = resourceRepository.getAllResources(id)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

            _resourceState.value = ResourceState.ListSuccess(response)
        }
    }


    fun createNewResource(){
        viewModelScope.launch(Dispatchers.IO){
            _resourceState.value = ResourceState.Loading
            try {
                val studioId = sessionStore.studioIdFlow.first()
                val resourceRequest = ResourceRequestDTO(
                    resourceName = _resourceName.value,
                    resourcePrice = _resourcePrice.value,
                    studioId = studioId
                )
                val response = resourceRepository.createNewResource(resourceRequest)
                if(response.data != null){
                    _resourceState.value = ResourceState.Success(response.data)

                } else {
                    _resourceState.value = ResourceState.Error(response.error.message)
                }
            } catch (e: Exception){
                _resourceState.value = ResourceState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }
        }
    }

    fun updateResourceById(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _resourceState.value = ResourceState.Loading
                val studioId = sessionStore.studioIdFlow.first()
                val request = ResourceRequestDTO(
                    resourceName = _resourceName.value,
                    resourcePrice = _resourcePrice.value,
                    studioId = studioId
                )
                Log.d("UPDATE", "Sending values: ${_resourceName.value}, ${_resourcePrice.value}")
                val response = resourceRepository.updateResourceById(studioId, resourceId, request)
                if(response.data != null){
                    _resourceState.value = ResourceState.Success(response.data)
                }else {
                    _resourceState.value = ResourceState.Error(response.error.message)
                }
            }catch (e: Exception){
                _resourceState.value = ResourceState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }
        }
    }

    fun prepareEditResource(resource: ResourceResponseDTO) {
        updateResourceId(resource.resourceId)
        updateResourceName(resource.resourceName)
        updateResourcePrice(resource.resourcePrice)
    }
    fun clearResourceValues() {
        updateResourceId(0L)
        updateResourceName("")
        updateResourcePrice(0L)

    }

}