plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.plumon"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.plumon"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// build.gradle.kts (Module: app) - Bloque dependencies

dependencies {
    // 1. FUNDAMENTOS DE KOTLIN/ANDROID (Usando 'libs' donde está definido)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // 2. JETPACK COMPOSE (Usando el BOM para gestionar versiones)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // 3. NAVEGACIÓN Y MVVM
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // 4. FUNCIONALIDAD DE FECHAS Y UI
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.0")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material-icons-extended")

    // 5. CÁMARA (CameraX y Permisos)
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.zxing:core:3.5.2")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")

    // ----------------------------------------------------
    // 6. REQUISITOS ADICIONALES
    // ----------------------------------------------------

    // A. API CONSUMPTION (Retrofit + JSON Converter)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // To parse JSON responses

    // B. KOTLIN REFLECTION (Essential for Mockito/Coroutines with data classes)
    // Fixes issues when Mockito tries to create instances of data classes.
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.23") // Use the version matching your Kotlin version (1.9.x)

    // 7. TESTING
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // Add Coroutines Test dependency to link with MainDispatcherRule
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

    // Testing UI (AndroidX, Compose BOM, etc.)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}