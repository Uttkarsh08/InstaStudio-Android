package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        authRepository.signOut()
        sessionStore.clear()
    }
}
