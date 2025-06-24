package com.uttkarsh.InstaStudio.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(): Boolean = withContext(Dispatchers.IO) {
        firebaseAuth.currentUser != null
    }
}
