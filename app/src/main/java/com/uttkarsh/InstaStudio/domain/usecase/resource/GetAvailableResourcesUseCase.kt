package com.uttkarsh.InstaStudio.domain.usecase.resource

import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import java.time.LocalDateTime
import javax.inject.Inject

class GetAvailableResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(startDate: LocalDateTime, endDate: LocalDateTime): List<ResourceResponseDTO> {
        val studioId = sessionManager.getStudioId()
        val response = resourceRepository.getAvailableResources(studioId, startDate, endDate)

        return response.data ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")

    }
}