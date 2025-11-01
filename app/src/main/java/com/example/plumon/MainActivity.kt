package com.example.plumon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel // Necesario para acceder al VM en AppNavigation

import com.example.plumon.model.Usuario
import com.example.plumon.ui.screen.* // Importa todas tus pantallas
import com.example.plumon.ui.theme.PlumonTheme // Tu tema Compose
import com.example.plumon.viewmodel.AuthViewModel // Necesario para acceder al rol

// --- Definición de Rutas de Navegación ---
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard") // Destino: Recepcionista
    object CreateReservation : Screen("create_reservation")
    object QRCheckIn : Screen("qr_checkin/{reservaId}") { // Destino: Recepcionista (Vista del QR)
        fun createRoute(reservaId: String) = "qr_checkin/$reservaId"
    }
    object GuestScan : Screen("guest_scan/{reservaId}") { // Nuevo Destino: Huésped (Escaneo)
        fun createRoute(reservaId: String) = "guest_scan/$reservaId"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlumonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Instancia del AuthViewModel para verificar el rol después del login
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // MainActivity.kt - Dentro de la función AppNavigation

        composable(Screen.Login.route) {
            // 1. Obtener el AuthViewModel
            val authViewModel: AuthViewModel = viewModel()

            LoginScreen(onLoginSuccess = { usuario: Usuario ->

                // 2. Lógica para REDIRIGIR según el rol:
                val destinationRoute = if (authViewModel.userRole.value == "Recepcionista") {
                    // ➡️ REDIRECCIÓN CORRECTA: Dashboard (para Recepcionista)
                    Screen.Dashboard.route
                } else {
                    // ➡️ Redirige al Huésped a su pantalla de escaneo
                    Screen.GuestScan.createRoute(usuario.usuario)
                }

                navController.navigate(destinationRoute) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        // 2. Pantalla del DASHBOARD (Recepcionista)
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToCreate = { navController.navigate(Screen.CreateReservation.route) },
                onNavigateToQRScreen = { reservaId ->
                    navController.navigate(Screen.QRCheckIn.createRoute(reservaId))
                }
            )
        }

        // 3. Pantalla de CREACIÓN DE RESERVA
        composable(Screen.CreateReservation.route) {
            ReservationFormScreen(
                onReservaSaved = { navController.popBackStack() }
            )
        }

        // 4. Pantalla de QR para Check-in (Recepcionista - Vista del Código)
        composable(
            route = Screen.QRCheckIn.route,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""

            QRScreen(
                reservaId = reservaId,
                onCheckInComplete = { navController.popBackStack() }
            )
        }

        // 5. Pantalla de ESCANEO para Huéspedes (Destino del Huésped)
        composable(
            route = Screen.GuestScan.route,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            // 1. EXTRACCIÓN: Correctamente recuperas el argumento
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""

            // 2. PASAR EL ARGUMENTO: Ahora debes pasar la variable a la función Composable
            GuestScanScreen(
                reservaId = reservaId, // <-- ¡AÑADE ESTA LÍNEA!
                onScanCompleted = { scannedCode ->
                    // Lógica de manejo de escaneo
                    navController.popBackStack()
                }
            )
        }
    }
}