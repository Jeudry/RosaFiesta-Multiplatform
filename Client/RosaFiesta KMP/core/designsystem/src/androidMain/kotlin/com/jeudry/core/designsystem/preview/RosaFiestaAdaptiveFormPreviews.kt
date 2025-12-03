package com.jeudry.core.designsystem.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.jeudry.core.designsystem.components.brand.RosaFiestaBrandLogo
import com.jeudry.core.designsystem.components.layouts.RosaFiestaAdaptiveFormLayout
import com.jeudry.core.designsystem.theme.RosaFiestaTheme

@Composable
@PreviewLightDark
@PreviewScreenSizes
fun RosaFiestaAdaptiveFormLayoutLightPreview() {
    RosaFiestaTheme {
        RosaFiestaAdaptiveFormLayout(
            headerText = "Welcome to RosaFiesta!",
            errorText = "Login failed!",
            logo = { RosaFiestaBrandLogo() },
            formContent = {
                Text(
                    text = "Sample form title",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sample form title 2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}