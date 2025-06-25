package com.uttkarsh.InstaStudio.utils.session

import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val sessionStore: SessionStore
) {
    private var _studioId: Long? = null
    private var _userId: Long? = null

    suspend fun getStudioId(): Long {
        if (_studioId == null) {
            _studioId = sessionStore.studioIdFlow.first()
        }
        return _studioId ?: throw IllegalStateException("StudioId not loaded")
    }

    suspend fun getUserId(): Long {
        if (_userId == null) {
            _userId = sessionStore.userIdFlow.first()
        }
        return _userId ?: throw IllegalStateException("UserId not loaded")
    }

    suspend fun updateStudioAndUserId(studioId: Long, userId: Long) {
        _studioId = studioId
        _userId = userId
        sessionStore.saveStudioId(studioId)
        sessionStore.saveUserId(userId)
    }

    fun clear() {
        _studioId = null
        _userId = null
    }
}
