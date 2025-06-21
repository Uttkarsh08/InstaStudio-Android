package com.uttkarsh.InstaStudio.domain.model.dto.event

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberInEventDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO

data class SubEventResponseDTO(
    val eventId: Long,
    val parentEventId: Long,
    val eventType: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val members: List<MemberInEventDTO>,
    val resources: List<ResourceResponseDTO>
)