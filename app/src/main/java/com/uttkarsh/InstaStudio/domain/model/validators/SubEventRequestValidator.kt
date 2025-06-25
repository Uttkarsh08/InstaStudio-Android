package com.uttkarsh.InstaStudio.domain.model.validators

import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventRequestDTO
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import java.util.Date

fun SubEventRequestDTO.validate(
    timeProvider: TimeProvider
): String? {

    if (eventType.isBlank()) return "Event type can't be blank"
    if (eventType.length > 20) return "Event type must be at most 20 characters"

    val parsedStart = timeProvider.parseDateTime(eventStartDate)
        ?: return "Event start date is invalid"
    if (parsedStart.before(Date())) return "Event start date must be in the future"

    val parsedEnd = timeProvider.parseDateTime(eventEndDate)
        ?: return "Event end date is invalid"
    if (parsedEnd.before(parsedStart)) return "Event end date must be after start date"

    if (eventLocation.isBlank()) return "Event location can't be blank"
    if (eventLocation.length > 20) return "Event location must be at most 20 characters"

    if (eventCity.isBlank()) return "Event city can't be blank"
    if (eventCity.length > 20) return "Event city must be at most 20 characters"

    if (eventState.isBlank()) return "Event state can't be blank"
    if (eventState.length > 20) return "Event state must be at most 20 characters"

    if (studioId <= 0) return "Studio Id must be positive"

    if (memberIds.size > 20) return "You can assign up to 20 members only"

    if (resourceIds.size > 20) return "You can assign up to 20 resources only"

    return null
}
