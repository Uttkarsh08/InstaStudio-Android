package com.uttkarsh.InstaStudio.utils.states

import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO

sealed class AddEventState {
    object Idle : AddEventState()
    object Loading : AddEventState()
    data class Success(val response: EventResponseDTO) : AddEventState()
    data class Error(val message: String) : AddEventState()

}