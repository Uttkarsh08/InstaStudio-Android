package com.uttkarsh.InstaStudio.domain.usecase.dashboard

import javax.inject.Inject

data class DashboardUseCases @Inject constructor(
    val getUserProfile: GetUserProfileUseCase
)
