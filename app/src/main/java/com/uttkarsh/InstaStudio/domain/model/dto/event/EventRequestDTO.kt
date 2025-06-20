package com.uttkarsh.InstaStudio.domain.model.dto.event

data class EventRequestDTO(
    val clientName: String,
    val clientPhoneNo: String,
    val eventType: String,
    val subEventsIds: List<Int>,
    val memberIds: List<Int>,
    val resourceIds: List<Int>,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val evenIsSaved: Boolean,
    val studioId: Long
)