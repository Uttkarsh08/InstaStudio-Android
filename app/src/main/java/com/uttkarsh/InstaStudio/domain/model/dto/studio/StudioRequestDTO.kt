package com.uttkarsh.InstaStudio.domain.model.dto.studio

data class StudioRequestDTO(

    val studioName: String,
    val studioAddress: String,
    val studioCity: String,
    val studioState: String,
    val studioPinCode: String,
    val imageDataBase64: String?
)
