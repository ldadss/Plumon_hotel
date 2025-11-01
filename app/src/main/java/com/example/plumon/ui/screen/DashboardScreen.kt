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
import com.example.plumon.viewmodel.ReservationsViewModel

@Composable
fun DashboardScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToQRScreen: (String) -> Unit
) {
    val viewModel: ReservationsViewModel = viewModel()
    // Observa la lista de reservas directamente desde el ViewModel (mutableStateListOf)
    val reservas = viewModel.reservas

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recepción Principal", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateToCreate) {
                Text("Crear Nueva Reserva")
            }
            // Botón para ir a generar QR (usa la primera reserva pendiente)
            Button(onClick = {
                val nextReserva = viewModel.getNextPendingReserva()
                if (nextReserva != null) {
                    onNavigateToQRScreen(nextReserva.id) // Navega a la pantalla de QR
                }
            }) {
                Text("QR Check-in")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Lista de Reservas
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
            items(reservas) { reserva ->
                ReservaCard(reserva = reserva)
            }
        }
    }
}

// Componente para mostrar cada reserva en la lista
@Composable
fun ReservaCard(reserva: Reserva) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${reserva.id} | Habitación: ${reserva.habitacion}", style = MaterialTheme.typography.titleMedium)
            Text("Huésped: ${reserva.huesped}")

            // ***** CORRECCIÓN NECESARIA *****
            // Usa los nuevos campos fechaEntrada y fechaSalida
            Text("Entrada: ${reserva.fechaEntrada} | Salida: ${reserva.fechaSalida} | Estado: ${reserva.estado}",
                style = MaterialTheme.typography.bodySmall)
            // *******************************
        }
    }
}