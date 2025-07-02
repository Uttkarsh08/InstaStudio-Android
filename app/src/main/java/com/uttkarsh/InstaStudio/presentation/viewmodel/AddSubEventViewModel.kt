package com.uttkarsh.InstaStudio.presentation.viewmodel

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
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.AddSubEventUseCases
import com.uttkarsh.InstaStudio.utils.states.AddSubEventState
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.Long

@HiltViewModel
class AddSubEventViewModel @Inject constructor(
    private val addSubEventUseCases: AddSubEventUseCases,
    private val timeProvider: TimeProvider
): ViewModel(){

    private val _addSubEventState = MutableStateFlow<AddSubEventState>(AddSubEventState.Idle)
    val addSubEventState: StateFlow<AddSubEventState> = _addSubEventState


    var subEventId by mutableLongStateOf(0L)
        private set

    var subEventStartDate by mutableStateOf(timeProvider.nowDate())
        private set

    var subEventEndDate by mutableStateOf(timeProvider.nowDate())
        private set

    var subEventStartTime by mutableStateOf(timeProvider.nowTime())
        private set

    var subEventEndTime by mutableStateOf(timeProvider.nowTime())
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

    var shouldResetAddSubEventScreen by mutableStateOf(true)
        private set

    fun resetAddSubEventScreen(){
        if(shouldResetAddSubEventScreen){
            resetAddSubEventState()
            resetSubEventDetails()
            shouldResetAddSubEventScreen = false
        }
    }

    fun markAddSubEventScreenForReset() {
        shouldResetAddSubEventScreen = true
    }

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
        subEventStartDate = timeProvider.nowDate()
        subEventEndDate = timeProvider.nowDate()
        subEventStartTime = timeProvider.nowTime()
        subEventEndTime = timeProvider.nowTime()
        subEventLocation = ""
        subEventCity = ""
        subEventState = ""
        datePickerTarget = null
        timePickerTarget = null
    }

    fun createNewSubEvent(){
        viewModelScope.launch(Dispatchers.IO) {

            _addSubEventState.value = AddSubEventState.Loading

            val subEventStart = subEventStartDate+"T"+subEventStartTime
            val subEventEnd = subEventEndDate+"T"+subEventEndTime

            try {


                val response = addSubEventUseCases.createNewSubEvent(
                    eventType= _selectedSubEventType.value.toString(),
                    memberIds= emptySet(),
                    resourceIds= emptySet(),
                    eventStartDate= subEventStart,
                    eventEndDate= subEventEnd,
                    eventLocation= subEventLocation,
                    eventCity= subEventCity,
                    eventState= subEventState
                )

                _addSubEventState.value = AddSubEventState.Success(response)

            } catch (e: Exception) {
                _addSubEventState.value = AddSubEventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }

}