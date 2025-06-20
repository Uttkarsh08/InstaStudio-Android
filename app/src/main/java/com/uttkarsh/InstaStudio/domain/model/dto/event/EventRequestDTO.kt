package com.uttkarsh.InstaStudio.domain.model.dto.event

data class EventRequestDTO(
    val clientName: String,
    val clientPhoneNo: String,
    val eventType: String,
    val subEventsIds: Set<Long>,
    val memberIds: Set<Long>,
    val resourceIds: Set<Long>,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val evenIsSaved: Boolean,
    val studioId: Long
)