package com.uttkarsh.InstaStudio.domain.model

data class StudioResponseDTO(
    val studioId: Long,
    val studioName: String,
    val studioAddress: String,
    val studioCity: String,
    val studioState: String,
    val studioPinCode: String
)
