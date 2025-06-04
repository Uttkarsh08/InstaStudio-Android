package com.uttkarsh.InstaStudio.utils.states

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import kotlinx.coroutines.flow.Flow

sealed class MemberState {
        object Idle : MemberState()
        object Loading : MemberState()
        data class PageSuccess(val response: Flow<PagingData<MemberProfileResponseDTO>>): MemberState()
        data class ListSuccess(val response: List<MemberProfileResponseDTO>): MemberState()
        data class Success(val response: MemberProfileResponseDTO) : MemberState()
        data class Error(val message: String) : MemberState()
}