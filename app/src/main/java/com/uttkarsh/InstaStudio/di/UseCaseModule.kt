package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.domain.usecase.auth.*
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.DashboardUseCases
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.GetUserProfileUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.addEvent.AddEventUseCases
import com.uttkarsh.InstaStudio.domain.usecase.event.addEvent.CreateNewEventUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.AddSubEventUseCases
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.CreateNewSubEventUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.GetSubEventByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.member.CreateMemberUseCase
import com.uttkarsh.InstaStudio.domain.usecase.member.GetAllMembersUseCase
import com.uttkarsh.InstaStudio.domain.usecase.member.GetAvailableMembersUseCase
import com.uttkarsh.InstaStudio.domain.usecase.member.GetMemberByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.member.MemberUseCases
import com.uttkarsh.InstaStudio.domain.usecase.member.UpdateMemberByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.profile.FetchLatestEmailUseCase
import com.uttkarsh.InstaStudio.domain.usecase.profile.GetStudioImageUseCase
import com.uttkarsh.InstaStudio.domain.usecase.profile.ProfileUseCases
import com.uttkarsh.InstaStudio.domain.usecase.profile.SaveAdminProfileUseCase
import com.uttkarsh.InstaStudio.domain.usecase.resource.CreateResourceUseCase
import com.uttkarsh.InstaStudio.domain.usecase.resource.GetAllResourcesUseCase
import com.uttkarsh.InstaStudio.domain.usecase.resource.ResourceUseCases
import com.uttkarsh.InstaStudio.domain.usecase.resource.UpdateResourceUseCase
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
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

    @Provides
    fun provideProfileUseCases(
        profileRepository: ProfileRepository,
        authRepository: AuthRepository,
        sessionStore: SessionStore,
        sessionManager: SessionManager
    ): ProfileUseCases{

        return ProfileUseCases(
            fetchLatestEmail = FetchLatestEmailUseCase(sessionStore),
            saveAdminProfile = SaveAdminProfileUseCase(profileRepository, authRepository, sessionStore, sessionManager),
            getStudioImage = GetStudioImageUseCase(profileRepository, sessionManager)
        )
    }

    @Provides
    fun provideEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager
    ): AddEventUseCases {
        return AddEventUseCases(
            createNewEvent = CreateNewEventUseCase(eventRepository, sessionManager)
        )
    }


    @Provides
    fun provideAddSubEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager
    ): AddSubEventUseCases {
        return AddSubEventUseCases(
            createNewSubEvent = CreateNewSubEventUseCase(eventRepository, sessionManager),
            getSubEventById = GetSubEventByIdUseCase(eventRepository, sessionManager)
        )
    }
    
    @Provides
    fun provideResourceUseCases(
        resourceRepository: ResourceRepository,
        sessionManager: SessionManager
    ): ResourceUseCases {
        return ResourceUseCases(
            createResource = CreateResourceUseCase(resourceRepository, sessionManager),
            updateResource = UpdateResourceUseCase(resourceRepository, sessionManager),
            getAllResources = GetAllResourcesUseCase(resourceRepository, sessionManager)
        )
    }

    @Provides
    fun provideMemberUseCases(
        memberRepository: MemberRepository,
        sessionManager: SessionManager
    ): MemberUseCases {
        return MemberUseCases(
            getAllMembers = GetAllMembersUseCase(memberRepository, sessionManager),
            createMember = CreateMemberUseCase(memberRepository, sessionManager),
            updateMemberById = UpdateMemberByIdUseCase(memberRepository, sessionManager),
            getMemberById = GetMemberByIdUseCase(memberRepository, sessionManager),
            getAvailableMembers = GetAvailableMembersUseCase(memberRepository, sessionManager)
        )
    }
}
