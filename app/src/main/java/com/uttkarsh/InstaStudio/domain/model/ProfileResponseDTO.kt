package com.uttkarsh.InstaStudio.domain.model

data class ProfileResponseDTO(
  val userId: Long,
  val firebaseId: String,
  val userName: String,
  val userEmail: String,
  val userPhoneNo: String,
  val userType: UserType
)