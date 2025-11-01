package com.example.plumon.ui.screen

import android.content.Context
import android.util.Log // <--- (1) Solución: Logging de Android
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier // <--- (2) Solución: Modificadores de Compose
import androidx.compose.foundation.layout.fillMaxSize // <--- (3) Solución: Modificador fillMaxSize
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner // <--- (4) Solución: LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraPreviewComposable(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Objeto que representa la vista tradicional de la cámara
    val previewView = remember { PreviewView(context) }

    // Ejecutor para el procesamiento de la cámara
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    // Este efecto maneja el ciclo de vida (iniciar/detener la cámara)
    DisposableEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // 1. Configurar la vista previa y el proveedor de superficie
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // 2. Seleccionar la cámara trasera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 3. Desvincular todas las vistas y vincular la nuestra
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview
                )
            } catch (exc: Exception) {
                // Manejar error si la cámara no se puede enlazar
                Log.e("CAMERA", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))

        // Al salir de la pantalla, apaga el ejecutor de la cámara
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // El Composable que inserta la vista tradicional de Android (PreviewView)
    AndroidView(
        factory = { previewView },
        modifier = modifier
    )
}