package com.example.agora_app_events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.components.AgoraBottomNavBar
import com.example.agora_app_events.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onSaveClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onMyTicketsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("Juan Carlos") }
    var email by remember { mutableStateOf("juan_carlos@gmail.com") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Editar información",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AgoraDark
                )
            )
        },
        bottomBar = {
            AgoraBottomNavBar(
                selectedItem = 2,
                onItemSelected = { index ->
                    when (index) {
                        0 -> onHomeClick()
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
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD9D9D9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp),
                    tint = Color(0xFF8E8E8E)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form Fields
            EditField(
                label = "Nombre",
                value = name,
                onValueChange = { name = it },
                placeholder = "Nombre"
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditField(
                label = "Correo electrónico",
                value = email,
                onValueChange = { email = it },
                placeholder = "juan_carlos@gmail.com"
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditField(
                label = "Contraseña actual",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = "**************",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditField(
                label = "Nueva contraseña",
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "**************",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditField(
                label = "Confirmar nueva contraseña",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "**************",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Save Button
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF08AB31)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Guardar cambios",
                    style = Typography.titleLarge.copy(
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = Typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ),
            fontFamily = Poppins,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            placeholder = { 
                Text(
                    placeholder, 
                    fontFamily = Poppins, 
                    color = Color(0xFF9E9E9E),
                    fontSize = 14.sp
                ) 
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDDE1F0),
                unfocusedContainerColor = Color(0xFFDDE1F0),
                disabledContainerColor = Color(0xFFDDE1F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = Typography.bodyLarge.copy(fontSize = 14.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    AgoraTheme {
        EditProfileScreen()
    }
}
