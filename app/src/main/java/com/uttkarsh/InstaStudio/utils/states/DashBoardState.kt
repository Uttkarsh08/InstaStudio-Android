package com.uttkarsh.InstaStudio.utils.states

sealed class DashBoardState {
        object Idle : DashBoardState()

        object Loading : DashBoardState()

        data class Success(
                val studioId: Long,
                val userId: Long
        ) : DashBoardState()

        data class Error(val message: String) : DashBoardState()
}