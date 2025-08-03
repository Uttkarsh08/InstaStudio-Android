package com.uttkarsh.InstaStudio.domain.usecase.resource

import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import javax.inject.Inject

class UpdateResourceUseCase @Inject constructor(
    private val repository: ResourceRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(resourceId: Long, resourceName: String, resourcePrice: Long): ResourceResponseDTO {
        val studioId = sessionManager.getStudioId()
        val request = ResourceRequestDTO(resourceName, resourcePrice, studioId)

        request.validate()?.let { throw IllegalArgumentException(it) }

        val response = repository.updateResourceById(studioId, resourceId, request)

        return response.data ?: throw Exception(response.error.message)
    }
}
