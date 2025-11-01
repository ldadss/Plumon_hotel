package com.example.plumon.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumon.viewmodel.ReservationsViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
@Composable
fun ReservationFormScreen(onReservaSaved: () -> Unit) {
    val viewModel: ReservationsViewModel = viewModel()

    // Estados del formulario
    var huesped by remember { mutableStateOf("") }
    var habitacion by remember { mutableStateOf("") }
    var fechaEntradaStr by remember { mutableStateOf("") }
    var fechaSalidaStr by remember { mutableStateOf("") }

    // 1. Estado y Scope para el Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Envuelve el Column en un Scaffold para poder usar SnackbarHost
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplica el padding del Scaffold
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Crear Nueva Reserva", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(30.dp))

            // ... (Tus campos OutlinedTextField para huesped, habitacion, fechaEntradaStr, fechaSalidaStr aquí) ...

            OutlinedTextField(value = huesped, onValueChange = { huesped = it }, label = { Text("Nombre Huésped") })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = habitacion, onValueChange = { habitacion = it }, label = { Text("Habitación") })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = fechaEntradaStr, onValueChange = { fechaEntradaStr = it }, label = { Text("Fecha Entrada (YYYY-MM-DD)") })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = fechaSalidaStr, onValueChange = { fechaSalidaStr = it }, label = { Text("Fecha Salida (YYYY-MM-DD)") })

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                    // Validación para los CUATRO campos requeridos
                    if (huesped.isNotBlank() && habitacion.isNotBlank() && fechaEntradaStr.isNotBlank() && fechaSalidaStr.isNotBlank()) {

                        // LÓGICA DE ÉXITO
                        viewModel.createReserva(
                            huesped,
                            habitacion,
                            fechaEntradaStr,
                            fechaSalidaStr
                        )
                        onReservaSaved()

                    } else {
                        // 2. LÓGICA DEL ELSE: Muestra el Snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Error: Por favor, complete todos los campos.",
                                actionLabel = "Cerrar",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Guardar Reserva")
            }
        }
    }
}