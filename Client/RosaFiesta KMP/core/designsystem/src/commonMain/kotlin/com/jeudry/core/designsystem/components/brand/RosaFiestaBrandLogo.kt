package com.jeudry.core.designsystem.components.brand

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.vectorResource
import rosafiesta.core.designsystem.generated.resources.Res
import rosafiesta.core.designsystem.generated.resources.logo_rosafiesta

/// <summary>
/// Displays the RosaFiesta brand logo as an icon
/// </summary>
@Composable
fun RosaFiestaBrandLogo(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = vectorResource(Res.drawable.logo_rosafiesta),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}