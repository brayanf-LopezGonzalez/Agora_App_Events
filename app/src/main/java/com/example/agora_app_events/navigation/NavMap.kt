package com.example.agora_app_events.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agora_app_events.ui.screens.EventDetailScreen
import com.example.agora_app_events.ui.screens.HomeScreen
import com.example.agora_app_events.ui.screens.LoginScreen
import com.example.agora_app_events.ui.screens.RegisterScreen
import com.example.agora_app_events.ui.screens.ConfirmReservationScreen
import com.example.agora_app_events.ui.screens.PaymentScreen
import com.example.agora_app_events.ui.screens.TicketSuccessScreen

@Composable
fun NavMap() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                isLoggedIn = isLoggedIn,
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onMyTicketsClick = {
                    if (isLoggedIn) navController.navigate(Screen.MyTickets.route)
                    else navController.navigate(Screen.Login.route)
                },
                onProfileClick = {
                    if (isLoggedIn) navController.navigate(Screen.Profile.route)
                    else navController.navigate(Screen.Login.route)
                },
                onEventClick = { eventId, hasVenue ->
                    navController.navigate(Screen.EventDetail.route(eventId, hasVenue))
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    isLoggedIn = true
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    isLoggedIn = true
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(
                androidx.navigation.navArgument("eventId") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("hasVenue") {
                    type = androidx.navigation.NavType.BoolType
                }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            val hasVenue = backStackEntry.arguments?.getBoolean("hasVenue") ?: true
            EventDetailScreen(
                eventId = eventId,
                hasVenue = hasVenue,
                isLoggedIn = isLoggedIn,
                onBackClick = { navController.popBackStack() },
                onBuyClick = { navController.navigate(Screen.ConfirmReservation.route) },
                onLoginRequired = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.ConfirmReservation.route) {
            ConfirmReservationScreen(
                onConfirmClick = {
                    navController.navigate(Screen.Payment.route)
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                onPaymentSuccess = {
                    navController.navigate(Screen.TicketSuccess.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TicketSuccess.route) {
            TicketSuccessScreen(
                onViewReservationsClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }
}