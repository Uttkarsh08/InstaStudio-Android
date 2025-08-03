package com.uttkarsh.InstaStudio.domain.usecase.event.Event

import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(eventId: Long
    ): EventResponseDTO = withContext(Dispatchers.IO){

        val studioId = sessionManager.getStudioId()
        val response = eventRepository.getEventById(studioId, eventId)

        val data = response.data
            ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

        return@withContext data
    }
}
