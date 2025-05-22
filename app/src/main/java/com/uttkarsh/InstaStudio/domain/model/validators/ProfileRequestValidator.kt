package com.uttkarsh.InstaStudio.domain.model.validators

import android.util.Patterns
import com.uttkarsh.InstaStudio.domain.model.ProfileRequestDTO

fun ProfileRequestDTO.validate(): String? {
    if (firebaseId.isBlank()) return "Firebase ID can't be blank"

    if (userName.isBlank()) return "User name can't be blank"
    if (userName.length > 50) return "User name must be at most 50 characters"

    if (userEmail.isBlank()) return "User email can't be blank"
    if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) return "Invalid email format"

    if (!userPhoneNo.matches(Regex("\\d{10}"))) return "Phone number must be exactly 10 digits"

    if (userType == null) return "User type can't be null"

    return null
}
