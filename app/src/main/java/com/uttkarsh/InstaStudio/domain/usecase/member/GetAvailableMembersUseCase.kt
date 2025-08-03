package com.uttkarsh.InstaStudio.domain.usecase.member

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import java.time.LocalDateTime
import javax.inject.Inject

class GetAvailableMembersUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(startDate: LocalDateTime, endDate: LocalDateTime): List<MemberProfileResponseDTO> {
        val studioId = sessionManager.getStudioId()
        val response = memberRepository.getAvailableMembers(studioId, startDate, endDate)

        return response.data ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")
    }
}
