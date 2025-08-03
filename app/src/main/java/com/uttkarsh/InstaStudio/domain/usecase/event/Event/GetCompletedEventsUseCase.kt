package com.uttkarsh.InstaStudio.domain.usecase.event.Event

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompletedEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Flow<PagingData<EventListResponseDTO>> {
        val studioId = sessionManager.getStudioId()
        return eventRepository.getCompletedEvents(studioId)
    }
}
