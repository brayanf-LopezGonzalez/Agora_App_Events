package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.People
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String = "",
    hasVenue: Boolean = true,
    isLoggedIn: Boolean = false,
    onBackClick: () -> Unit = {},
    onBuyClick: () -> Unit = {},
    onLoginRequired: () -> Unit = {}
) {
    val venueZones = listOf("Zona VIP", "Zona A", "Zona B", "Zona C")
    val venuePrices = listOf("$1,200 MXN", "$800 MXN", "$700 MXN", "$700 MXN")
    val venueAvailable = listOf(133, 34, 42, 32)
    val venueQuantities = remember { mutableStateListOf(0, 0, 0, 0) }

    val noVenueTypes = listOf("Boleto VIP", "Boleto General")
    val noVenuePrices = listOf("$1,200 MXN", "$800 MXN")
    val noVenueAvailable = listOf(80, 133)
    val noVenueQuantities = remember { mutableStateListOf(0, 0) }

    val totalTickets = if (hasVenue) venueQuantities.sum() else noVenueQuantities.sum()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // image of the event
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF3a4060)
                ) {}
                Surface(
                    color = StatusActive,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
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
                // categories
                Surface(
                    color = Color(0xFFFFF0E0),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Evento deportivo",
                        color = AgoraOrange,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }

                // event name
                Text(
                    text = "América vs Chivas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Date
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = AgoraOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "15 Nov, 2025 · 7:00 PM",
                        fontSize = 13.sp,
                        fontFamily = Poppins,
                        color = Color(0xFF444444)
                    )
                }

                // location
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = AgoraOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Estadio Azteca, CDMX",
                        fontSize = 13.sp,
                        fontFamily = Poppins,
                        color = Color(0xFF444444)
                    )
                }

                // availability
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.People,
                        contentDescription = null,
                        tint = AgoraOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "320 / 500 disponibles",
                        fontSize = 13.sp,
                        fontFamily = Poppins,
                        color = Color(0xFF444444)
                    )
                }

                Divider(color = Color(0xFFE0E0E0))
                Spacer(modifier = Modifier.height(14.dp))

                // Description
                Text(
                    text = "Descripción",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "El Clásico Nacional regresa con toda la intensidad. América y Chivas se enfrentan en uno de los duelos más apasionantes del fútbol mexicano.",
                    fontSize = 13.sp,
                    fontFamily = Poppins,
                    color = Color(0xFF666666),
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(color = Color(0xFFE0E0E0))
                Spacer(modifier = Modifier.height(14.dp))

                // ticket types
                Text(
                    text = "Tipos de boleto",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                if (hasVenue) {
                    venueZones.forEachIndexed { index, zone ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF4F4F4),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(zone, fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = AgoraOrange)
                                    Text(venuePrices[index], fontSize = 13.sp, fontFamily = Poppins, color = Color.Black, fontWeight = FontWeight.Medium)
                                    Text("${venueAvailable[index]} disponibles", fontSize = 11.sp, fontFamily = Poppins, color = Color.Gray)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { if (venueQuantities[index] > 0) venueQuantities[index]-- }, modifier = Modifier.size(32.dp)) {
                                        Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                    }
                                    Text(
                                        text = "${venueQuantities[index]}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Poppins,
                                        color = if (venueQuantities[index] > 0) AgoraOrange else Color.Black,
                                        modifier = Modifier.widthIn(min = 24.dp)
                                    )
                                    IconButton(onClick = { venueQuantities[index]++ }, modifier = Modifier.size(32.dp)) {
                                        Surface(color = AgoraOrange, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(28.dp)) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    noVenueTypes.forEachIndexed { index, type ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF4F4F4),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(type, fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins, color = AgoraOrange)
                                    Text(noVenuePrices[index], fontSize = 13.sp, fontFamily = Poppins, color = Color.Black, fontWeight = FontWeight.Medium)
                                    Text("${noVenueAvailable[index]} disponibles", fontSize = 11.sp, fontFamily = Poppins, color = Color.Gray)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { if (noVenueQuantities[index] > 0) noVenueQuantities[index]-- }, modifier = Modifier.size(32.dp)) {
                                        Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                    }
                                    Text(
                                        text = "${noVenueQuantities[index]}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = Poppins,
                                        color = if (noVenueQuantities[index] > 0) AgoraOrange else Color.Black,
                                        modifier = Modifier.widthIn(min = 24.dp)
                                    )
                                    IconButton(onClick = { noVenueQuantities[index]++ }, modifier = Modifier.size(32.dp)) {
                                        Surface(color = AgoraOrange, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(28.dp)) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // error message
                if (showError) {
                    Text(
                        text = "Debes seleccionar al menos un boleto",
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // buy button
                Button(
                    onClick = {
                        if (totalTickets == 0) {
                            showError = true
                        } else {
                            showError = false
                            if (isLoggedIn) onBuyClick()
                            else onLoginRequired()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (totalTickets > 0) AgoraOrange else Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (totalTickets > 0) "Comprar boletos ($totalTickets)" else "Comprar boletos",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    AgoraTheme {
        EventDetailScreen()
    }
}