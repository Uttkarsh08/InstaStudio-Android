package com.uttkarsh.InstaStudio.utils.states

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import kotlinx.coroutines.flow.Flow

sealed class EventState {
    object Idle : EventState()
    object Loading : EventState()
    data class Success(val response: EventResponseDTO) : EventState()
    data class PagingSuccess(val data: Flow<PagingData<EventListResponseDTO>>) : EventState()
    data class Error(val message: String) : EventState()

}