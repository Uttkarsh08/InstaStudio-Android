package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.EventType
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.states.AddEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import androidx.compose.runtime.State

@RequiresApi(Build.VERSION_CODES.O)
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

    var eventStartDate by mutableStateOf(LocalDate.now().toString())
        private set

    var eventEndDate by mutableStateOf(LocalDate.now().toString())
        private set

    var eventStartTime by mutableStateOf(LocalTime.now().toString())
        private set

    var eventEndTime by mutableStateOf(LocalTime.now().toString())
        private set

    var eventLocation by mutableStateOf("")
        private set

    var eventCity by mutableStateOf("")
        private set

    var eventState by mutableStateOf("")
        private set

    var eventIsSaved by mutableStateOf(false)
        private set

    var datePickerTarget by mutableStateOf<DatePickerTarget?>(null)
        private set

    var timePickerTarget by mutableStateOf<TimePickerTarget?>(null)
        private set

    private val _eventTypes = EventType.entries
    val eventTypes: List<EventType> = _eventTypes

    private val _selectedEventType = mutableStateOf(EventType.WEDDING)
    val selectedEventType: State<EventType> = _selectedEventType

    private val _eventTypeDropdownExpanded = mutableStateOf(false)
    val eventTypeDropdownExpanded: State<Boolean> = _eventTypeDropdownExpanded


    fun updateClientName(newName: String) {
        clientName = newName
    }

    fun updateClientPhoneNo(newPhoneNo: String) {
        clientPhoneNo = newPhoneNo
    }

    fun updateEventType(eventType: EventType) {
        _selectedEventType.value = eventType
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

    fun onDateBoxClick(target: DatePickerTarget) {
        datePickerTarget = target
    }

    fun onDatePicked(date: String) {
        when (datePickerTarget) {
            DatePickerTarget.START_DATE -> {
                eventStartDate = date
                timePickerTarget = TimePickerTarget.START_TIME
            }
            DatePickerTarget.END_DATE -> {
                eventEndDate = date
                timePickerTarget = TimePickerTarget.END_TIME
            }
            null -> {}
        }
        datePickerTarget = null
    }

    fun onTimeBoxClick(target: TimePickerTarget) {
        timePickerTarget = target
    }

    fun onTimePicked(date: String) {
        when (timePickerTarget) {
            TimePickerTarget.START_TIME -> eventStartTime = date
            TimePickerTarget.END_TIME -> eventEndTime = date
            null -> {}
        }
        timePickerTarget = null
    }

    fun resetAddEventState() {
        _addEventState.value = AddEventState.Idle
    }

    fun onEventTypeSelected(type: EventType) {
        _selectedEventType.value = type
        _eventTypeDropdownExpanded.value = false
        updateEventType(type)
    }

    fun toggleEventTypeDropdown() {
        _eventTypeDropdownExpanded.value = !_eventTypeDropdownExpanded.value
    }

    fun closeEventTypeDropdown() {
        _eventTypeDropdownExpanded.value = false
    }

    fun resetEventDetails(){
        eventId = 0L
        clientName = ""
        clientPhoneNo = ""
        _selectedEventType.value = EventType.WEDDING
        eventStartDate = ""
        eventEndDate = ""
        eventLocation = ""
        eventCity = ""
        eventState = ""
        eventIsSaved = false
        datePickerTarget = null
        timePickerTarget = null
    }


    fun createNewEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            val studioId = sessionStore.studioIdFlow.first()

            _addEventState.value = AddEventState.Loading

            val eventStart = eventStartDate+"T"+eventStartTime
            val eventEnd = eventEndDate+"T"+eventEndTime

            try {
                val request = EventRequestDTO(
                    clientName = clientName,
                    clientPhoneNo = clientPhoneNo,
                    eventType = _selectedEventType.value.toString(),
                    subEventsIds = emptyList(),
                    memberIds = emptyList(),
                    resourceIds = emptyList(),
                    eventStartDate = eventStart,
                    eventEndDate = eventEnd,
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