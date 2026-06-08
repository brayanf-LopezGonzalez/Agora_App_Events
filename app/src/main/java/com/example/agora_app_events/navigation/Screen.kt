package com.example.agora_app_events.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object MyTickets : Screen("my_tickets")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object ConfirmReservation : Screen("confirm_reservation")
    object Payment : Screen("payment")
    object TicketSuccess : Screen("ticket_success")
    object EventDetail : Screen("event/{eventId}/{hasVenue}") {
        fun route(id: String, hasVenue: Boolean) = "event/$id/$hasVenue"
    }
}