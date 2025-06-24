package com.uttkarsh.InstaStudio.domain.usecase.profile

import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchLatestEmailUseCase @Inject constructor(
    private val sessionStore: SessionStore
) {
    operator fun invoke() = sessionStore.emailFlow
}