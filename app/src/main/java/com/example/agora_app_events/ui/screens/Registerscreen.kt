package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.R
import com.example.agora_app_events.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.agora_logo),
                        contentDescription = "agora",
                        modifier = Modifier.height(24.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Regístrate con nosotros",
                style = Typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 24.sp
                ),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Nombre Field
            RegistrationTextField(
                label = "Nombre",
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = "Escriba su nombre..."
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email Field
            RegistrationTextField(
                label = "Correo electrónico",
                value = email,
                onValueChange = { email = it },
                placeholder = "tucorreo@ejemplo.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password Field
            RegistrationTextField(
                label = "Contraseña",
                value = password,
                onValueChange = { password = it },
                placeholder = "**************",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Confirm Password Field
            RegistrationTextField(
                label = "Confirmar contraseña",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "**************",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = { /* Handle registration */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BtnConfirm
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Crear cuenta",
                    style = Typography.titleLarge.copy(
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = TextSecondary)) {
                    append("¿Ya tiene cuenta? ")
                }
                withStyle(style = SpanStyle(color = AgoraOrange, fontWeight = FontWeight.Bold)) {
                    append("Iniciar sesión")
                }
            }

            Text(
                text = annotatedString,
                style = Typography.bodyMedium,
                fontFamily = Poppins,
                modifier = Modifier.clickable { /* Handle login navigation */ },
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun RegistrationTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            fontFamily = Poppins,
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
                    color = TextHint
                )
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDDE1F0),
                unfocusedContainerColor = Color(0xFFDDE1F0),
                disabledContainerColor = Color(0xFFDDE1F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AgoraTheme {
        RegisterScreen()
    }
}
