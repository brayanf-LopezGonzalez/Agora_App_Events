package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.R
import com.example.agora_app_events.ui.theme.*

data class Event(
    val id: Int,
    val title: String,
    val location: String,
    val date: String,
    val availableSpots: Int,
    val totalSpots: Int,
    val status: EventStatus,
    val backgroundColor: Color
)

enum class EventStatus {
    AVAILABLE, FULL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isLoggedIn: Boolean = false,
    onLoginClick: () -> Unit = {},
    onMyTicketsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val categories = listOf(
        "Todos", "Concierto", "Evento deportivo",
        "Obra de teatro", "Conferencia", "Festival", "Evento social"
    )
    var selectedCategory by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            AgoraBottomNavBar(
                selectedItem = selectedTab,
                onItemSelected = { index ->
                    when (index) {
                        0 -> selectedTab = 0
                        1 -> onMyTicketsClick()
                        2 -> onProfileClick()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Navbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AgoraDark)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.agora_logo),
                    contentDescription = "agora",
                    modifier = Modifier.height(24.dp)
                )
                if (isLoggedIn) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.White
                    )
                } else {
                    Button(
                        onClick = onLoginClick,
                        colors = ButtonDefaults.buttonColors(containerColor = AgoraOrange),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Iniciar sesion",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = Poppins
                        )
                    }
                }
            }

            // scrolleable
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = "¿Qué evento buscas?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text("Buscar eventos...", fontFamily = Poppins, color = Color.Gray)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = AgoraOrange
                        )
                    )

                    // categories with horizontal scroll
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 16.dp)
                    ) {
                        categories.forEach { category ->
                            val isSelected = selectedCategory == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = category },
                                label = {
                                    Text(
                                        text = category,
                                        fontFamily = Poppins,
                                        fontSize = 12.sp,
                                        maxLines = 1
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AgoraOrange,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White,
                                    labelColor = Color.Black
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = Color.LightGray,
                                    selectedBorderColor = AgoraOrange
                                )
                            )
                        }
                    }

                    Text(
                        text = "Proximos eventos",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                val events = listOf(
                    Event(
                        id = 1,
                        title = "America vs Chivas",
                        location = "Arena Monterrey",
                        date = "15 Nov, 2025",
                        availableSpots = 120,
                        totalSpots = 500,
                        status = EventStatus.AVAILABLE,
                        backgroundColor = Color(0xFF8B0000) // Deep red for stadium feel
                    ),
                    Event(
                        id = 2,
                        title = "Chayanne",
                        location = "Arena Monterrey",
                        date = "15 Nov, 2025",
                        availableSpots = 0,
                        totalSpots = 500,
                        status = EventStatus.FULL,
                        backgroundColor = Color(0xFF4A0000) // Darker red for concert
                    )
                )

                items(events) { event ->
                    EventCard(event = event)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun AgoraBottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = AgoraDark,
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Inicio", Icons.Outlined.Home, 0),
            Triple("Mis boletos", Icons.Outlined.ConfirmationNumber, 1),
            Triple("Perfil", Icons.Outlined.Person, 2)
        )

        items.forEach { (label, icon, index) ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = { Icon(icon, contentDescription = label) },
                label = { 
                    Text(
                        text = label, 
                        fontFamily = Poppins,
                        fontSize = 10.sp,
                        fontWeight = if (selectedItem == index) FontWeight.Bold else FontWeight.Normal
                    ) 
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AgoraOrange,
                    selectedTextColor = AgoraOrange,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = event.backgroundColor)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Status Badge (Top Right)
            Surface(
                color = if (event.status == EventStatus.AVAILABLE) StatusActive else StatusFull,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Text(
                    text = if (event.status == EventStatus.AVAILABLE) "Disponible" else "Lleno",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = Poppins,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = event.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
                Text(
                    text = event.location,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
                Text(
                    text = event.date,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
                Text(
                    text = "${event.availableSpots}/${event.totalSpots} disponibles",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
            }

            // Button (Bottom Right)
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (event.status == EventStatus.AVAILABLE) AgoraOrange else Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .height(32.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text(
                    text = if (event.status == EventStatus.AVAILABLE) "Ver detalles" else "No disponible",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = Poppins
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AgoraTheme {
        HomeScreen()
    }
}