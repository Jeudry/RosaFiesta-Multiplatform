package com.plcoding.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.presentation.util.currentDeviceConfiguration

@Composable
fun RosaFiestaAdaptiveDialogSheetLayout(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val configuration = currentDeviceConfiguration()
    if(configuration.isMobile) {
        RosaFiestaBottomSheet(
            onDismiss = onDismiss,
            content = content
        )
    } else {
        RosaFiestaDialogContent(
            onDismiss = onDismiss,
            content = content
        )
    }
}