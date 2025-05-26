package com.uttkarsh.InstaStudio.domain.model.dto.member

data class MemberProfileRequestDTO(
    val memberEmail: String,
    val salary: Long,
    val specialization: String,
    val studioId: Long
)