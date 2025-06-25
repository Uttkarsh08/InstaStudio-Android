package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uttkarsh.InstaStudio.domain.usecase.event.subEvent.SubEventUseCases
import com.uttkarsh.InstaStudio.utils.states.SubEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubEventViewModel  @Inject constructor(
    private val subEventUseCases: SubEventUseCases
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

                val response = subEventUseCases.getSubEventById(subEventId)

                _subEventState.value = SubEventState.Success(response)


            }catch (e: Exception) {
                _subEventState.value = SubEventState.Error(e.localizedMessage ?: "Unexpected error occurred")
                Log.d("EventById", "Exception: ${e.localizedMessage}")
            }
        }
    }
}