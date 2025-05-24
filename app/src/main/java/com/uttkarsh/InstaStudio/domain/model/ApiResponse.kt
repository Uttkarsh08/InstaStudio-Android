package com.uttkarsh.InstaStudio.domain.model

data class ApiResponse<T>(
  val data: T?,
  val error: ApiError,
  val timeStamp: String
)