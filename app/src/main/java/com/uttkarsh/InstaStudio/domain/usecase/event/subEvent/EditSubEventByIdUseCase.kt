package com.uttkarsh.InstaStudio.domain.usecase.event.subEvent

import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditSubEventByIdUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager,
    private val timeProvider: TimeProvider
) {
    suspend operator fun invoke(
        subEventId: Long,
        eventType: String,
        memberIds: Set<Long>,
        resourceIds: Set<Long>,
        eventStartDate: String,
        eventEndDate: String,
        eventLocation: String,
        eventCity: String,
        eventState: String
    ): SubEventResponseDTO = withContext(Dispatchers.IO){
        val studioId = sessionManager.getStudioId()

        val request = SubEventRequestDTO(
            eventType = eventType,
            memberIds = memberIds,
            resourceIds = resourceIds,
            eventStartDate = eventStartDate,
            eventEndDate = eventEndDate,
            eventLocation = eventLocation,
            eventCity = eventCity,
            eventState = eventState,
            studioId = studioId
        )

         request.validate(timeProvider)?.let { throw IllegalArgumentException(it) }

        val response = eventRepository.editSubEventById( subEventId, request)

        response.data ?: throw Exception(response.error.message)
    }
}