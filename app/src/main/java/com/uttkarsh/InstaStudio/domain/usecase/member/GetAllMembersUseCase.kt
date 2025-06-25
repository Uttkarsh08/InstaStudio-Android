package com.uttkarsh.InstaStudio.domain.usecase.member

import androidx.paging.PagingData
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import kotlinx.coroutines.flow.Flow

class GetAllMembersUseCase(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Flow<PagingData<MemberProfileResponseDTO>> {
        val studioId = sessionManager.getStudioId()
        return memberRepository.getAllMembers(studioId)
    }
}
