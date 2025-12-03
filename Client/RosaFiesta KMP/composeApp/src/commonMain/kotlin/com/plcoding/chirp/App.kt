package com.plcoding.rosafiesta

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.chat.presentation.navigation.ChatGraphRoutes
import com.plcoding.rosafiesta.MainEvent
import com.plcoding.rosafiesta.MainViewModel
import com.plcoding.core.designsystem.theme.RosaFiestaTheme
import com.plcoding.rosafiesta.navigation.DeepLinkListener
import com.plcoding.rosafiesta.navigation.NavigationRoot
import com.plcoding.core.designsystem.theme.RosaFiestaTheme
import com.plcoding.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    onAuthenticationChecked: () -> Unit = {},
    onDeepLinkListenerSetup: () -> Unit = {},
    viewModel: MainViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth) {
        if(!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is MainEvent.OnSessionExpired -> {
                navController.navigate(AuthGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = false
                    }
                }
            }
        }
    }

    RosaFiestaTheme(
        darkTheme = isDarkTheme
    ) {
        if(!state.isCheckingAuth) {
            NavigationRoot(
                navController = navController,
                startDestination = if(state.isLoggedIn) {
                    ChatGraphRoutes.Graph
                } else {
                    AuthGraphRoutes.Graph
                }
            )
            DeepLinkListener(navController, onDeepLinkListenerSetup)
        }
    }
}