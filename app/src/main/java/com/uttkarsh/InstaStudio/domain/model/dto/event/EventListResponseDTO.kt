package com.uttkarsh.InstaStudio.domain.model.dto.event

data class EventListResponseDTO(
    val eventId: Long,
    val clientName: String,
    val clientPhoneNo: String,
    val eventType: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val eventIsSaved: Boolean,
    val subEventsIds: Set<Long>,
    val memberIds: Set<Long>,
    val resourceIds: Set<Long>
)