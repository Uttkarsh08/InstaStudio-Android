package com.uttkarsh.InstaStudio.di

import android.content.Context
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionStoreModule {

    @Provides
    @Singleton
    fun provideSessionStore(@ApplicationContext context: Context): SessionStore {
        return SessionStore(context)
    }
}