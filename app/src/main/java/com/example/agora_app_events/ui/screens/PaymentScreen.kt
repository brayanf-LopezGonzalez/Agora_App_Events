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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onPaymentSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Realizar pago",
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
                text = "Ingrese los datos de tu tarjeta para completar la reservación",
                fontFamily = Poppins,
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Purchase Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AgoraDark.copy(alpha = 0.9f))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Resumen de compra",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontFamily = Poppins
                        )
                        Text(
                            text = "América vs Chivas - 3 boletos",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }
                    Text(
                        text = "$600 MXN",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form Fields
            PaymentTextField(
                label = "Nombre del titular",
                value = cardHolder,
                onValueChange = { cardHolder = it },
                placeholder = "Como aparece en la tarjeta"
            )

            Spacer(modifier = Modifier.height(16.dp))

            PaymentTextField(
                label = "Número de tarjeta",
                value = cardNumber,
                onValueChange = { cardNumber = it },
                placeholder = "0000 0000 0000 0000",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    PaymentTextField(
                        label = "Expiración",
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        placeholder = "MM / AA",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    PaymentTextField(
                        label = "CVV",
                        value = cvv,
                        onValueChange = { cvv = it },
                        placeholder = "000",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onPaymentSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BtnConfirm),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Pagar $600 MXN",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Supported Cards Section
            Text(
                text = "Tarjetas aceptadas",
                fontFamily = Poppins,
                fontSize = 12.sp,
                color = TextHint,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "VISA · Mastercard · AMEX · Bancomer · Santander",
                fontFamily = Poppins,
                fontSize = 11.sp,
                color = TextHint,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PaymentTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = Poppins,
                    color = TextHint,
                    fontSize = 14.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F2F5),
                unfocusedContainerColor = Color(0xFFF0F2F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = keyboardOptions,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    AgoraTheme {
        PaymentScreen()
    }
}
