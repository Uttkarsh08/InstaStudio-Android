package com.uttkarsh.InstaStudio.domain.model

data class ProfileRequestDTO(
    val firebaseId: String,
    val userName: String,
    val userPhoneNo: String,
    val userEmail: String,
    val userType: UserType?

)