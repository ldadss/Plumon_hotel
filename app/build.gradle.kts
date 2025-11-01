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

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    // Jetpack Compose Material 3 (for components like Button, TextField, etc.)
    implementation("androidx.compose.material3:material3")

    // Compose Navigation (needed if you use the 'navigation' library, but good practice)
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.0")

    // Compose ViewModel Integration (CRUCIAL for viewModel() calls)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // Use the latest stable version

    // If using Kotlin reflection for some features (like serialization)
    // implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

    // Core KTX and Activity Compose
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.1")
    // Usaremos una librería de Compose para manejar la cámara
    implementation("com.google.zxing:core:3.5.2") // Núcleo de ZXing
// Dependencia Compose para permisos y cámara (ejemplo, puedes usar otra librería si lo prefieres)
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.compose.material:material-icons-extended")
}