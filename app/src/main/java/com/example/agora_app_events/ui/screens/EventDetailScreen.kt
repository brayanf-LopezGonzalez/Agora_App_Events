package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.theme.*
import com.example.agora_app_events.data.api.RetrofitClient
import com.example.agora_app_events.data.api.EventResponse
import com.example.agora_app_events.data.api.ZoneResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String = "",
    isLoggedIn: Boolean = false,
    onBackClick: () -> Unit = {},
    onBuyClick: (Int, Int, Double, String, String) -> Unit = { _, _, _, _, _ -> }, // zonaId, cantidad, total, eventName, date
    onLoginRequired: () -> Unit = {}
) {
    var eventData by remember { mutableStateOf<EventResponse?>(null) }
    var zones by remember { mutableStateOf<List<ZoneResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    val selectedQuantities = remember { mutableStateMapOf<Int, Int>() }

    LaunchedEffect(eventId) {
        try {
            val response = RetrofitClient.instance.getEventDetails(eventId)
            if (response.isSuccessful) {
                eventData = response.body()?.evento
                zones = response.body()?.zonas ?: emptyList()
                zones.forEach { selectedQuantities[it.id] = 0 }
            }
        } catch (e: Exception) {
            // Error
        } finally {
            isLoading = false
        }
    }

    val totalTickets = selectedQuantities.values.sum()
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle del evento",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AgoraDark
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AgoraOrange)
            }
        } else if (eventData == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se pudo cargar el evento", fontFamily = Poppins)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF3a4060)) {}
                    Surface(
                        color = StatusActive,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                    ) {
                        Text(
                            text = "Disponible",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = Poppins,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        color = Color(0xFFFFF0E0),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Evento",
                            color = AgoraOrange,
                            fontSize = 11.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }

                    Text(
                        text = eventData?.title ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp)) {
                        Icon(Icons.Outlined.CalendarMonth, null, tint = AgoraOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${eventData?.date} · ${eventData?.time}", fontSize = 13.sp, fontFamily = Poppins, color = Color(0xFF444444))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp)) {
                        Icon(Icons.Outlined.LocationOn, null, tint = AgoraOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(eventData?.venue ?: "Ubicación", fontSize = 13.sp, fontFamily = Poppins, color = Color(0xFF444444))
                    }

                    HorizontalDivider(color = Color(0xFFE0E0E0), modifier = Modifier.padding(vertical = 14.dp))

                    Text("Descripción", fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = Color.Black)
                    Text(eventData?.description ?: "", fontSize = 13.sp, fontFamily = Poppins, color = Color(0xFF666666), lineHeight = 20.sp)

                    HorizontalDivider(color = Color(0xFFE0E0E0), modifier = Modifier.padding(vertical = 14.dp))

                    Text("Tipos de boleto", fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = Color.Black, modifier = Modifier.padding(bottom = 10.dp))

                    zones.forEach { zone ->
                        val qty = selectedQuantities[zone.id] ?: 0
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF4F4F4),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text(zone.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = AgoraOrange)
                                    Text("$${zone.precio} MXN", fontSize = 13.sp, fontFamily = Poppins, color = Color.Black, fontWeight = FontWeight.Medium)
                                    Text("${zone.available} disponibles", fontSize = 11.sp, fontFamily = Poppins, color = Color.Gray)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { if (qty > 0) selectedQuantities[zone.id] = qty - 1 }, modifier = Modifier.size(32.dp)) {
                                        Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Text("$qty", fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = if (qty > 0) AgoraOrange else Color.Black, modifier = Modifier.widthIn(min = 24.dp))
                                    IconButton(onClick = { if (qty < zone.available) selectedQuantities[zone.id] = qty + 1 }, modifier = Modifier.size(32.dp)) {
                                        Surface(color = AgoraOrange, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(28.dp)) {
                                            Box(contentAlignment = Alignment.Center) { Text("+", color = Color.White, fontWeight = FontWeight.Bold) }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (showError) {
                        Text("Debes seleccionar al menos un tipo de boleto", color = Color.Red, fontSize = 11.sp, fontFamily = Poppins, modifier = Modifier.padding(vertical = 8.dp))
                    }

                    Button(
                        onClick = {
                            if (totalTickets == 0) showError = true
                            else {
                                showError = false
                                if (isLoggedIn) {
                                    val zone = zones.find { (selectedQuantities[it.id] ?: 0) > 0 }
                                    zone?.let { 
                                        onBuyClick(
                                            it.id, 
                                            selectedQuantities[it.id]!!, 
                                            it.precio * selectedQuantities[it.id]!!,
                                            eventData?.title ?: "",
                                            eventData?.date ?: ""
                                        ) 
                                    }
                                } else onLoginRequired()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (totalTickets > 0) AgoraOrange else Color.Gray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (totalTickets > 0) "Comprar boletos ($totalTickets)" else "Comprar boletos", color = Color.White, fontFamily = Poppins)
                    }
                }
            }
        }
    }
}
