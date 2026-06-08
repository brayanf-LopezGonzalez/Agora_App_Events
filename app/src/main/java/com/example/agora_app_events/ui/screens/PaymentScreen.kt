package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.theme.*
import com.example.agora_app_events.data.api.RetrofitClient
import com.example.agora_app_events.data.api.ReservationRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    userId: Int,
    eventId: Int,
    zoneId: Int,
    quantity: Int,
    total: Double,
    onPaymentSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    
    var isLoading by remember { mutableStateOf(false) }
    var apiError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Realizar pago", color = Color.White, fontFamily = Poppins) },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AgoraDark)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Ingrese los datos de su tarjeta para completar la reservación",
                fontFamily = Poppins, fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center
            )

            // Resumen (Diseño Original)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AgoraDark.copy(alpha = 0.9f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Resumen de compra", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, fontFamily = Poppins)
                        Text("$quantity Boletos seleccionados", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                    }
                    Text("$${total} MXN", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                }
            }

            // Formulario
            PaymentField(label = "Nombre del titular", value = cardHolder, onValueChange = { cardHolder = it }, placeholder = "Como aparece en la tarjeta")
            Spacer(modifier = Modifier.height(16.dp))
            PaymentField(label = "Número de tarjeta", value = cardNumber, onValueChange = { if(it.length <= 16) cardNumber = it }, placeholder = "0000 0000 0000 0000", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    PaymentField(label = "Expiración", value = expiry, onValueChange = { if(it.length <= 5) expiry = it }, placeholder = "MM/AA")
                }
                Box(modifier = Modifier.weight(1f)) {
                    PaymentField(label = "CVV", value = cvv, onValueChange = { if(it.length <= 3) cvv = it }, placeholder = "000", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                }
            }

            if (apiError != null) {
                Text(apiError!!, color = Color.Red, fontSize = 12.sp, fontFamily = Poppins, modifier = Modifier.padding(top = 16.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        apiError = null
                        try {
                            val response = RetrofitClient.instance.createReservation(
                                ReservationRequest(userId, eventId, zoneId, quantity, total)
                            )
                            if (response.isSuccessful && response.body()?.status == "success") {
                                onPaymentSuccess()
                            } else {
                                val errorMsg = response.body()?.message ?: response.errorBody()?.string() ?: "Error de servidor"
                                apiError = errorMsg
                            }
                        } catch (e: Exception) {
                            apiError = "Fallo de conexión: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BtnConfirm),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("Pagar $${total} MXN", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Tarjetas aceptadas", fontFamily = Poppins, fontSize = 12.sp, color = TextHint)
            Text("VISA · Mastercard · AMEX · Bancomer · Santander", fontFamily = Poppins, fontSize = 11.sp, color = TextHint, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PaymentField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String, keyboardOptions: KeyboardOptions = KeyboardOptions.Default) {
    Column {
        Text(text = label, fontFamily = Poppins, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))
        TextField(
            value = value, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, fontSize = 14.sp, color = TextHint) },
            colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFF0F2F5), unfocusedContainerColor = Color(0xFFF0F2F5), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp), singleLine = true, keyboardOptions = keyboardOptions
        )
    }
}
