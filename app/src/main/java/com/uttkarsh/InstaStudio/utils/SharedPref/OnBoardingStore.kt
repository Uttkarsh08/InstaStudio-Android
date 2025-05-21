package com.uttkarsh.InstaStudio.utils.SharedPref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class OnboardingStore @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)

    fun setOnboardingShown() {
        prefs.edit { putBoolean("onboarding_shown", true) }
    }

    fun isOnboardingShown(): Boolean {
        return prefs.getBoolean("onboarding_shown", false)
    }

    fun clear() {
        prefs.edit { clear() }
    }
}
