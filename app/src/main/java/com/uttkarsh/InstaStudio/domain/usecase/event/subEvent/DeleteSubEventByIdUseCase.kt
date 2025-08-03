package com.uttkarsh.InstaStudio.domain.usecase.event.subEvent

import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteSubEventByIdUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(subEventId: Long
    ): Unit = withContext(Dispatchers.IO) {
        val studioId = sessionManager.getStudioId()
        eventRepository.deleteSubEventById(studioId, subEventId)
    }
}