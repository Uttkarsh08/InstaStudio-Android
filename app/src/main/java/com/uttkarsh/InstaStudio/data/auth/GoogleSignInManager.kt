package com.uttkarsh.InstaStudio.data.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.uttkarsh.InstaStudio.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GoogleSignInManager @Inject constructor() {

    suspend fun signInWithGoogle(activity: Activity): String? {
        val credentialManager = CredentialManager.create(activity)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(activity.getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(activity, request)
            val credential = result.credential
            Log.d("GoogleSignIn", "Got Credentials: $credential")
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                Log.d("GoogleSignIn", "Got Google ID Token: $idToken")

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                val firebaseAuth = FirebaseAuth.getInstance()
                val authResult = withContext(Dispatchers.IO) { firebaseAuth.signInWithCredential(firebaseCredential).await() }
                val firebaseUser = authResult.user

                val firebaseIdTokenResult = firebaseUser?.getIdToken(false)?.await()
                val firebaseIdToken = firebaseIdTokenResult?.token
                val firebaseId = firebaseUser?.uid

                Log.d("GoogleSignIn", "Firebase ID : $firebaseId")
                Log.d("GoogleSignIn", "Firebase ID Token: $firebaseIdToken")

                firebaseIdToken
            } else {
                Log.e("GoogleSignIn", "Invalid credential type")
                null
            }
        }catch (e: NoCredentialException) {
            Log.e("GoogleSignIn", "No credentials available. Possibly no Google account or wrong config.", e)
            null
        }
        catch (e: Exception) {
            Log.e("GoogleSignIn", "Google sign-in failed", e)
            null
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}
