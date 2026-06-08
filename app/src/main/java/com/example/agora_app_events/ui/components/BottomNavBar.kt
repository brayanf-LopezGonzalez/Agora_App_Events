package com.example.agora_app_events.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agora_app_events.ui.theme.AgoraDark
import com.example.agora_app_events.ui.theme.AgoraOrange
import com.example.agora_app_events.ui.theme.Poppins

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
