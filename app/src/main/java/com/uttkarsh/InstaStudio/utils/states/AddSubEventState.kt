package com.uttkarsh.InstaStudio.utils.states

import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO

sealed class AddSubEventState {
    object Idle : AddSubEventState()
    object Loading : AddSubEventState()
    object DeletionSuccess : AddSubEventState()
    data class Success(val response: SubEventResponseDTO) : AddSubEventState()
    data class Error(val message: String) : AddSubEventState()

}