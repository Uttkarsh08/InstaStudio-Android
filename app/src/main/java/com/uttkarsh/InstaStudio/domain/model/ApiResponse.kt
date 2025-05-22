package com.uttkarsh.InstaStudio.domain.model

import java.time.LocalDateTime

data class ApiResponse<T>(
  val data: T?,
  val error: ApiError,
  val timeStamp: String
)