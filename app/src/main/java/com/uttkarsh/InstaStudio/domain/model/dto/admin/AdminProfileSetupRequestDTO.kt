package com.uttkarsh.InstaStudio.domain.model.dto.admin

import com.uttkarsh.InstaStudio.domain.model.dto.studio.StudioRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.user.ProfileRequestDTO

data class AdminProfileSetupRequestDTO(

    val user: ProfileRequestDTO,

    val studio: StudioRequestDTO
)