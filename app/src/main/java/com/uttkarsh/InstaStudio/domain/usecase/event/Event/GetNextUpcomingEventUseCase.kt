package com.uttkarsh.InstaStudio.domain.usecase.event.Event

import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNextUpcomingEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke()
    : EventResponseDTO = withContext(Dispatchers.IO){

        val response = eventRepository.getNextUpcomingEvent(sessionManager.getStudioId())

        val data = response.data
            ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

        return@withContext data
    }
}
