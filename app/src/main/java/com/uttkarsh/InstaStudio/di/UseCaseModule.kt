package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.domain.usecase.auth.*
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.DashboardUseCases
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.GetUserProfileUseCase
import com.uttkarsh.InstaStudio.utils.session.SessionManager
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

    @Provides
    fun provideDashboardUseCases(
        profileRepository: ProfileRepository,
        sessionManager: SessionManager
    ): DashboardUseCases {
        return DashboardUseCases(
            getUserProfile = GetUserProfileUseCase(profileRepository, sessionManager)
        )
    }
}
