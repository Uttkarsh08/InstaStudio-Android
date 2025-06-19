package com.uttkarsh.InstaStudio.domain.model.validators

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileRequestDTO

fun MemberProfileRequestDTO.validate(): String?{
    if(memberEmail.isBlank()) return "Email cannot be empty"
    if(salary == 0L) return "Salary cannot be empty"
    if(salary < 0L) return "Salary must be a valid positive number"
    if(specialization.isBlank()) return "Specialization cannot be empty"
    return null
}