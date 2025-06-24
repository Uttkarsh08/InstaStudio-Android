package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import javax.inject.Inject

class ObserveRegistrationStatusUseCase @Inject constructor(
    private val sessionStore: SessionStore
) {
    operator fun invoke() = sessionStore.isRegisteredFlow
}
