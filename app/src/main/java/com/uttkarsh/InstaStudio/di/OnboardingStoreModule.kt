package com.uttkarsh.InstaStudio.di

import android.content.Context
import com.uttkarsh.InstaStudio.utils.SharedPref.OnboardingStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingStoreModule {

    @Provides
    @Singleton
    fun provideOnboardingStore(@ApplicationContext context: Context): OnboardingStore {
        return OnboardingStore(context)
    }
}
