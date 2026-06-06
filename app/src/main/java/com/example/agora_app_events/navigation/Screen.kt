package com.example.agora_app_events.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object MyTickets : Screen("my_tickets")
    object Profile : Screen("profile")
    object EventDetail : Screen("event/{eventId}") {
        fun route(id: String) = "event/$id"
    }
}