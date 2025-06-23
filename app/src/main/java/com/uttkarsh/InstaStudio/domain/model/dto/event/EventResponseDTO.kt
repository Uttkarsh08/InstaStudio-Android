package com.uttkarsh.InstaStudio.domain.model.dto.event

import com.google.gson.annotations.SerializedName
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberInEventDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO

data class EventResponseDTO(
    val eventId: Long,
    val clientName: String?,
    val clientPhoneNo: String?,
    val eventType: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val eventIsSaved: Boolean,
    val subEvents: Set<EventResponseDTO>,
    val members: Set<MemberInEventDTO>,
    val resources: Set<ResourceResponseDTO>
)