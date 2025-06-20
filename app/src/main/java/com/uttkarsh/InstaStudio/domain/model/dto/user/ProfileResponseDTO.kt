package com.uttkarsh.InstaStudio.domain.model.dto.user

import com.uttkarsh.InstaStudio.domain.model.UserType

data class ProfileResponseDTO(
  val userId: Long,
  val firebaseId: String,
  val userName: String,
  val userEmail: String,
  val userPhoneNo: String,
  val userType: UserType,
  val registrationDate: String
)