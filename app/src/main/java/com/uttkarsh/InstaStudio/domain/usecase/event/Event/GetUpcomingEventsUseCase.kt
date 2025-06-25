package com.uttkarsh.InstaStudio.domain.usecase.event.Event

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.flow.Flow

class GetUpcomingEventsUseCase(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Flow<PagingData<EventListResponseDTO>> {
        val studioId = sessionManager.getStudioId()
        return eventRepository.getUpcomingEvents(studioId)
    }
}
