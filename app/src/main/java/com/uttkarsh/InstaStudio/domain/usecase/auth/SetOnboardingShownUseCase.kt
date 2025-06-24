package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetOnboardingShownUseCase @Inject constructor(
    private val onboardingStore: OnboardingStore
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        onboardingStore.setOnboardingShown()
    }
}
