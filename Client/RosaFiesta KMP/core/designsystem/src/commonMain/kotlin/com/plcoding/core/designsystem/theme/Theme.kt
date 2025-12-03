package com.plcoding.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

val ColorScheme.extended: ExtendedColors
    @ReadOnlyComposable
    @Composable
    get() = LocalExtendedColors.current

@Immutable
data class ExtendedColors(
    // Button states
    val primaryHover: Color,
    val destructiveHover: Color,
    val destructiveSecondaryOutline: Color,
    val disabledOutline: Color,
    val disabledFill: Color,
    val successOutline: Color,
    val success: Color,
    val onSuccess: Color,
    val secondaryFill: Color,

    // Text variants
    val textPrimary: Color,
    val textTertiary: Color,
    val textSecondary: Color,
    val textPlaceholder: Color,
    val textDisabled: Color,

    // Surface variants
    val surfaceLower: Color,
    val surfaceHigher: Color,
    val surfaceOutline: Color,
    val overlay: Color,

    // Accent colors
    val accentBlue: Color,
    val accentPurple: Color,
    val accentViolet: Color,
    val accentPink: Color,
    val accentOrange: Color,
    val accentYellow: Color,
    val accentGreen: Color,
    val accentTeal: Color,
    val accentLightBlue: Color,
    val accentGrey: Color,

    // Cake colors for chat bubbles
    val cakeViolet: Color,
    val cakeGreen: Color,
    val cakeBlue: Color,
    val cakePink: Color,
    val cakeOrange: Color,
    val cakeYellow: Color,
    val cakeTeal: Color,
    val cakePurple: Color,
    val cakeRed: Color,
    val cakeMint: Color,
)

val LightExtendedColors = ExtendedColors(
    primaryHover = RosaFiestaBrand600,
    destructiveHover = RosaFiestaRed600,
    destructiveSecondaryOutline = RosaFiestaRed200,
    disabledOutline = RosaFiestaBase200,
    disabledFill = RosaFiestaBase150,
    successOutline = RosaFiestaBrand100,
    success = RosaFiestaBrand600,
    onSuccess = RosaFiestaBase0,
    secondaryFill = RosaFiestaBase100,

    textPrimary = RosaFiestaBase1000,
    textTertiary = RosaFiestaBase800,
    textSecondary = RosaFiestaBase900,
    textPlaceholder = RosaFiestaBase700,
    textDisabled = RosaFiestaBase400,

    surfaceLower = RosaFiestaBase100,
    surfaceHigher = RosaFiestaBase100,
    surfaceOutline = RosaFiestaBase1000Alpha14,
    overlay = RosaFiestaBase1000Alpha80,

    accentBlue = RosaFiestaBlue,
    accentPurple = RosaFiestaPurple,
    accentViolet = RosaFiestaViolet,
    accentPink = RosaFiestaPink,
    accentOrange = RosaFiestaOrange,
    accentYellow = RosaFiestaYellow,
    accentGreen = RosaFiestaGreen,
    accentTeal = RosaFiestaTeal,
    accentLightBlue = RosaFiestaLightBlue,
    accentGrey = RosaFiestaGrey,

    cakeViolet = RosaFiestaCakeLightViolet,
    cakeGreen = RosaFiestaCakeLightGreen,
    cakeBlue = RosaFiestaCakeLightBlue,
    cakePink = RosaFiestaCakeLightPink,
    cakeOrange = RosaFiestaCakeLightOrange,
    cakeYellow = RosaFiestaCakeLightYellow,
    cakeTeal = RosaFiestaCakeLightTeal,
    cakePurple = RosaFiestaCakeLightPurple,
    cakeRed = RosaFiestaCakeLightRed,
    cakeMint = RosaFiestaCakeLightMint,
)

