package com.uttkarsh.InstaStudio.domain.usecase.auth

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val signInWithGoogle: SignInWithGoogleUseCase,
    val validateFirebaseToken: ValidateFirebaseTokenUseCase,
    val logout: LogoutUseCase,
    val checkUserLoggedIn: CheckUserLoggedInUseCase,
    val setOnboardingShown: SetOnboardingShownUseCase,
    val observeOnboardingShown: ObserveOnboardingShownUseCase,
    val observeRegistrationStatus: ObserveRegistrationStatusUseCase
)
