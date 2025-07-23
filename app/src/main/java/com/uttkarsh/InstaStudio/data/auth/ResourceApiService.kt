package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.PagedResponse
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime
import javax.inject.Singleton

@Singleton
interface ResourceApiService {

    @POST("/api/v1/register/resource")
    suspend fun registerResource(
        @Body request: ResourceRequestDTO
    ): ApiResponse<ResourceResponseDTO>

    @GET("/api/v1/{studioId}/all-resources")
    suspend fun getAllResources(
        @Path("studioId") studioId: Long,
        @Query("PageNumber") page: Int,
    ): ApiResponse<PagedResponse<ResourceResponseDTO>>

    @PUT("/api/v1/{studioId}/edit-resource/{resourceId}")
    suspend fun updateResourceById(
        @Path("studioId") studioId: Long,
        @Path("resourceId") resourceId: Long,
        @Body request: ResourceRequestDTO

    ): ApiResponse<ResourceResponseDTO>

    @GET("/api/v1/{studioId}/available-resources")
    suspend fun getAvailableResources(
        @Path("studioId") studioId: Long,
        @Query("startDate") startDate: LocalDateTime,
        @Query("endDate") endDate : LocalDateTime
    ): ApiResponse<List<ResourceResponseDTO>>
}
