package com.example.plumon.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plumon.model.Reserva
import com.example.plumon.repository.HotelRepository
import com.example.plumon.repository.IHotelRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException
import androidx.compose.runtime.snapshots.SnapshotStateList

// Estado para la UI
sealed interface ReservationsUiState {
    object Loading : ReservationsUiState
    data class Success(val reservas: SnapshotStateList<Reserva>) : ReservationsUiState
    data class Error(val message: String) : ReservationsUiState
}

class ReservationsViewModel(private val repository: IHotelRepository = HotelRepository()) : ViewModel() {

    var uiState: ReservationsUiState by mutableStateOf(ReservationsUiState.Loading)
        private set

    init {
        // Carga las reservas tan pronto como el ViewModel se crea
        loadReservas()
    }

    fun loadReservas() {
        // Inicia el estado de carga
        uiState = ReservationsUiState.Loading
        viewModelScope.launch {
            try {
                repository.refreshReservas() // Llama a la función que busca en la API
                uiState = ReservationsUiState.Success(repository.getReservas())
            } catch (e: Exception) {
                uiState = ReservationsUiState.Error("Error al cargar las reservas: ${e.message}")
            }
        }
    }

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