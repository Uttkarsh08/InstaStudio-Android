package com.uttkarsh.InstaStudio.domain.model.validators

import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceRequestDTO

fun ResourceRequestDTO.validate(): String? {
    if (resourceName.isBlank()) return "Resource name cannot be empty"
    if (resourcePrice == 0L) return "Resource price cannot be empty"
    if (resourcePrice < 0L) return "Resource price must be a valid positive number"
    return null
}