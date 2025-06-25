package com.uttkarsh.InstaStudio.domain.usecase.member

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager

class GetMemberByIdUseCase(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(memberId: Long): MemberProfileResponseDTO {
        val studioId = sessionManager.getStudioId()
        val response = memberRepository.getMemberById(studioId, memberId)

        return response.data ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")
    }
}
