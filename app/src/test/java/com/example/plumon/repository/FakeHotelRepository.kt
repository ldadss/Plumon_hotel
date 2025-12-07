package com.example.plumon.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.plumon.model.Reserva
import com.example.plumon.model.Usuario

class FakeHotelRepository : IHotelRepository {

    private val reservas = mutableStateListOf<Reserva>()
    private var nextId = 1

    override fun login(usuario: String, contrasena: String): Usuario? {
        // No es necesario para las pruebas del ViewModel de reservas
        return null
    }

    // --- MÉTODO QUE FALTABA ---
    override suspend fun refreshReservas() {
        // En el Fake, esta función no necesita hacer nada, ya que los datos
        // se añaden manualmente con addInitialReservas().
    }

    override fun getReservas(): SnapshotStateList<Reserva> {
        return reservas
    }

    override fun addReserva(reserva: Reserva) {
        reservas.add(reserva)
    }

    override fun updateReserva(reserva: Reserva) {
        val index = reservas.indexOfFirst { it.id == reserva.id }
        if (index != -1) {
            reservas[index] = reserva
        }
    }

    override fun getNextReservaId(): String {
        return "R${String.format("%03d", nextId++)}"
    }

    // Función de ayuda para que las pruebas puedan añadir datos iniciales.
    fun addInitialReservas(initialReservas: List<Reserva>) {
        reservas.addAll(initialReservas)
        nextId = initialReservas.size + 1
    }
}