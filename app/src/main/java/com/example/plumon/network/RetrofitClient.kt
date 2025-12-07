package com.example.plumon.network

import com.google.gson.GsonBuilder
import org.threeten.bp.LocalDate
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // URL base para el servicio npoint.io
    private const val BASE_URL = "https://api.npoint.io/"

    // 1. Crea una instancia de Gson personalizada que "sabe" cómo manejar LocalDate.
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    // 2. Crea una instancia "lazy" de Retrofit usando el Gson personalizado.
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // 3. Usa el convertidor de Gson con nuestra configuración personalizada.
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // Expone públicamente la instancia de ApiService para que el resto de la app pueda usarla
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}