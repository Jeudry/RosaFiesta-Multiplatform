package com.plcoding.rosafiesta

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}