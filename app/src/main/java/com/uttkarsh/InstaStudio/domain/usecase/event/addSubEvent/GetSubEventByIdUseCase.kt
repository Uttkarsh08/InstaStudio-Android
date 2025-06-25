package com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent

import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSubEventByIdUseCase(
    private val eventRepository: EventRepository,
    private val sessionManager: SessionManager
) {

    suspend operator fun invoke(
        subEventId: Long
    ): SubEventResponseDTO = withContext(Dispatchers.IO){

        val studioId = sessionManager.getStudioId()

        val response = eventRepository.getSubEventById(studioId, subEventId)

        val data = response.data
            ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

        return@withContext data

    }

}