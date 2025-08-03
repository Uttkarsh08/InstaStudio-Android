package com.uttkarsh.InstaStudio.domain.usecase.resource

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Flow<PagingData<ResourceResponseDTO>> {
        val studioId = sessionManager.getStudioId()
        return resourceRepository.getAllResources(studioId)
    }
}
