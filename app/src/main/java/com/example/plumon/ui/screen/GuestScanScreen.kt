package com.example.plumon.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumon.viewmodel.ReservationsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

// Marcamos esta función como experimental por el uso de la biblioteca Accompanist
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GuestScanScreen(reservaId: String, onScanCompleted: (String) -> Unit) {

    val viewModel: ReservationsViewModel = viewModel()

    // 1. Define el estado del permiso de la cámara
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Check-in para Huéspedes", style = MaterialTheme.typography.headlineMedium)
        Text("Reserva ID: $reservaId", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))

        Spacer(Modifier.height(50.dp))

        // Lógica condicional basada en el estado del permiso
        when {
            // A. Permiso Concedido: Listo para escanear
            // GuestScanScreen.kt - dentro del bloque when donde el permiso es concedido

            cameraPermissionState.status.isGranted -> {

                // Texto de instrucción
                Text("Escaneando código QR del lobby...", color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(16.dp))

                // ***** INTEGRACIÓN DEL VISOR DE CÁMARA *****
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // Define un área visible para la cámara
                        .padding(horizontal = 16.dp)
                ) {
                    // Llama al Composable que activa el visor
                    CameraPreviewComposable()
                }
                // *****************************************

                // Botón Simulado (para iniciar la lógica de Check-in después de que se escanee algo)
                Button(
                    onClick = { /* Lógica de Check-in (mantener por ahora) */
                        onScanCompleted("LOBBY_CHECKIN_SUCCESS")
                    },
                    modifier = Modifier.padding(top = 24.dp).fillMaxWidth(0.8f)
                ) {
                    Text("Marcar Check-in Exitoso (Simulación)")
                }
            }

            // B. Permiso Denegado (pero se puede solicitar de nuevo) o Primera Vez
            cameraPermissionState.status.shouldShowRationale || !cameraPermissionState.status.isGranted -> {
                Text(
                    "Necesitamos acceso a la cámara para escanear el código QR en el lobby.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Button(
                    onClick = { cameraPermissionState.launchPermissionRequest() }, // Solicita el permiso
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Solicitar Acceso a Cámara")
                }
            }

            // C. Permiso Permanentemente Denegado (El usuario debe ir a Ajustes)
            else -> {
                Text("Acceso a cámara denegado permanentemente. Por favor, habilítelo en los ajustes del sistema.")
            }
        }
    }
}