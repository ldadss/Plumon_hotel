package com.example.plumon.network

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

/**
 * Este adaptador le enseña a Gson cómo convertir un String de fecha (ej. "2025-12-01")
 * en un objeto LocalDate y viceversa. Es crucial para que Retrofit pueda parsear la API.
 */
class LocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    // Define el formato de fecha que usa la API
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE // Formato: YYYY-MM-DD

    /**
     * Convierte un objeto LocalDate a un String JSON.
     */
    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(formatter))
    }

    /**
     * Convierte un String JSON a un objeto LocalDate.
     */
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate? {
        return if (json?.asString != null && json.asString.isNotEmpty()) {
            LocalDate.parse(json.asString, formatter)
        } else {
            null
        }
    }
}