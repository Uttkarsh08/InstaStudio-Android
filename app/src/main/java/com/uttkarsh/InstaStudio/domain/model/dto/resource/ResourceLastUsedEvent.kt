package com.uttkarsh.InstaStudio.domain.model.dto.resource

import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO

data class ResourceLastUsedEvent(
    val clientName: String,
    val clientPhoneNo: String,
    val evenIsSaved: Boolean,
    val eventCity: String,
    val eventEndDate: String,
    val eventId: Long,
    val eventLocation: String,
    val eventStartDate: String,
    val eventState: String,
    val eventType: String,
    val parentEvent: String,
    val subEvents: List<String>,
    val resources: List<ResourceResponseDTO>,
    val members: List<MemberProfileResponseDTO>
)