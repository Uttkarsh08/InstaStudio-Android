package com.uttkarsh.InstaStudio.utils.states

import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO

sealed class SubEventState {
    object Idle : SubEventState()
    object Loading : SubEventState()
    data class Success(val response: SubEventResponseDTO) : SubEventState()
    data class Error(val message: String) : SubEventState()

}