package com.jeudry.rosafiesta

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}