package com.uttkarsh.InstaStudio.domain.usecase.profile

data class ProfileUseCases(
    val saveAdminProfile: SaveAdminProfileUseCase,
    val getStudioImage: GetStudioImageUseCase,
    val fetchLatestEmail: FetchLatestEmailUseCase
)
