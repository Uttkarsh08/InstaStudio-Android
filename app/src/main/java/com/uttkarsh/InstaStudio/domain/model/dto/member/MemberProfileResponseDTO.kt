package com.uttkarsh.InstaStudio.domain.model.dto.member

data class MemberProfileResponseDTO(
    val memberAverageRating: Long,
    val memberEmail: String,
    val memberId: Long,
    val memberName: String,
    val memberPhoneNo: String,
    val salary: Long,
    val specialization: String
)