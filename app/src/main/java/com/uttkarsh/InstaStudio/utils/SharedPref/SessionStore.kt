package com.uttkarsh.InstaStudio.utils.SharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.uttkarsh.InstaStudio.domain.model.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SessionStore @Inject constructor(@ApplicationContext context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveTokens(access: String, refresh: String) {
        prefs.edit {
            putString("access", access)
                .putString("refresh", refresh)
        }
    }

    fun saveUserInfo(name: String?, email: String?, firebaseId: String?, userType: UserType, isRegistered: Boolean) {
        prefs.edit {
            putString("name", name)
                .putString("email", email)
                .putString("firebaseId", firebaseId)
                .putString("userType", userType.toString())
                .putBoolean("isRegistered", isRegistered)
        }
    }

    fun saveStudioId(studioId: Long) {
        prefs.edit {
            putLong("studioId", studioId)
        }
    }

    fun saveUserId(userId: Long) {
        prefs.edit {
            putLong("userId", userId)
        }
    }

    fun getAccessToken(): String? = prefs.getString("access", null)
    fun getRefreshToken(): String? = prefs.getString("refresh", null)

    fun getName(): String? = prefs.getString("name", null)
    fun getEmail(): String? = prefs.getString("email", null)
    fun getFirebaseId(): String? = prefs.getString("firebaseId", null)
    fun getUserType(): UserType? = prefs.getString("userType", null)?.let { UserType.valueOf(it) }

    fun getIsRegistered(): Boolean {
        return prefs.getBoolean("isRegistered", false)
    }

    fun updateIsRegistered() {
         prefs.edit(commit = true) { putBoolean("isRegistered", true) }
    }
    fun getStudioId(): Long = prefs.getLong("studioId", -1)
    fun getUserId(): Long = prefs.getLong("userId", -1)

    fun clear() {
        prefs.edit { clear() }
    }
}
