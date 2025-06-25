package com.uttkarsh.InstaStudio.di

import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TimeProviderEntryPoint {
    fun getTimeProvider(): TimeProvider
}
