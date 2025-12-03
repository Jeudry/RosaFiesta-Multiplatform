package com.plcoding.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import rosafiesta.feature.chat.presentation.generated.resources.Res
import rosafiesta.feature.chat.presentation.generated.resources.add
import rosafiesta.feature.chat.presentation.generated.resources.email_or_username
import com.plcoding.core.designsystem.components.buttons.RosaFiestaButton
import com.plcoding.core.designsystem.components.buttons.RosaFiestaButtonStyle
import com.plcoding.core.designsystem.components.textfields.RosaFiestaTextField
import com.plcoding.core.presentation.util.UiText
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatParticipantSearchTextSection(
    queryState: TextFieldState,
    onAddClick: () -> Unit,
    isSearchEnabled: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    error: UiText? = null,
    onFocusChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RosaFiestaTextField(
            state = queryState,
            modifier = Modifier
                .weight(1f),
            placeholder = stringResource(Res.string.email_or_username),
            title = null,
            supportingText = error?.asString(),
            isError = error != null,
            singleLine = true,
            keyboardType = KeyboardType.Email,
            onFocusChanged = onFocusChanged
        )
        RosaFiestaButton(
            text = stringResource(Res.string.add),
            onClick = onAddClick,
            style = RosaFiestaButtonStyle.SECONDARY,
            enabled = isSearchEnabled,
            isLoading = isLoading,
        )
    }
}