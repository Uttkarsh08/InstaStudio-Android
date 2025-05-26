package com.uttkarsh.InstaStudio.domain.model.dto.resource

data class ResourceRequestDTO(
    val resourceName: String,
    val resourcePrice: Long,
    val studioId: Long
)