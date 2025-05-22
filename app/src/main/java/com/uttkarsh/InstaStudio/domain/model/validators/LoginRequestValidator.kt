package com.uttkarsh.InstaStudio.domain.model.validators

import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.UserType


fun LoginRequestDTO.validate(): String? {
    if (firebaseToken.isBlank()) return "Firebase token cannot be empty"
    if (loginType !in UserType.entries.toTypedArray()) return "Invalid user type"
    return null
}
