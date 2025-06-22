package com.uttkarsh.InstaStudio.domain.model.validators

import android.os.Build
import androidx.annotation.RequiresApi
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventRequestDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun EventRequestDTO.validate(): String? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    if (clientName.isBlank()) return "Client name can't be blank"
    if (clientName.length > 20) return "Client name must be at most 20 characters"

    if (clientPhoneNo.isBlank()) return "Client number can't be blank"
    if (!clientPhoneNo.matches(Regex("\\d{10}"))) return "Client phone number must be exactly 10 digits"

    if (eventType.isBlank()) return "Event type can't be blank"
    if (eventType.length > 20) return "Event type must be at most 20 characters"

    val parsedStart = try {
        LocalDateTime.parse(eventStartDate, formatter)
    } catch (e: Exception) {
        return "Event start date is invalid"
    }
    if (parsedStart.isBefore(LocalDateTime.now())) return "Event start date must be in the future"

    val parsedEnd = try {
        LocalDateTime.parse(eventEndDate, formatter)
    } catch (e: Exception) {
        return "Event end date is invalid"
    }
    if (parsedEnd.isBefore(LocalDateTime.now())) return "Event end date must be after start date"

    if (eventLocation.isBlank()) return "Event location can't be blank"
    if (eventLocation.length > 20) return "Event location must be at most 20 characters"

    if (eventCity.isBlank()) return "Event city can't be blank"
    if (eventCity.length > 20) return "Event city must be at most 20 characters"

    if (eventState.isBlank()) return "Event state can't be blank"
    if (eventState.length > 20) return "Event state must be at most 20 characters"

    if (studioId <= 0) return "Studio Id must be positive"

    return null
}
