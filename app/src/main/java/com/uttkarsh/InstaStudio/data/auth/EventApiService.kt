package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.PagedResponse
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface EventApiService {

    @POST("/api/v1/register/event")
    suspend fun registerEvent(
        @Body request: EventRequestDTO
    ): ApiResponse<EventResponseDTO>

    @POST("/api/v1/register/sub-event")
    suspend fun registerSubEvent(
        @Body request: EventRequestDTO
    ): ApiResponse<EventResponseDTO>

    @GET("/api/v1/{studioId}/upcoming-events")
    suspend fun getUpcomingEvents(
        @Path("studioId") studioId: Long,
        @Query("PageNumber") page: Int,
    ): ApiResponse<PagedResponse<EventListResponseDTO>>

    @GET("/api/v1/{studioId}/completed-events")
    suspend fun getCompletedEvents(
        @Path("studioId") studioId: Long,
        @Query("PageNumber") page: Int,
    ): ApiResponse<PagedResponse<EventListResponseDTO>>

}