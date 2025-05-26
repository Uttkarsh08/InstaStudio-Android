package com.uttkarsh.InstaStudio.utils.api

import android.util.Log
import com.uttkarsh.InstaStudio.data.auth.AuthApiService
import com.uttkarsh.InstaStudio.domain.model.dto.auth.TokenRefreshRequestDTO
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val sessionStore: SessionStore,
    private val authApiService: AuthApiService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("Authenticator", "Started Authenticating")
        if (responseCount(response) >= 2) return null

        val refreshToken= runBlocking {
            sessionStore.refreshTokenFlow.firstOrNull()
        } ?: return null

        val requestDTO  = TokenRefreshRequestDTO(refreshToken)

        return try {
            val refreshResponse = runBlocking {
                authApiService.refreshToken(requestDTO)
            }
            Log.d("Authenticator", "refreshToken called")

            if (refreshResponse.data != null) {
                val newAccessToken = refreshResponse.data.accessToken
                val newRefreshToken = refreshResponse.data.refreshToken

                if (newAccessToken.isNotBlank() && newRefreshToken.isNotBlank()) {
                    runBlocking {
                        sessionStore.saveTokens(newAccessToken, newRefreshToken)
                    }
                    Log.d("Authenticator", "Saved new token to store")
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()

                } else null
            } else null

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var current = response.priorResponse
        while (current != null) {
            count++
            current = current.priorResponse
        }
        return count
    }
}