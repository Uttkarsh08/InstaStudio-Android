package com.uttkarsh.InstaStudio.domain.usecase.member

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.utils.session.SessionManager

class UpdateMemberByIdUseCase(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(
        memberId: Long,
        email: String,
        salary: Long,
        specialization: String
    ): MemberProfileResponseDTO {
        val studioId = sessionManager.getStudioId()

        val request = MemberProfileRequestDTO(
            memberEmail = email,
            salary = salary,
            specialization = specialization,
            studioId = studioId
        )

        request.validate()?.let { error -> throw IllegalArgumentException(error) }

        val response = memberRepository.updateMemberById(studioId, memberId, request)

        return response.data ?: throw Exception("${response.error.message}: ${response.error.subErrors.joinToString()}")
    }
}
