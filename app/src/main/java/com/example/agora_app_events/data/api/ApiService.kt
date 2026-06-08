package com.example.agora_app_events.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("get_events.php")
    suspend fun getEvents(): Response<List<EventResponse>>

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register.php")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @GET("get_event_details.php")
    suspend fun getEventDetails(@Query("id") eventId: String): Response<EventDetailsResponse>

    @POST("create_reservation.php")
    suspend fun createReservation(@Body request: ReservationRequest): Response<ReservationResponse>

    @GET("get_my_tickets.php")
    suspend fun getMyTickets(@Query("user_id") userId: Int): Response<List<TicketResponse>>

    @POST("cancel_reservation.php")
    suspend fun cancelReservation(@Body request: CancelRequest): Response<LoginResponse>
}
