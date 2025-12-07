// com.example.plumon.ui.screen.DashboardScreen.kt

package com.example.plumon.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumon.model.Reserva
import com.example.plumon.viewmodel.ReservationsUiState
import com.example.plumon.viewmodel.ReservationsViewModel

@Composable
fun DashboardScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToQRScreen: (String) -> Unit
) {
    val viewModel: ReservationsViewModel = viewModel()
    val uiState = viewModel.uiState

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recepción Principal", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateToCreate) {
                Text("Crear Nueva Reserva")
            }
            Button(onClick = {
                val nextReserva = viewModel.getNextPendingReserva()
                if (nextReserva != null) {
                    onNavigateToQRScreen(nextReserva.id)
                }
            }) {
                Text("QR Check-in")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Aquí es donde manejamos los diferentes estados de la UI
        when (uiState) {
            is ReservationsUiState.Loading -> {
                // Muestra un indicador de carga centrado
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ReservationsUiState.Success -> {
                // Muestra la lista de reservas si la carga fue exitosa
                LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                    items(uiState.reservas) { reserva ->
                        ReservaCard(reserva = reserva)
                    }
                }
            }
            is ReservationsUiState.Error -> {
                // Muestra un mensaje de error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.message}", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun ReservaCard(reserva: Reserva) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${reserva.id} | Habitación: ${reserva.habitacion}", style = MaterialTheme.typography.titleMedium)
            Text("Huésped: ${reserva.huesped}")
            Text("Entrada: ${reserva.fechaEntrada} | Salida: ${reserva.fechaSalida} | Estado: ${reserva.estado}",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}