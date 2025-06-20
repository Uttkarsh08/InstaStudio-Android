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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventApi: EventApiService
) {

    suspend fun createNewEvent(request: EventRequestDTO): ApiResponse<EventResponseDTO> {
        return eventApi.registerEvent(request)
    }

    suspend fun createNewSubEvent(request: EventRequestDTO): ApiResponse<EventResponseDTO> {
        return eventApi.registerSubEvent(request)
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

}