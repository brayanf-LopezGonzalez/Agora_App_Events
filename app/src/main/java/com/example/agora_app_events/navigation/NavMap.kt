package com.example.agora_app_events.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

import com.example.agora_app_events.ui.screens.MyTicketsScreen

@Composable
fun NavMap() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf(-1) }
    var userName by remember { mutableStateOf("") }
    
    // Estado temporal para la compra actual
    var selectedEventId by remember { mutableStateOf(-1) }
    var selectedEventName by remember { mutableStateOf("") }
    var selectedEventDate by remember { mutableStateOf("") }
    var selectedZoneId by remember { mutableStateOf(-1) }
    var selectedZoneName by remember { mutableStateOf("") }
    var selectedQuantity by remember { mutableStateOf(0) }
    var selectedTotal by remember { mutableStateOf(0.0) }

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
                onLoginSuccess = { user ->
                    isLoggedIn = true
                    userId = user.id
                    userName = user.nombre
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
                androidx.navigation.navArgument("eventId") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("hasVenue") { type = androidx.navigation.NavType.BoolType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(
                eventId = eventId,
                isLoggedIn = isLoggedIn,
                onBackClick = { navController.popBackStack() },
                onBuyClick = { zoneId, qty, total, eventName, date -> 
                    selectedEventId = eventId.toIntOrNull() ?: -1
                    selectedEventName = eventName
                    selectedEventDate = date
                    selectedZoneId = zoneId
                    selectedQuantity = qty
                    selectedTotal = total
                    
                    // Buscar el nombre de la zona real
                    selectedZoneName = "Zona Seleccionada" 
                    navController.navigate(Screen.ConfirmReservation.route)
                },
                onLoginRequired = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.ConfirmReservation.route) {
            ConfirmReservationScreen(
                eventName = selectedEventName,
                zoneName = selectedZoneName,
                quantity = selectedQuantity,
                total = selectedTotal,
                onConfirmClick = { navController.navigate(Screen.Payment.route) },
                onCancelClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                userId = userId,
                eventId = selectedEventId,
                zoneId = selectedZoneId,
                quantity = selectedQuantity,
                total = selectedTotal,
                onPaymentSuccess = {
                    navController.navigate(Screen.TicketSuccess.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.TicketSuccess.route) {
            TicketSuccessScreen(
                eventName = selectedEventName,
                userName = userName,
                date = selectedEventDate,
                zone = selectedZoneName,
                quantity = selectedQuantity,
                total = selectedTotal,
                onViewReservationsClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.MyTickets.route) {
            MyTicketsScreen(
                userId = userId,
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onViewTicket = { reservationId ->
                    navController.navigate(Screen.TicketSuccess.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Próximamente: Perfil", fontFamily = com.example.agora_app_events.ui.theme.Poppins)
            }
        }
    }
}
