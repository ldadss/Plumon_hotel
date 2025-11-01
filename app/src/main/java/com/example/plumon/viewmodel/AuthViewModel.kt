package com.example.plumon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf // Necesitas esta importación para los estados
import com.example.plumon.model.Usuario
import com.example.plumon.repository.HotelRepository

class AuthViewModel(private val repository: HotelRepository = HotelRepository()) : ViewModel() {

    // El usuario autenticado (observable por la UI)
    var currentUser = mutableStateOf<Usuario?>(null)
        private set

    // ESTADO AÑADIDO: Almacena el rol del usuario para la lógica de navegación en Compose
    var userRole = mutableStateOf<String?>(null)
        private set

    fun performLogin(usuario: String, contrasena: String): Boolean {
        val user = repository.login(usuario, contrasena)
        currentUser.value = user

        if (user != null) {
            userRole.value = user.rol // <--- Almacena el rol después del login exitoso
        } else {
            userRole.value = null
        }

        return user != null
    }

    fun logout() {
        currentUser.value = null
        userRole.value = null
    }
}