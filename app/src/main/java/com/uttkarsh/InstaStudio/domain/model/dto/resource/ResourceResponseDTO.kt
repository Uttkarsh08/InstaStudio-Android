package com.uttkarsh.InstaStudio.domain.model.dto.resource

data class ResourceResponseDTO(
    val resourceId: Long,
    val resourceLastUsedEvent: ResourceLastUsedEvent? = null,
    val resourceName: String,
    val resourcePrice: Long,
    val resourceRegisteredAt: String
)