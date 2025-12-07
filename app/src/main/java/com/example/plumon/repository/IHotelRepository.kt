package com.example.plumon.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.plumon.model.Reserva
import com.example.plumon.model.Usuario

interface IHotelRepository {
    fun login(usuario: String, contrasena: String): Usuario?
    fun getReservas(): SnapshotStateList<Reserva>
    suspend fun refreshReservas() // Nueva función para actualizar desde la API
    fun addReserva(reserva: Reserva)
    fun updateReserva(reserva: Reserva)
    fun getNextReservaId(): String
}