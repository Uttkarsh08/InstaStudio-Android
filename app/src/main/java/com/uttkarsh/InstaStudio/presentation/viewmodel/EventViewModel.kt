package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.states.EventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionStore: SessionStore
): ViewModel(){

    private val _upcomingEventState = MutableStateFlow<EventState>(EventState.Idle)
    val upcomingEventState: StateFlow<EventState> = _upcomingEventState.asStateFlow()

    private val _completedEventState = MutableStateFlow<EventState>(EventState.Idle)
    val completedEventState: StateFlow<EventState> = _completedEventState.asStateFlow()

    private val _eventState = MutableStateFlow<EventState>(EventState.Idle)
    val eventState: StateFlow<EventState> = _eventState.asStateFlow()

    var hasLoadedUpcoming by mutableStateOf(false)
        private set

    var hasLoadedCompleted by mutableStateOf(false)
        private set

    var hasLoadedNextUpcomingEvent by mutableStateOf(false)
        private set

    var eventId by mutableLongStateOf(0L)
        private set

    fun updateEventId(newId: Long){
        eventId = newId
    }

    fun loadNextUpcomingEventIfNeeded(){
        if(!hasLoadedNextUpcomingEvent){
            hasLoadedNextUpcomingEvent = true
            getNextUpcomingEvent()
        }
    }

    fun loadUpcomingEventsIfNeeded() {
        if (!hasLoadedUpcoming) {
            hasLoadedUpcoming = true
            getUpcomingEvents()
        }
    }

    fun loadCompletedEventsIfNeeded() {
        if (!hasLoadedCompleted) {
            hasLoadedCompleted = true
            getCompletedEvents()
        }
    }

    fun resetHasLoadedFlags() {
        hasLoadedUpcoming = false
        hasLoadedCompleted = false
    }

    fun resetHasLoadedNextUpcomingEvent(){
        hasLoadedNextUpcomingEvent = false
    }

    fun getUpcomingEvents() {
        Log.d("EventViewModel", "getUpcomingEvents called")
        viewModelScope.launch(Dispatchers.IO) {
            _upcomingEventState.value = EventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()
                val response = eventRepository.getUpcomingEvents(studioId)
                    .cachedIn(viewModelScope)
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

                _upcomingEventState.value = EventState.UpcomingPagingSuccess(response)

            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _upcomingEventState.value = EventState.Error(message)

            } catch (e: Exception) {
                _upcomingEventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }

    fun getCompletedEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _completedEventState.value = EventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()
                val response = eventRepository.getCompletedEvents(studioId)
                    .cachedIn(viewModelScope)
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

                _completedEventState.value = EventState.CompletedPagingSuccess(response)

            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _completedEventState.value = EventState.Error(message)

            } catch (e: Exception) {
                _completedEventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }

    fun getNextUpcomingEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            _eventState.value = EventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()

                val response = eventRepository.getNextUpcomingEvent(studioId)

                Log.d("NextUpcomingEvent", "Data: ${response.data}, Error: ${response.error}")

                if(response.data != null){
                    _eventState.value = EventState.Success(response.data)
                }else{
                    _eventState.value = EventState.Error(response.error.message)
                }
            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _eventState.value = EventState.Error(message)

            } catch (e: Exception) {
                _eventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }

    fun getEventById() {
        viewModelScope.launch(Dispatchers.IO) {
            _eventState.value = EventState.Loading
            Log.d("EventById", "Loading state set")

            try {
                val studioId = sessionStore.studioIdFlow.first()
                Log.d("EventById", "Studio ID: $studioId, Event ID: $eventId")

                val response = eventRepository.getEventById(studioId, eventId)

                Log.d("EventById", "Raw response: $response")
                Log.d("EventById", "Response data: ${response.data}")
                Log.d("EventById", "Response error: ${response.error}")

                if (response.data != null) {
                    _eventState.value = EventState.Success(response.data)
                    Log.d("EventById", "EventState set to Success")
                } else {
                    _eventState.value = EventState.Error(response.error?.message ?: "Unknown error")
                    Log.d("EventById", "EventState set to Error with error: ${response.error?.message}")
                }

            } catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _eventState.value = EventState.Error(message)
                Log.d("EventById", "HttpException: $message")

            } catch (e: Exception) {
                _eventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
                Log.d("EventById", "Exception: ${e.localizedMessage}")
            }
        }
    }
}