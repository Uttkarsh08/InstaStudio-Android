package com.uttkarsh.InstaStudio.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.uttkarsh.InstaStudio.data.auth.ResourceApiService
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import javax.inject.Inject

class ResourcePagingSource @Inject constructor(
    private val resourceApi: ResourceApiService,
    private val studioId: Long
) : PagingSource<Int, ResourceResponseDTO>() {

    override fun getRefreshKey(state: PagingState<Int, ResourceResponseDTO>): Int? {
        return state.anchorPosition?.let {position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResourceResponseDTO> {
        return try {
            val page = params.key ?: 0

            val response = resourceApi.getAllResources(studioId, page)
            val resources = response.data?.content ?: emptyList()

            LoadResult.Page(
                data =resources,
                prevKey =if(page == 0) null else page-1,
                nextKey = if(resources.isEmpty()) null else page+1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}