package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ConfirmReservationScreen(
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Confirmar reservación",
            fontFamily = Poppins,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Revise el resumen de su reservación antes de confirmar",
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AgoraDark)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "América vs Chivas",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                SummaryRow(label = "Fecha", value = "Martes 4 Nov, 05:00 pm")
                SummaryRow(label = "Ubicación", value = "Estadio del Pueblo, La paz")
                SummaryRow(label = "Asientos", value = "B1, B2, B3")
                SummaryRow(label = "Zona", value = "Zona B")
                SummaryRow(label = "Cantidad", value = "3 boletos")

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.White.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total a pagar",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                    Text(
                        text = "$600 MXN",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .weight(1.2f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BtnConfirm),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Confirmar y pagar",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = onCancelClick,
                modifier = Modifier
                    .weight(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BtnDanger),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Cancelar",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontFamily = Poppins
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Poppins,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmReservationScreenPreview() {
    AgoraTheme {
        ConfirmReservationScreen()
    }
}
