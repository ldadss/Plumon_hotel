package com.example.plumon.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.plumon.model.Reserva
import com.example.plumon.model.Usuario
import com.example.plumon.network.ApiService
import com.example.plumon.network.RetrofitClient
import java.io.IOException

class HotelRepository(private val apiService: ApiService = RetrofitClient.apiService) : IHotelRepository {

    // La lista ahora comienza vacía y se llenará con los datos de la API.
    private val _reservas = mutableStateListOf<Reserva>()

    override suspend fun refreshReservas() {
        try {
            // Llama al ApiService para obtener las reservas
            val nuevasReservas = apiService.getReservas()
            _reservas.clear() // Limpia la lista actual
            _reservas.addAll(nuevasReservas) // Añade los nuevos datos
            Log.d("HotelRepository", "Reservas actualizadas: ${_reservas.size} encontradas")
        } catch (e: IOException) {
            // Maneja errores de red (ej. sin conexión)
            Log.e("HotelRepository", "Error de red al actualizar reservas", e)
        } catch (e: Exception) {
            // Maneja otros errores (ej. problemas con el JSON, etc.)
            Log.e("HotelRepository", "Error inesperado al actualizar reservas", e)
        }
    }

    override fun login(usuario: String, contrasena: String): Usuario? {
        // Esta lógica puede quedarse como está o también podría consumir un endpoint de login.
        if (usuario == "recepcionista" && contrasena == "hotel123") {
            return Usuario("Recepción Principal", usuario, "Recepcionista")
        } else if (usuario == "R001" && contrasena == "1234") {
            return Usuario("Pedro Hernandez", usuario, "Huesped")
        }
        return null
    }

    override fun getReservas(): SnapshotStateList<Reserva> = _reservas

    // Estas funciones ahora operarían sobre la lista en memoria (cache).
    // Para una app completa, deberían llamar a endpoints de la API (POST, PUT, etc.)
    override fun addReserva(reserva: Reserva) { _reservas.add(reserva) }
    override fun updateReserva(reserva: Reserva) {
        val index = _reservas.indexOfFirst { it.id == reserva.id }
        if (index != -1) { _reservas[index] = reserva }
    }
    override fun getNextReservaId(): String {
        val nextId = _reservas.size + 1
        return "R${String.format("%03d", nextId)}"
    }
}