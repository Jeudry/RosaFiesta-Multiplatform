package com.plcoding.rosafiesta

import androidx.compose.ui.window.TrayState
import com.plcoding.rosafiesta.windows.WindowState
import com.plcoding.core.domain.preferences.ThemePreference

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState()),
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val trayState: TrayState = TrayState()
)
