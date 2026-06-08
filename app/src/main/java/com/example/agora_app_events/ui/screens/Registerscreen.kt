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
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.R
import com.example.agora_app_events.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    fun validateEmail(value: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(value)
    }

    fun validate(): Boolean {
        var valid = true
        if (name.isBlank()) {
            nameError = "El nombre no puede estar vacío"
            valid = false
        } else {
            nameError = ""
        }
        if (!validateEmail(email)) {
            emailError = "Ingresa un correo válido (ejemplo@correo.com)"
            valid = false
        } else {
            emailError = ""
        }
        if (password.length < 8) {
            passwordError = "La contraseña debe tener al menos 8 caracteres"
            valid = false
        } else {
            passwordError = ""
        }
        if (confirmPassword != password) {
            confirmPasswordError = "Las contraseñas no coinciden"
            valid = false
        } else {
            confirmPasswordError = ""
        }
        return valid
    }

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
                    IconButton(onClick = { onBackClick() }) {
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

            // Name
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nombre",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = name,
                    onValueChange = {
                        if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                            name = it
                            if (nameError.isNotEmpty()) nameError = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("Escriba su nombre...", fontFamily = Poppins, color = TextHint) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (nameError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        unfocusedContainerColor = if (nameError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    isError = nameError.isNotEmpty()
                )
                if (nameError.isNotEmpty()) {
                    Text(
                        text = nameError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // email
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Correo electrónico",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (emailError.isNotEmpty()) emailError = ""
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("tucorreo@ejemplo.com", fontFamily = Poppins, color = TextHint) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (emailError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        unfocusedContainerColor = if (emailError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    isError = emailError.isNotEmpty()
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Password
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Contraseña",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (passwordError.isNotEmpty()) passwordError = ""
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("**************", color = TextHint) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = null,
                                tint = TextHint
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (passwordError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        unfocusedContainerColor = if (passwordError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = passwordError.isNotEmpty()
                )
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // confirm password
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Confirmar contraseña",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (confirmPasswordError.isNotEmpty()) confirmPasswordError = ""
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    placeholder = { Text("**************", color = TextHint) },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = null,
                                tint = TextHint
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = if (confirmPasswordError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        unfocusedContainerColor = if (confirmPasswordError.isNotEmpty()) Color(0xFFFFEEEE) else Color(0xFFDDE1F0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = confirmPasswordError.isNotEmpty()
                )
                if (confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmPasswordError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { if (validate()) onRegisterSuccess() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BtnConfirm),
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
                modifier = Modifier.clickable { onLoginClick() },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AgoraTheme {
        RegisterScreen()
    }
}