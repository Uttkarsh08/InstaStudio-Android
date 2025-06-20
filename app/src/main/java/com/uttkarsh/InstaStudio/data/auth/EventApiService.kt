package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.PagedResponse
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO
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

    @POST("/api/v1/register/sub-event")
    suspend fun registerSubEvent(
        @Body request: SubEventRequestDTO
    ): ApiResponse<SubEventResponseDTO>

    @GET("/api/v1/{studioId}/sub-event/{eventId}")
    suspend fun getSubEventById(
        @Path("studioId") studioId: Long,
        @Path("eventId") eventId: Long,
    ): ApiResponse<SubEventResponseDTO>

}