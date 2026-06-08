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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
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
    var cardNumberRaw by remember { mutableStateOf("") }
    var expiryRaw by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var cardHolderError by remember { mutableStateOf("") }
    var cardNumberError by remember { mutableStateOf("") }
    var expiryDateError by remember { mutableStateOf("") }
    var cvvError by remember { mutableStateOf("") }

    fun validate(): Boolean {
        var valid = true

        if (cardHolder.isBlank()) {
            cardHolderError = "Ingresa el nombre del titular"
            valid = false
        } else {
            cardHolderError = ""
        }

        if (cardNumberRaw.length != 16) {
            cardNumberError = "El número de tarjeta debe tener 16 dígitos"
            valid = false
        } else {
            cardNumberError = ""
        }

        val expiryRegex = Regex("^(0[1-9]|1[0-2])[0-9]{2}$")
        if (!expiryRegex.matches(expiryRaw)) {
            expiryDateError = "Formato inválido, usa MM/AA"
            valid = false
        } else {
            expiryDateError = ""
        }

        if (cvv.length != 3) {
            cvvError = "El CVV debe tener 3 dígitos"
            valid = false
        } else {
            cvvError = ""
        }

        return valid
    }

    val cardNumberTransformation = VisualTransformation { text ->
        val formatted = text.text.chunked(4).joinToString(" ")
        TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    val spaces = offset / 4
                    return (offset + spaces).coerceAtMost(formatted.length)
                }
                override fun transformedToOriginal(offset: Int): Int {
                    val spaces = offset / 5
                    return (offset - spaces).coerceAtMost(text.text.length)
                }
            }
        )
    }

    val expiryTransformation = VisualTransformation { text ->
        val formatted = if (text.text.length >= 2) {
            "${text.text.substring(0, 2)}/${text.text.substring(2)}"
        } else {
            text.text
        }
        TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset >= 2) (offset + 1).coerceAtMost(formatted.length) else offset
                }
                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset >= 3) (offset - 1).coerceAtMost(text.text.length) else offset
                }
            }
        )
    }

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

            // Card holder name
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nombre del titular",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = cardHolder,
                    onValueChange = {
                        if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                            cardHolder = it
                            if (cardHolderError.isNotEmpty()) cardHolderError = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("Como aparece en la tarjeta", fontFamily = Poppins, color = TextHint) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (cardHolderError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                        unfocusedContainerColor = if (cardHolderError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    isError = cardHolderError.isNotEmpty()
                )
                if (cardHolderError.isNotEmpty()) {
                    Text(
                        text = cardHolderError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card number
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Número de tarjeta",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = cardNumberRaw,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }
                        if (digits.length <= 16) {
                            cardNumberRaw = digits
                            if (cardNumberError.isNotEmpty()) cardNumberError = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("0000 0000 0000 0000", fontFamily = Poppins, color = TextHint) },
                    visualTransformation = cardNumberTransformation,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (cardNumberError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                        unfocusedContainerColor = if (cardNumberError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = cardNumberError.isNotEmpty()
                )
                if (cardNumberError.isNotEmpty()) {
                    Text(
                        text = cardNumberError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Expiry date
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Expiración",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = expiryRaw,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            if (digits.length <= 4) {
                                expiryRaw = digits
                                if (expiryDateError.isNotEmpty()) expiryDateError = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        placeholder = { Text("MM/AA", fontFamily = Poppins, color = TextHint) },
                        visualTransformation = expiryTransformation,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = if (expiryDateError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                            unfocusedContainerColor = if (expiryDateError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = expiryDateError.isNotEmpty()
                    )
                    if (expiryDateError.isNotEmpty()) {
                        Text(
                            text = expiryDateError,
                            color = Color.Red,
                            fontSize = 10.sp,
                            fontFamily = Poppins,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                }

                // CVV
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CVV",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = cvv,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            if (digits.length <= 3) {
                                cvv = digits
                                if (cvvError.isNotEmpty()) cvvError = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        placeholder = { Text("000", fontFamily = Poppins, color = TextHint) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = if (cvvError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                            unfocusedContainerColor = if (cvvError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFF0F2F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = cvvError.isNotEmpty()
                    )
                    if (cvvError.isNotEmpty()) {
                        Text(
                            text = cvvError,
                            color = Color.Red,
                            fontSize = 10.sp,
                            fontFamily = Poppins,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { if (validate()) onPaymentSuccess() },
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

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    AgoraTheme {
        PaymentScreen()
    }
}