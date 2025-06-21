package com.uttkarsh.InstaStudio.domain.model.dto.event

data class SubEventRequestDTO(
    val eventType: String,
    val memberIds: Set<Long>,
    val resourceIds: Set<Long>,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventLocation: String,
    val eventCity: String,
    val eventState: String,
    val studioId: Long
)