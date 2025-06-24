package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.states.SubEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SubEventViewModel  @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionStore: SessionStore
): ViewModel() {

    private val _subEventState = MutableStateFlow<SubEventState>(SubEventState.Idle)
    val subEventState: StateFlow<SubEventState> = _subEventState.asStateFlow()

    var subEventId by mutableLongStateOf(0L)
        private set

    fun updateSubEventId(newId: Long){
        subEventId = newId
    }

    fun getSubEventById(){
        viewModelScope.launch(Dispatchers.IO) {
            _subEventState.value = SubEventState.Loading

            try {
                val studioId = sessionStore.studioIdFlow.first()

                val response = eventRepository.getSubEventById(studioId, subEventId)

                if (response.data != null) {
                    _subEventState.value = SubEventState.Success(response.data)
                } else {
                    _subEventState.value = SubEventState.Error(response.error.message)
                }


            }catch (e: HttpException) {
                val message = ApiErrorExtractor.extractMessage(e)
                _subEventState.value = SubEventState.Error(message)
                Log.d("EventById", "HttpException: $message")

            } catch (e: Exception) {
                _subEventState.value = SubEventState.Error(e.localizedMessage ?: "Unexpected error occurred")
                Log.d("EventById", "Exception: ${e.localizedMessage}")
            }
        }
    }
}