package com.uttkarsh.InstaStudio.domain.usecase.resource

data class ResourceUseCases(
    val createResource: CreateResourceUseCase,
    val updateResource: UpdateResourceUseCase,
    val getAllResources: GetAllResourcesUseCase,
    val getAvailableResources: GetAvailableResourcesUseCase
)
