package com.uttkarsh.InstaStudio.utils.SharedPref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "onboarding_prefs")

@Singleton
class OnboardingStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore = context.dataStore
    private val ONBOARDING_SHOWN_KEY = booleanPreferencesKey("onboarding_shown")

    val isOnboardingShownFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[ONBOARDING_SHOWN_KEY] ?: false }

    suspend fun setOnboardingShown() {
        context.dataStore.edit { it[ONBOARDING_SHOWN_KEY] = true }
    }
}
