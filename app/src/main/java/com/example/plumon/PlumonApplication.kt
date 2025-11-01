// PlumonApplication.kt

package com.example.plumon

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class PlumonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa el backport de la API de tiempo
        AndroidThreeTen.init(this)
    }
}