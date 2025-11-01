package com.example.plumon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background // <-- ¡IMPORTACIÓN CRUCIAL!
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumon.viewmodel.AuthViewModel
import com.example.plumon.model.Usuario
import com.example.plumon.R

@Composable
fun LoginScreen(onLoginSuccess: (Usuario) -> Unit) {
    val viewModel: AuthViewModel = viewModel()
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            // 1. AÑADE EL MODIFICADOR BACKGROUND PARA USAR EL COLOR DEL TEMA
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Título y Espaciado Inicial ---
        // 2. EL COLOR DE LAS LETRAS SERÁ AUTOMÁTICAMENTE MORADO
        //    gracias a la propiedad onBackground de tu tema.
        Text("Bienvenidos a Hotel Plumon", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(40.dp))

        // ***** LOGO *****
        Image(
            painter = painterResource(id = R.drawable.ic_plumon_logo_placeholder),
            contentDescription = "Logo del Hotel Plumon",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 30.dp)
        )
        // ******************

        // Campo Usuario
        OutlinedTextField(value = usuario, onValueChange = { usuario = it }, label = { Text("usuario") })

        Spacer(Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(value = contrasena, onValueChange = { contrasena = it }, label = { Text("contraseña") })

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.performLogin(usuario, contrasena)) {
                    onLoginSuccess(viewModel.currentUser.value!!)
                } else {
                    loginError = true
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            // El botón usará el color morado de colorPrimary
            Text("ingresar")
        }

        if (loginError) {
            Text("Credenciales incorrectas.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        // Enlace de recuperación
        Text("olvido la contraseña", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary), modifier = Modifier.padding(top = 24.dp).clickable { /* Lógica de recuperación */ })
    }
}