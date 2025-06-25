package com.uttkarsh.InstaStudio.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.uttkarsh.InstaStudio.data.auth.MemberApiService
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import javax.inject.Inject

class MemberPagingSource @Inject constructor(
    private val memberApiService: MemberApiService,
    private val studioId: Long
) : PagingSource<Int, MemberProfileResponseDTO>() {

    override fun getRefreshKey(state: PagingState<Int, MemberProfileResponseDTO>): Int? {
        return state.anchorPosition?.let {position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemberProfileResponseDTO> {
        return try {
            val page = params.key ?: 0

            val response = memberApiService.getAllMembers(studioId, page)
            val members = response.data?.content?: emptyList()

            LoadResult.Page(
                data = members,
                prevKey =if(page == 0) null else page-1,
                nextKey = if(members.isEmpty()) null else page+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}