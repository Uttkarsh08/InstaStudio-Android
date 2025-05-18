package com.uttkarsh.InstaStudio.utils.SharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.uttkarsh.InstaStudio.domain.model.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(@ApplicationContext context: Context) {

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
        prefs.edit()
            .putString("access", access)
            .putString("refresh", refresh)
            .apply()
    }

    fun saveUserInfo(name: String?, email: String?, firebaseId: String?, userType: UserType) {
        prefs.edit()
            .putString("name", name)
            .putString("email", email)
            .putString("firebaseId", firebaseId)
            .putString("userType", userType.toString())
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString("access", null)
    fun getRefreshToken(): String? = prefs.getString("refresh", null)

    fun getName(): String? = prefs.getString("name", null)
    fun getEmail(): String? = prefs.getString("email", null)
    fun getFirebaseId(): String? = prefs.getString("firebaseId", null)
    fun getUserType(): UserType? = prefs.getString("userType", null)?.let { UserType.valueOf(it) }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
