package com.uttkarsh.InstaStudio.utils.states

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import kotlinx.coroutines.flow.Flow

sealed class ResourceState {
        object Idle : ResourceState()
        object Loading : ResourceState()
        data class ListSuccess(val response: List<ResourceResponseDTO>): ResourceState()
        data class PageSuccess(val response: Flow<PagingData<ResourceResponseDTO>>): ResourceState()
        data class Success(val response: ResourceResponseDTO) : ResourceState()
        data class Error(val message: String) : ResourceState()
}