val DarkExtendedColors = ExtendedColors(
    primaryHover = RosaFiestaBrand600,
    destructiveHover = RosaFiestaRed600,
    destructiveSecondaryOutline = RosaFiestaRed200,
    disabledOutline = RosaFiestaBase900,
    disabledFill = RosaFiestaBase1000,
    successOutline = RosaFiestaBrand500Alpha40,
    success = RosaFiestaBrand500,
    onSuccess = RosaFiestaBase1000,
    secondaryFill = RosaFiestaBase900,

    textPrimary = RosaFiestaBase0,
    textTertiary = RosaFiestaBase200,
    textSecondary = RosaFiestaBase150,
    textPlaceholder = RosaFiestaBase400,
    textDisabled = RosaFiestaBase500,

    surfaceLower = RosaFiestaBase1000,
    surfaceHigher = RosaFiestaBase900,
    surfaceOutline = RosaFiestaBase100Alpha10Alt,
    overlay = RosaFiestaBase1000Alpha80,

    accentBlue = RosaFiestaBlue,
    accentPurple = RosaFiestaPurple,
    accentViolet = RosaFiestaViolet,
    accentPink = RosaFiestaPink,
    accentOrange = RosaFiestaOrange,
    accentYellow = RosaFiestaYellow,
    accentGreen = RosaFiestaGreen,
    accentTeal = RosaFiestaTeal,
    accentLightBlue = RosaFiestaLightBlue,
    accentGrey = RosaFiestaGrey,

    cakeViolet = RosaFiestaCakeDarkViolet,
    cakeGreen = RosaFiestaCakeDarkGreen,
    cakeBlue = RosaFiestaCakeDarkBlue,
    cakePink = RosaFiestaCakeDarkPink,
    cakeOrange = RosaFiestaCakeDarkOrange,
    cakeYellow = RosaFiestaCakeDarkYellow,
    cakeTeal = RosaFiestaCakeDarkTeal,
    cakePurple = RosaFiestaCakeDarkPurple,
    cakeRed = RosaFiestaCakeDarkRed,
    cakeMint = RosaFiestaCakeDarkMint,
)

val LightColorScheme = lightColorScheme(
    primary = RosaFiestaBrand500,
    onPrimary = RosaFiestaBrand1000,
    primaryContainer = RosaFiestaBrand100,
    onPrimaryContainer = RosaFiestaBrand900,

    secondary = RosaFiestaBase700,
    onSecondary = RosaFiestaBase0,
    secondaryContainer = RosaFiestaBase100,
    onSecondaryContainer = RosaFiestaBase900,

    tertiary = RosaFiestaBrand900,
    onTertiary = RosaFiestaBase0,
    tertiaryContainer = RosaFiestaBrand100,
    onTertiaryContainer = RosaFiestaBrand1000,

    error = RosaFiestaRed500,
    onError = RosaFiestaBase0,
    errorContainer = RosaFiestaRed200,
    onErrorContainer = RosaFiestaRed600,

    background = RosaFiestaBrand1000,
    onBackground = RosaFiestaBase0,
    surface = RosaFiestaBase0,
    onSurface = RosaFiestaBase1000,
    surfaceVariant = RosaFiestaBase100,
    onSurfaceVariant = RosaFiestaBase900,

    outline = RosaFiestaBase1000Alpha8,
    outlineVariant = RosaFiestaBase200,
)

val DarkColorScheme = darkColorScheme(
    primary = RosaFiestaBrand500,
    onPrimary = RosaFiestaBrand1000,
    primaryContainer = RosaFiestaBrand900,
    onPrimaryContainer = RosaFiestaBrand500,

    secondary = RosaFiestaBase400,
    onSecondary = RosaFiestaBase1000,
    secondaryContainer = RosaFiestaBase900,
    onSecondaryContainer = RosaFiestaBase150,

    tertiary = RosaFiestaBrand500,
    onTertiary = RosaFiestaBase1000,
    tertiaryContainer = RosaFiestaBrand900,
    onTertiaryContainer = RosaFiestaBrand500,

    error = RosaFiestaRed500,
    onError = RosaFiestaBase0,
    errorContainer = RosaFiestaRed600,
    onErrorContainer = RosaFiestaRed200,

    background = RosaFiestaBase1000,
    onBackground = RosaFiestaBase0,
    surface = RosaFiestaBase950,
    onSurface = RosaFiestaBase0,
    surfaceVariant = RosaFiestaBase900,
    onSurfaceVariant = RosaFiestaBase150,

    outline = RosaFiestaBase100Alpha10,
    outlineVariant = RosaFiestaBase800,
)