package com.jeudry.auth.presentation.login

sealed interface LoginEvent {
    data object Success: LoginEvent
}