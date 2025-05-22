package com.uttkarsh.InstaStudio.domain.model

data class ApiError (
    val status: String,
    val message: String,
    val subErrors: List<String>

)