package com.example.agora_app_events.data.api

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id_evento") val id: Int,
    @SerializedName("nombre_evento") val title: String?,
    @SerializedName("descripcion") val description: String?,
    @SerializedName("fecha") val date: String?,
    @SerializedName("hora_inicio") val time: String?,
    @SerializedName("nombre_recinto") val venue: String?
)

data class LoginRequest(
    val correo: String,
    val password: String
)

data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val password: String
)

data class LoginResponse(
    val status: String,
    val message: String,
    val user: UserData?
)

data class UserData(
    val id: Int,
    val nombre: String
)

data class EventDetailsResponse(
    val evento: EventResponse,
    val zonas: List<ZoneResponse>
)

data class ZoneResponse(
    @SerializedName("id_zona") val id: Int,
    @SerializedName("nombre_zona") val name: String,
    val precio: Double,
    @SerializedName("lugares_disponibles") val available: Int
)

data class ReservationRequest(
    @SerializedName("id_usuario") val userId: Int,
    @SerializedName("id_evento") val eventId: Int,
    @SerializedName("id_zona") val zoneId: Int,
    val cantidad: Int,
    val total: Double
)

data class TicketResponse(
    @SerializedName("id_reservacion") val id: Int,
    @SerializedName("nombre_evento") val eventName: String,
    val fecha: String,
    @SerializedName("hora_inicio") val time: String,
    @SerializedName("estado_reservacion") val status: String,
    @SerializedName("nombre_zona") val zone: String,
    val precio: Double,
    @SerializedName("espacios_reservados") val quantity: Int,
    val asientos: String?
)

data class CancelRequest(
    @SerializedName("id_reservacion") val id: Int
)

data class ReservationResponse(
    val status: String,
    val message: String,
    @SerializedName("id_reservacion") val reservationId: Int?
)
