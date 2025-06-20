package com.uttkarsh.InstaStudio.domain.model.dto.member

import com.uttkarsh.InstaStudio.domain.model.dto.user.ProfileResponseDTO

data class MemberInEventDTO(
    val memberId: Long,
    val memberSalary: Long,
    val specialization: String,
    val user: ProfileResponseDTO
)
