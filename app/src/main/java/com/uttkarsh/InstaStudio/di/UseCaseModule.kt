package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.domain.usecase.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAuthUseCases(
        signInWithGoogleUseCase: SignInWithGoogleUseCase,
        validateFirebaseTokenUseCase: ValidateFirebaseTokenUseCase,
        logoutUseCase: LogoutUseCase,
        checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
        setOnboardingShownUseCase: SetOnboardingShownUseCase,
        observeOnboardingShownUseCase: ObserveOnboardingShownUseCase,
        observeRegistrationStatusUseCase: ObserveRegistrationStatusUseCase
    ): AuthUseCases {
        return AuthUseCases(
            signInWithGoogleUseCase,
            validateFirebaseTokenUseCase,
            logoutUseCase,
            checkUserLoggedInUseCase,
            setOnboardingShownUseCase,
            observeOnboardingShownUseCase,
            observeRegistrationStatusUseCase
        )
    }
}
