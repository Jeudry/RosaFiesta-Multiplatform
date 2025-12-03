package com.jeudry.rosafiesta

import androidx.compose.ui.window.TrayState
import com.jeudry.rosafiesta.windows.WindowState
import com.jeudry.core.domain.preferences.ThemePreference

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState()),
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val trayState: TrayState = TrayState()
)
