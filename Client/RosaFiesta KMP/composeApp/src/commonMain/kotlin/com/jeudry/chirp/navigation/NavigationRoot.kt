package com.jeudry.rosafiesta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jeudry.auth.presentation.navigation.AuthGraphRoutes
import com.jeudry.auth.presentation.navigation.authGraph
import com.jeudry.chat.presentation.navigation.ChatGraphRoutes
import com.jeudry.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(ChatGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            }
        )
        chatGraph(
            navController = navController,
            onLogout = {
                navController.navigate(AuthGraphRoutes.Graph) {
                    popUpTo(ChatGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            }
        )
    }
}