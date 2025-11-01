// com.example.plumon.ui.screen.QRScreen.kt
package com.example.plumon.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumon.viewmodel.ReservationsViewModel
// Importaciones necesarias para el ícono
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner

@Composable
fun QRScreen(reservaId: String, onCheckInComplete: () -> Unit) {
    val viewModel: ReservationsViewModel = viewModel()

    // Busca la reserva actual para mostrar los detalles
    val reserva by remember { mutableStateOf(viewModel.reservas.find { it.id == reservaId }) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Check-in: Muestre este Código", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(30.dp))

        Text("Reserva ID: ${reservaId}", style = MaterialTheme.typography.titleMedium)
        Text("Huésped: ${reserva?.huesped ?: "Reserva no encontrada"}")

        // SIMULACIÓN del Área del Código QR (Mejorada visualmente)
        Card(
            modifier = Modifier.size(200.dp).padding(vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                // REEMPLAZAMOS EL TEXTO POR UN ÍCONO GRANDE DE QR
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = "Código QR PlaceHolder",
                    tint = MaterialTheme.colorScheme.primary, // Usa tu color morado/principal
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        // Simulación del Check-in (botón temporal que marca la reserva como 'ocupada')
        Button(
            onClick = {
                if (reserva != null) {
                    viewModel.markAsCheckedIn(reservaId) // Actualiza el estado en el ViewModel
                    onCheckInComplete() // Navega de vuelta
                }
            },
            enabled = reserva?.estado != "ocupada", // Deshabilita si ya está ocupada
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(if (reserva?.estado == "ocupada") "Check-in Completado" else "Simular Check-in")
        }
    }
}