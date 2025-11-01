package com.example.plumon.viewmodel

import androidx.lifecycle.ViewModel
import com.example.plumon.model.Reserva
import com.example.plumon.repository.HotelRepository
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException
import androidx.compose.runtime.snapshots.SnapshotStateList

class ReservationsViewModel(private val repository: HotelRepository = HotelRepository()) : ViewModel() {

    val reservas: SnapshotStateList<Reserva>
        get() = repository.getReservas()

    // Función para crear una nueva reserva (desde el formulario)
    fun createReserva(huesped: String, habitacion: String, fechaEntradaStr: String, fechaSalidaStr: String) {

        val fechaEntradaDate: LocalDate
        val fechaSalidaDate: LocalDate

        try {
            // Conversión de String a LocalDate
            fechaEntradaDate = org.threeten.bp.LocalDate.parse(fechaEntradaStr)
            fechaSalidaDate = org.threeten.bp.LocalDate.parse(fechaSalidaStr)
        } catch (e: DateTimeParseException) {
            println("Error de formato de fecha: ${e.message}")
            return
        }

        val newReserva = Reserva(
            id = repository.getNextReservaId(),
            huesped = huesped,
            habitacion = habitacion,
            fechaEntrada = fechaEntradaDate,
            fechaSalida = fechaSalidaDate,
            estado = "pendiente"
        )
        repository.addReserva(newReserva)
    }

    // Funciones de utilidad y actualización
    fun getNextPendingReserva(): Reserva? {
        return reservas.firstOrNull { it.estado == "pendiente" }
    }

    fun markAsCheckedIn(reservaId: String) {
        val existingReserva = reservas.find { it.id == reservaId }
        if (existingReserva != null) {
            val updatedReserva = existingReserva.copy(estado = "ocupada")
            repository.updateReserva(updatedReserva)
        }
    }
}