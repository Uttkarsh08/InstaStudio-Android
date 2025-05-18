package com.uttkarsh.InstaStudio.domain.model

data class ApiResponse<T>(
  val data: T?,
  val error: String?,
  val timeStamp: String
)