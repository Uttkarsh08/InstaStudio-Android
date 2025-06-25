package com.uttkarsh.InstaStudio.domain.usecase.event.addEvent

import android.os.Build
import androidx.annotation.RequiresApi
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateNewEventUseCase(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        clientName: String,
        clientPhoneNo: String,
        eventType: String,
        subEventIds: Set<Long>,
        eventStart: String,
        eventEnd: String,
        eventLocation: String,
        eventCity: String,
        eventState: String,
        evenIsSaved: Boolean
    ): EventResponseDTO = withContext(Dispatchers.IO) {

        val studioId = sessionManager.getStudioId()
        val request = EventRequestDTO(
            clientName = clientName,
            clientPhoneNo = clientPhoneNo,
            eventType = eventType,
            subEventsIds = subEventIds,
            memberIds = emptySet(),
            resourceIds = emptySet(),
            eventStartDate = eventStart,
            eventEndDate = eventEnd,
            eventLocation = eventLocation,
            eventCity = eventCity,
            eventState = eventState,
            evenIsSaved = evenIsSaved,
            studioId = studioId
        )

        request.validate()?.let {
            throw IllegalArgumentException(it)
        }

        val response = eventRepository.createNewEvent(request)

        val data  = response.data
        ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

        return@withContext  data
    }
}
