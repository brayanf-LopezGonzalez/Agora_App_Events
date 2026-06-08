package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.R
import com.example.agora_app_events.data.api.RetrofitClient
import com.example.agora_app_events.data.api.TicketResponse
import com.example.agora_app_events.data.api.CancelRequest
import com.example.agora_app_events.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTicketsScreen(
    userId: Int,
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onViewTicket: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var tickets by remember { mutableStateOf<List<TicketResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    fun loadTickets() {
        scope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.instance.getMyTickets(userId)
                if (response.isSuccessful) {
                    tickets = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadTickets() }

    Scaffold(
        bottomBar = {
            AgoraBottomNavBar(
                selectedItem = 1, // "Mis boletos" es el índice 1
                onItemSelected = { index ->
                    when (index) {
                        0 -> onHomeClick()
                        1 -> { /* Ya estamos aquí */
                        }

                        2 -> onProfileClick()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF0F2F5))
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Banner Superior (Igual a la web)
            Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(AgoraDark)) {
                Image(
                    painter = painterResource(id = R.drawable.agora_logo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().alpha(0.3f),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(20.dp).align(Alignment.CenterStart)) {
                    Text(
                        "Tu escenario, tu evento.",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                    Text(
                        "Gestiona tus reservaciones aquí.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontFamily = Poppins
                    )
                }
            }

            Text(
                "Mis reservaciones",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.Black
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AgoraOrange)
                }
            } else if (tickets.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes reservaciones.", fontFamily = Poppins, color = Color.Gray)
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(tickets) { ticket ->
                            TicketItem(
                                ticket = ticket,
                                onView = { onViewTicket(ticket.id) },
                                onCancel = {
                                    scope.launch {
                                        try {
                                            val resp = RetrofitClient.instance.cancelReservation(
                                                CancelRequest(ticket.id)
                                            )
                                            if (resp.isSuccessful) loadTickets()
                                        } catch (e: Exception) {
                                        }
                                    }
                                }
                            )
                        }
                    }

                    // Botones de Navegación (Estilo Web)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "❮ Anterior",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            "1",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgoraDark,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text("|", color = Color.LightGray)
                        Text(
                            "2",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            "Siguiente ❯",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            color = AgoraDark,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TicketItem(ticket: TicketResponse, onView: () -> Unit, onCancel: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen pequeña del evento
                Surface(
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                    color = AgoraDark
                ) {
                    Icon(
                        Icons.Default.ConfirmationNumber,
                        null,
                        tint = Color.White,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        ticket.eventName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        "${ticket.fecha} - ${ticket.time}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = Poppins
                    )
                    Text(
                        "Asientos: ${ticket.asientos ?: "N/A"}",
                        fontSize = 12.sp,
                        color = AgoraOrange,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins
                    )
                }

                // Badge de estado
                Surface(
                    color = when (ticket.status) {
                        "Confirmado" -> StatusActive
                        "Cancelado" -> StatusCanceled
                        else -> StatusOngoing
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        ticket.status, color = Color.White, fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontFamily = Poppins, fontWeight = FontWeight.Bold
                    )
                }
            }

            // Botones de acción (Estilo Web)
            Row(modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Button(
                    onClick = onView,
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5F8F))
                ) {
                    Text("Ver boleto", fontSize = 12.sp, fontFamily = Poppins)
                }
                if (ticket.status != "Cancelado") {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BtnDanger)
                    ) {
                        Text("Cancelar", fontSize = 12.sp, fontFamily = Poppins)
                    }
                }
            }
        }
    }
}
