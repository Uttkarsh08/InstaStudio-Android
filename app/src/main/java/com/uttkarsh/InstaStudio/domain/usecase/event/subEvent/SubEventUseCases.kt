package com.uttkarsh.InstaStudio.domain.usecase.event.subEvent

data class SubEventUseCases(
    val getSubEventById: GetSubEventByIdUseCase,
    val editSubEventById: EditSubEventByIdUseCase,
    val deleteSubEventById: DeleteSubEventByIdUseCase
)
