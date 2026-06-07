package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.theme.*

@Composable
fun TicketSuccessScreen(
    onViewReservationsClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Reservación confirmada!",
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Tu boleto ha sido generado.\nPreséntalo en la entrada del evento",
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)
        )

        // Ticket Card
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F2F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                // Ticket Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AgoraDark)
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "América vs Chivas",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                        Text(
                            text = "Partido deportivo",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            fontFamily = Poppins
                        )
                    }
                }

                // Ticket Body
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        TicketDetailItem(label = "TITULAR", value = "Juan Carlos")
                        TicketDetailItem(label = "FECHA Y HORA", value = "Martes 4 Nov, 2025 - 06:00 pm")
                        TicketDetailItem(label = "ASIENTOS", value = "B1, B2, B3 - Zona B")
                        TicketDetailItem(label = "CANTIDAD", value = "3 boletos")
                        TicketDetailItem(label = "TOTAL PAGADO", value = "$600 MXN")
                    }

                    // QR Code Placeholder
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = "QR Code",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onViewReservationsClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgoraOrange),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Ver mis reservaciones",
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TicketDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = label,
            fontFamily = Poppins,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = value,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TicketSuccessScreenPreview() {
    AgoraTheme {
        TicketSuccessScreen()
    }
}
