package com.uttkarsh.InstaStudio.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.uttkarsh.InstaStudio.data.auth.EventApiService
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import javax.inject.Inject

class UpcomingEventPagingSource @Inject constructor(
    private val eventApiService: EventApiService,
    private val studioId: Long
): PagingSource<Int, EventListResponseDTO>(){
    override fun getRefreshKey(state: PagingState<Int, EventListResponseDTO>): Int? {
        return state.anchorPosition?.let {position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventListResponseDTO> {
        return try {
            val page = params.key ?:0

            val response = eventApiService.getUpcomingEvents(studioId, page)
            val upcomingEvents = response.data?.content?: emptyList()

            LoadResult.Page(
                data = upcomingEvents,
                prevKey = if(page == 0) null else page-1,
                nextKey = if(upcomingEvents.isEmpty()) null else page+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}