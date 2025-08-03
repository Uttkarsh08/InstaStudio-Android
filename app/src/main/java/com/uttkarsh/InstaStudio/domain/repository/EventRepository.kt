package com.uttkarsh.InstaStudio.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.data.auth.EventApiService
import com.uttkarsh.InstaStudio.data.pagination.CompletedEventPagingSource
import com.uttkarsh.InstaStudio.data.pagination.UpcomingEventPagingSource
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventApi: EventApiService
) {

    //Events

    suspend fun createNewEvent(request: EventRequestDTO): ApiResponse<EventResponseDTO> {
        return eventApi.registerEvent(request)
    }


    fun getUpcomingEvents(studioId: Long): Flow<PagingData<EventListResponseDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { UpcomingEventPagingSource(eventApi, studioId) }
        ).flow
    }

    fun getCompletedEvents(studioId: Long): Flow<PagingData<EventListResponseDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CompletedEventPagingSource(eventApi, studioId)}
        ).flow
    }

    suspend fun getNextUpcomingEvent(studioId: Long): ApiResponse<EventResponseDTO>{
        return eventApi.getNextUpcomingEvent(studioId)
    }

    suspend fun getEventById(studioId: Long, eventId: Long): ApiResponse<EventResponseDTO>{
        return eventApi.getEventById(studioId, eventId)
    }

    //Sub-Events

    suspend fun createNewSubEvent(request: SubEventRequestDTO): ApiResponse<SubEventResponseDTO> {
        return eventApi.registerSubEvent(request)
    }

    suspend fun getSubEventById(studioId: Long, eventId: Long): ApiResponse<SubEventResponseDTO> {
        return eventApi.getSubEventById(studioId, eventId)
    }

    suspend fun editSubEventById(eventId: Long, request: SubEventRequestDTO): ApiResponse<SubEventResponseDTO> {
        return eventApi.EditSubEventById(eventId, request)

    }

    suspend fun deleteSubEventById(studioId: Long, eventId: Long): ApiResponse<Unit> {
        return eventApi.deleteSubEventById(studioId, eventId)
    }

}