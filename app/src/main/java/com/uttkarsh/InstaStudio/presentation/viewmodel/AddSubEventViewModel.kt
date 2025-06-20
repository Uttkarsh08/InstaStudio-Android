package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.SubEventType
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventRequestDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.states.AddEventState
import com.uttkarsh.InstaStudio.utils.states.AddSubEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddSubEventViewModel @Inject constructor(
    private val evenRepository: EventRepository,
    private val sessionStore: SessionStore
): ViewModel(){

    private val _addSubEventState = MutableStateFlow<AddSubEventState>(AddSubEventState.Idle)
    val addSubEventState: StateFlow<AddSubEventState> = _addSubEventState

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US)

    var subEventId by mutableLongStateOf(0L)
        private set

    var subEventStartDate by mutableStateOf(LocalDate.now().format(dateFormatter))
        private set

    var subEventEndDate by mutableStateOf(LocalDate.now().format(dateFormatter))
        private set

    var subEventStartTime by mutableStateOf(LocalTime.now().format(timeFormatter))
        private set

    var subEventEndTime by mutableStateOf(LocalTime.now().format(timeFormatter))
        private set

    var datePickerTarget by mutableStateOf<DatePickerTarget?>(null)
        private set

    var timePickerTarget by mutableStateOf<TimePickerTarget?>(null)
        private set


    var subEventLocation by mutableStateOf("")
        private set

    var subEventCity by mutableStateOf("")
        private set

    var subEventState by mutableStateOf("")
        private set

    private val _subEventTypes = SubEventType.entries
    val subEventTypes: List<SubEventType> = _subEventTypes

    private val _selectedSubEventType = mutableStateOf(SubEventType.WEDDING)
    val selectedSubEventType: State<SubEventType> = _selectedSubEventType

    private val _subEventTypeDropdownExpanded = mutableStateOf(false)
    val subEventTypeDropdownExpanded: State<Boolean> = _subEventTypeDropdownExpanded


    fun updateSubEventId(id: Long){
        subEventId = id
    }

    fun updateSubEventType(subEventType: SubEventType) {
        _selectedSubEventType.value = subEventType
    }

    fun updateSubEventLocation(newLocation: String) {
        subEventLocation = newLocation
    }

    fun updateSubEventCity(newCity: String) {
        subEventCity = newCity
    }

    fun updateSubEventState(newState: String) {
        subEventState = newState
    }

    fun onDateBoxClick(target: DatePickerTarget) {
        datePickerTarget = target
    }

    fun onDatePicked(date: String) {
        when (datePickerTarget) {
            DatePickerTarget.START_DATE -> {
                subEventStartDate = date
            }
            DatePickerTarget.END_DATE -> {
                subEventEndDate = date
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
            TimePickerTarget.START_TIME -> subEventStartTime = date
            TimePickerTarget.END_TIME -> subEventEndTime = date
            null -> {}
        }
        timePickerTarget = null
    }

    fun resetAddSubEventState() {
        _addSubEventState.value = AddSubEventState.Idle
    }

    fun onSubEventTypeSelected(type: SubEventType) {
        _selectedSubEventType.value = type
        _subEventTypeDropdownExpanded.value = false
        updateSubEventType(type)
    }

    fun toggleSubEventTypeDropdown() {
        _subEventTypeDropdownExpanded.value = !_subEventTypeDropdownExpanded.value
    }

    fun closeSubEventTypeDropdown() {
        _subEventTypeDropdownExpanded.value = false
    }

    fun resetSubEventDetails(){
        subEventId =0L
        _selectedSubEventType.value = SubEventType.WEDDING
        subEventStartDate = LocalDate.now().format(dateFormatter)
        subEventEndDate = LocalDate.now().format(dateFormatter)
        subEventStartTime = LocalTime.now().format(timeFormatter)
        subEventEndTime = LocalTime.now().format(timeFormatter)
        subEventLocation = ""
        subEventCity = ""
        subEventState = ""
        datePickerTarget = null
        timePickerTarget = null
    }

    fun createNewSubEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            val studioId = sessionStore.studioIdFlow.first()

            _addSubEventState.value = AddSubEventState.Loading

            val subEventStart = subEventStartDate+"T"+subEventStartTime
            val subEventEnd = subEventEndDate+"T"+subEventEndTime

            try {
                val request = SubEventRequestDTO(
                    eventType = selectedSubEventType.value.toString(),
                    memberIds = emptySet(),
                    resourceIds = emptySet(),
                    eventStartDate = subEventStart,
                    eventEndDate = subEventEnd,
                    eventLocation = subEventLocation,
                    eventCity = subEventCity,
                    eventState = subEventState,
                    studioId = studioId
                )

                val response = evenRepository.createNewSubEvent(request)

                if(response.data != null){
                    _addSubEventState.value = AddSubEventState.Success(response.data)
                }

            }catch (e: Exception){
                _addSubEventState.value = AddSubEventState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun getSubEventById(){
        viewModelScope.launch(Dispatchers.IO) {
            val studioId = sessionStore.studioIdFlow.first()

            _addSubEventState.value = AddSubEventState.Loading

            try {
                val response = evenRepository.getSubEventById(studioId, subEventId)

                if(response.data != null){

                    _addSubEventState.value = AddSubEventState.Success(response.data)
                }else {
                    _addSubEventState.value = AddSubEventState.Error(response.error.message)
                }
            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _addSubEventState.value = AddSubEventState.Error(message)

            } catch (e: Exception) {
                _addSubEventState.value = AddSubEventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }


}