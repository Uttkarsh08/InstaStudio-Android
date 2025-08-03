package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.domain.repository.AuthRepository
import com.uttkarsh.InstaStudio.domain.repository.EventRepository
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import com.uttkarsh.InstaStudio.domain.repository.ProfileRepository
import com.uttkarsh.InstaStudio.domain.repository.ResourceRepository
import com.uttkarsh.InstaStudio.domain.usecase.auth.*
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.DashboardUseCases
import com.uttkarsh.InstaStudio.domain.usecase.dashboard.GetUserProfileUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.Event.EventUseCases
import com.uttkarsh.InstaStudio.domain.usecase.event.Event.GetCompletedEventsUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.Event.GetEventByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.Event.GetNextUpcomingEventUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.Event.GetUpcomingEventsUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.addEvent.AddEventUseCases
import com.uttkarsh.InstaStudio.domain.usecase.event.addEvent.CreateNewEventUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.AddSubEventUseCases
import com.uttkarsh.InstaStudio.domain.usecase.event.addSubEvent.CreateNewSubEventUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.subEvent.DeleteSubEventByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.subEvent.EditSubEventByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.subEvent.GetSubEventByIdUseCase
import com.uttkarsh.InstaStudio.domain.usecase.event.subEvent.SubEventUseCases
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
import com.uttkarsh.InstaStudio.domain.usecase.resource.GetAvailableResourcesUseCase
import com.uttkarsh.InstaStudio.domain.usecase.resource.ResourceUseCases
import com.uttkarsh.InstaStudio.domain.usecase.resource.UpdateResourceUseCase
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAuthUseCases(
        validateFirebaseTokenUseCase: ValidateFirebaseTokenUseCase,
        logoutUseCase: LogoutUseCase,
        checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
        setOnboardingShownUseCase: SetOnboardingShownUseCase,
        observeOnboardingShownUseCase: ObserveOnboardingShownUseCase,
        observeRegistrationStatusUseCase: ObserveRegistrationStatusUseCase
    ): AuthUseCases {
        return AuthUseCases(
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
    fun provideAddEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager,
        timeProvider: TimeProvider
    ): AddEventUseCases {
        return AddEventUseCases(
            createNewEvent = CreateNewEventUseCase(eventRepository, sessionManager, timeProvider)
        )
    }


    @Provides
    fun provideAddSubEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager,
        timeProvider: TimeProvider
    ): AddSubEventUseCases {
        return AddSubEventUseCases(
            createNewSubEvent = CreateNewSubEventUseCase(eventRepository, sessionManager, timeProvider)
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
            getAllResources = GetAllResourcesUseCase(resourceRepository, sessionManager),
            getAvailableResources = GetAvailableResourcesUseCase(resourceRepository, sessionManager)
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

    @Provides
    fun provideSubEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager,
        timeProvider: TimeProvider
    ): SubEventUseCases {
        return SubEventUseCases(
            getSubEventById = GetSubEventByIdUseCase(eventRepository, sessionManager),
            editSubEventById = EditSubEventByIdUseCase(eventRepository, sessionManager, timeProvider),
            deleteSubEventById = DeleteSubEventByIdUseCase(eventRepository, sessionManager)
        )
    }

    @Provides
    fun provideEventUseCases(
        eventRepository: EventRepository,
        sessionManager: SessionManager
    ): EventUseCases {
        return EventUseCases(
            getUpcomingEvents = GetUpcomingEventsUseCase(eventRepository, sessionManager),
            getCompletedEvents = GetCompletedEventsUseCase(eventRepository, sessionManager),
            getNextUpcomingEvent = GetNextUpcomingEventUseCase(eventRepository, sessionManager),
            getEventById = GetEventByIdUseCase(eventRepository, sessionManager)
        )
    }

}
