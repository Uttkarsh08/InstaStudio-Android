package com.uttkarsh.InstaStudio.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.data.auth.MemberApiService
import com.uttkarsh.InstaStudio.data.pagination.MemberPagingSource
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberRepository @Inject constructor(
    private val memberApiService: MemberApiService
){
    suspend fun createNewMember(request: MemberProfileRequestDTO): ApiResponse<MemberProfileResponseDTO> {
        return memberApiService.createNewMember(request)
    }

    fun getAllMembers(studioId: Long): Flow<PagingData<MemberProfileResponseDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MemberPagingSource(memberApiService, studioId)
            }
        ).flow
    }

    suspend fun getAvailableMembers(studioId: Long,
                                    startDate: LocalDateTime,
                                    endDate: LocalDateTime
    ): ApiResponse<List<MemberProfileResponseDTO>>{

        return memberApiService.getAvailableMembers(studioId, startDate, endDate)
    }

    suspend fun getMemberById(
        studioId: Long,
        memberId: Long
    ): ApiResponse<MemberProfileResponseDTO> {
        return memberApiService.getMemberBuId(studioId, memberId)
    }

    suspend fun updateMemberById(
        studioId: Long,
        memberId: Long,
        request: MemberProfileRequestDTO
    ): ApiResponse<MemberProfileResponseDTO> {
        return memberApiService.updateMemberById(studioId, memberId, request)

    }
}