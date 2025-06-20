package com.uttkarsh.InstaStudio.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.states.AddEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionStore: SessionStore
): ViewModel(){

    private val _addEventState = MutableStateFlow<AddEventState>(AddEventState.Idle)
    val addEventState: StateFlow<AddEventState> = _addEventState

    var eventId by mutableLongStateOf(0L)
        private set

    var clientName by mutableStateOf("")
        private set

    var clientPhoneNo by mutableStateOf("")
        private set

    var eventType by mutableStateOf("")
        private set

    var eventStartDate by mutableStateOf("")
        private set

    var eventEndDate by mutableStateOf("")
        private set

    var eventLocation by mutableStateOf("")
        private set

    var eventCity by mutableStateOf("")
        private set

    var eventState by mutableStateOf("")
        private set

    var eventIsSaved by mutableStateOf(false)
        private set

    fun updateEventId(newId: Long) {
        eventId = newId
    }

    fun updateClientName(newName: String) {
        clientName = newName
    }

    fun updateClientPhoneNo(newPhoneNo: String) {
        clientPhoneNo = newPhoneNo
    }

    fun updateEventType(newType: String) {
        eventType = newType
    }

    fun updateEventStartDate(newStartDate: String) {
        eventStartDate = newStartDate
    }

    fun updateEventEndDate(newEndDate: String) {
        eventEndDate = newEndDate
    }

    fun updateEventLocation(newLocation: String) {
        eventLocation = newLocation
    }

    fun updateEventCity(newCity: String) {
        eventCity = newCity
    }

    fun updateEventState(newState: String) {
        eventState = newState
    }

    fun updateEventIsSaved(newIsSaved: Boolean) {
        eventIsSaved = newIsSaved
    }


    fun createNewEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            val studioId = sessionStore.studioIdFlow.first()

            _addEventState.value = AddEventState.Loading

            try {
                val request = EventRequestDTO(
                    clientName = clientName,
                    clientPhoneNo = clientPhoneNo,
                    eventType = eventType,
                    subEventsIds = emptyList(),
                    memberIds = emptyList(),
                    resourceIds = emptyList(),
                    eventStartDate = "2025-06-28T08:00:00",
                    eventEndDate = "2025-06-28T08:00:00",
                    eventLocation = eventLocation,
                    eventCity = eventCity,
                    eventState = eventState,
                    evenIsSaved = eventIsSaved,
                    studioId = studioId
                )

                val response = eventRepository.createNewEvent(request)
                if(response.data != null){
                    _addEventState.value = AddEventState.Success(response.data)
                }

            }catch (e: Exception){
                _addEventState.value = AddEventState.Error(e.localizedMessage ?: "Unknown Error")

            }
        }
    }
}