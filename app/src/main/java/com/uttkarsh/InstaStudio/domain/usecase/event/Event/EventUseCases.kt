package com.uttkarsh.InstaStudio.domain.usecase.event.Event

data class EventUseCases(
    val getUpcomingEvents: GetUpcomingEventsUseCase,
    val getCompletedEvents: GetCompletedEventsUseCase,
    val getNextUpcomingEvent: GetNextUpcomingEventUseCase,
    val getEventById: GetEventByIdUseCase
)
