package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.R
import com.example.agora_app_events.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isLoggedIn: Boolean = false,
    onLoginClick: () -> Unit = {}
) {
    val categories = listOf(
        "Todos", "Concierto", "Evento deportivo",
        "Obra de teatro", "Conferencia", "Festival", "Evento social"
    )
    var selectedCategory by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        // Navbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AgoraDark)
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
                    imageVector = androidx.compose.material.icons.Icons.Outlined.Notifications,
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