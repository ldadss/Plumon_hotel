package com.example.plumon.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.plumon.model.Reserva
import com.example.plumon.model.Usuario
import org.threeten.bp.LocalDate // Importación necesaria

class HotelRepository {

    // Lista de Reservas (Mutable) para que los cambios se reflejen
    private val _reservas = mutableStateListOf(
        // Datos simulados con sintaxis LocalDate.parse corregida
        Reserva("R001", "Pedro Hernandez", "101", org.threeten.bp.LocalDate.parse("2025-11-20"), org.threeten.bp.LocalDate.parse("2025-11-25"), "pendiente"),
        Reserva("R002", "Ignacio Cerda", "205", org.threeten.bp.LocalDate.parse("2025-11-21"), org.threeten.bp.LocalDate.parse("2025-11-30"), "pendiente")
    )

    // HotelRepository.kt - Modificar la función login

    fun login(usuario: String, contrasena: String): Usuario? {
        // Lógica para el Recepcionista (Se queda igual)
        if (usuario == "recepcionista" && contrasena == "hotel123") {
            return Usuario("Recepción Principal", usuario, "Recepcionista")
        }
        // Lógica para el Huésped (Usa el ID de Reserva como credencial)
        else if (usuario == "R001" && contrasena == "1234") {
            return Usuario("Pedro Hernandez", usuario, "Huesped")
        }
        // Si no es ninguno
        return null
    }

    // Funciones de CRUD
    fun getReservas() = _reservas
    fun addReserva(reserva: Reserva) { _reservas.add(reserva) }
    fun updateReserva(reserva: Reserva) {
        val index = _reservas.indexOfFirst { it.id == reserva.id }
        if (index != -1) { _reservas[index] = reserva }
    }
    fun getNextReservaId(): String {
        val nextId = _reservas.size + 1
        return "R${String.format("%03d", nextId)}"
    }
}