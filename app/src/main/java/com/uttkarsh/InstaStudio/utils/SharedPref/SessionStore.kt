package com.uttkarsh.InstaStudio.utils.SharedPref

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import android.util.Base64
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.uttkarsh.InstaStudio.domain.model.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import java.security.KeyStore

private val Context.dataStore by preferencesDataStore("auth_prefs")

@Singleton
class SessionStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val ACCESS = stringPreferencesKey("access")
        val REFRESH = stringPreferencesKey("refresh")
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val FIREBASE_ID = stringPreferencesKey("firebaseId")
        val USER_TYPE = stringPreferencesKey("userType")
        val IS_REGISTERED = booleanPreferencesKey("isRegistered")
        val STUDIO_ID = longPreferencesKey("studioId")
        val USER_ID = longPreferencesKey("userId")
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        return if (keyStore.containsAlias("MyDataStoreKeyAlias")) {
            (keyStore.getEntry("MyDataStoreKeyAlias", null) as KeyStore.SecretKeyEntry).secretKey
        } else {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                "MyDataStoreKeyAlias",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    private fun createCipher(): Cipher = Cipher.getInstance("AES/GCM/NoPadding")

    private fun encrypt(plainText: String): String {
        val cipher = createCipher()
        val secretKey = getOrCreateSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.NO_WRAP)
    }

    private fun decrypt(encryptedText: String): String? {
        return try {
            val decoded = Base64.decode(encryptedText, Base64.NO_WRAP)
            val iv = decoded.copyOfRange(0, 12)
            val encrypted = decoded.copyOfRange(12, decoded.size)
            val cipher = createCipher()
            val spec = GCMParameterSpec(128, iv)
            val secretKey = getOrCreateSecretKey()
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            String(cipher.doFinal(encrypted), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveTokens(access: String, refresh: String) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.ACCESS] = encrypt(access)
            prefs[PreferencesKeys.REFRESH] = encrypt(refresh)
        }
    }

    suspend fun saveUserInfo(
        name: String?,
        email: String?,
        firebaseId: String?,
        userType: UserType,
        isRegistered: Boolean
    ) {
        dataStore.edit { prefs ->
            name?.let { prefs[PreferencesKeys.NAME] = encrypt(it) }
            email?.let { prefs[PreferencesKeys.EMAIL] = encrypt(it) }
            firebaseId?.let { prefs[PreferencesKeys.FIREBASE_ID] = encrypt(it) }
            prefs[PreferencesKeys.USER_TYPE] = encrypt(userType.toString())
            prefs[PreferencesKeys.IS_REGISTERED] = isRegistered
        }
    }

    suspend fun saveStudioId(studioId: Long) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.STUDIO_ID] = studioId }
    }

    suspend fun saveUserId(userId: Long) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.USER_ID] = userId }
    }

    val accessTokenFlow: Flow<String?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.ACCESS]?.let { decrypt(it) } }

    val refreshTokenFlow: Flow<String?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.REFRESH]?.let { decrypt(it) } }

    val nameFlow: Flow<String?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.NAME]?.let { decrypt(it) } }

    val emailFlow: Flow<String?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.EMAIL]?.let { decrypt(it) } }

    val firebaseIdFlow: Flow<String?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.FIREBASE_ID]?.let { decrypt(it) } }

    val userTypeFlow: Flow<UserType?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            prefs[PreferencesKeys.USER_TYPE]?.let { decrypt(it)?.let(UserType::valueOf) }
        }

    val isRegisteredFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val value = preferences[PreferencesKeys.IS_REGISTERED] ?: false
            Log.d("SessionStore", "Emitting isRegistered: $value")
            value
        }


    val studioIdFlow: Flow<Long> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.STUDIO_ID] ?: -1L }

    val userIdFlow: Flow<Long> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs -> prefs[PreferencesKeys.USER_ID] ?: -1L }

    suspend fun updateIsRegistered() {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.IS_REGISTERED] = true }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
