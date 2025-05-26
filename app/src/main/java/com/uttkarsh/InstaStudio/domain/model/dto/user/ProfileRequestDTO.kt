package com.uttkarsh.InstaStudio.domain.model.dto.user

import com.uttkarsh.InstaStudio.domain.model.UserType

data class ProfileRequestDTO(
    val firebaseId: String,
    val userName: String,
    val userPhoneNo: String,
    val userEmail: String,
    val userType: UserType?

)