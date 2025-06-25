package com.uttkarsh.InstaStudio.di

import TimeProviderImpl
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimeModule {

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider = TimeProviderImpl()
}