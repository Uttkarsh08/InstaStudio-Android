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

    private val _eventState = MutableStateFlow<EventState>(EventState.Idle)
    val eventState  = _eventState.asStateFlow()


    init {
        Log.d("EventViewModel", "EventViewModel created")
        getUpcomingEvents()
    }

    fun getUpcomingEvents() {
        Log.d("EventViewModel", "getUpcomingEvents called")
        viewModelScope.launch(Dispatchers.IO) {
            _eventState.value = EventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()
                val response = eventRepository.getUpcomingEvents(studioId)
                    .cachedIn(viewModelScope)
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

                _eventState.value = EventState.PagingSuccess(response)

            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _eventState.value = EventState.Error(message)

            } catch (e: Exception) {
                _eventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }

    fun getCompletedEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _eventState.value = EventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()
                val response = eventRepository.getCompletedEvents(studioId)
                    .cachedIn(viewModelScope)
                    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

                _eventState.value = EventState.PagingSuccess(response)

            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _eventState.value = EventState.Error(message)

            } catch (e: Exception) {
                _eventState.value = EventState.Error(e.localizedMessage ?: "Unexpected error occurred")
            }
        }
    }
}