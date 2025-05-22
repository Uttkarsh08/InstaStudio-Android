package com.uttkarsh.InstaStudio.utils.states

sealed class ProfileState {
        object Idle : ProfileState()

        object Loading : ProfileState()

        data class Success(
                val studioId: Long,
                val userId: Long
        ) : ProfileState()

        data class Error(val message: String) : ProfileState()
}