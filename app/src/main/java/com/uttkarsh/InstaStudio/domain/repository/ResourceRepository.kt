package com.uttkarsh.InstaStudio.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.data.auth.ResourceApiService
import com.uttkarsh.InstaStudio.data.pagination.ResourcePagingSource
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceRepository @Inject constructor(
    private val resourceApi: ResourceApiService,
){

    suspend fun createNewResource(request: ResourceRequestDTO): ApiResponse<ResourceResponseDTO>{
        return resourceApi.registerResource(request)
    }

    fun getAllResources(studioId: Long): Flow<PagingData<ResourceResponseDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ResourcePagingSource(resourceApi, studioId)
            }
        ).flow
    }

    suspend fun updateResourceById(
        studioId: Long,
        resourceId: Long,
        request: ResourceRequestDTO
    ): ApiResponse<ResourceResponseDTO>{
        return resourceApi.updateResourceById(studioId, resourceId, request)
    }

}