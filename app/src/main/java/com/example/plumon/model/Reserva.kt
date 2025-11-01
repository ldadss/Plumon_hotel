

package com.example.plumon.model

import org.threeten.bp.LocalDate // Importación necesaria para el tipo Date

data class Reserva(
    val id: String,
    val huesped: String,
    val habitacion: String,
    // ¡CAMPOS MODIFICADOS A LOCALDATE!
    val fechaEntrada: LocalDate,
    val fechaSalida: org.threeten.bp.LocalDate?,
    val estado: String
)