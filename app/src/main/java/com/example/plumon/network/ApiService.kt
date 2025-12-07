package com.example.plumon.network

import com.example.plumon.model.Reserva
import retrofit2.http.GET

interface ApiService {
    /**
     * Obtiene la lista de todas las reservas desde el servidor.
     * La anotación @GET especifica la ruta del endpoint en la API.
     */
    // ¡RECUERDA REEMPLAZAR "a1b2c3d4e5f6g7h8i9" CON TU CÓDIGO DE NPOINT.IO!
    @GET("f45c5b61a3ea330cda33")
    suspend fun getReservas(): List<Reserva>
}
