package com.uttkarsh.InstaStudio.domain.usecase.auth

import android.content.Context
import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context): String? {
        return withContext(Dispatchers.IO) {
            authRepository.signInWithGoogle(context)
        }
    }
}
