package com.uttkarsh.InstaStudio.domain.model

data class AdminProfileSetupRequestDTO(

    val user: ProfileRequestDTO,

    val studio: StudioRequestDTO
)
