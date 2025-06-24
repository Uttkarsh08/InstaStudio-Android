package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import javax.inject.Inject

class ObserveOnboardingShownUseCase @Inject constructor(
    private val onboardingStore: OnboardingStore
) {
    operator fun invoke() = onboardingStore.isOnboardingShownFlow
}
