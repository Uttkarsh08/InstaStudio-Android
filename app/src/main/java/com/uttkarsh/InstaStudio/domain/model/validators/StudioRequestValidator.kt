package com.uttkarsh.InstaStudio.domain.model.validators

import com.uttkarsh.InstaStudio.domain.model.dto.studio.StudioRequestDTO

fun StudioRequestDTO.validate(): String? {
    if (studioName.isBlank()) return "Studio name can't be blank"
    if (studioName.length > 100) return "Studio name must be at most 100 characters"

    if (studioAddress.isBlank()) return "Studio address can't be blank"
    if (studioAddress.length > 200) return "Studio address must be at most 200 characters"

    if (studioCity.isBlank()) return "Studio city can't be blank"
    if (studioCity.length > 50) return "Studio city must be at most 50 characters"

    if (studioState.isBlank()) return "Studio state can't be blank"
    if (studioState.length > 50) return "Studio state must be at most 50 characters"

    if (studioPinCode.isBlank()) return "Studio pinCode can't be blank"
    if (!studioPinCode.matches(Regex("\\d{6}"))) return "Studio pinCode must be exactly 6 digits"

    return null
}
