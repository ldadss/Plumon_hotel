package com.example.plumon.viewmodel

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.example.plumon.repository.HotelRepository
import com.example.plumon.model.Usuario
import com.example.plumon.MainDispatcherRule // Regla para Coroutines
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest


@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    // Regla para que las corrutinas se ejecuten de forma síncrona
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AuthViewModel
    // Mockea el repositorio para simular la dependencia
    private val mockRepository = mock(HotelRepository::class.java)

    @Before // Se ejecuta antes de cada test
    fun setup() {
        viewModel = AuthViewModel(mockRepository)
    }

    // ====================================================================
    // REQUISITO 1: Login Exitoso (Verifica que el rol se asigne)
    // ====================================================================
    @Test
    fun login_withCorrectCredentials_setsUserAndRole() = runTest {
        val testUser = Usuario("Test User", "test", "Recepcionista")

        // Simular que el repositorio devuelve un usuario para este login
        `when`(mockRepository.login("test", "pass")).thenReturn(testUser)

        val result = viewModel.performLogin("test", "pass")

        // Afirmaciones:
        assertTrue("El login debe ser exitoso", result)
        assertTrue("El rol debe ser Recepcionista", viewModel.userRole.value == "Recepcionista")
    }

    // ====================================================================
    // REQUISITO 2: Login Fallido (Verifica que se devuelva false y el rol sea nulo)
    // ====================================================================
    @Test
    fun login_withIncorrectCredentials_returnsFalseAndNullUser() = runTest {
        // Simular que el repositorio devuelve null (login fallido)
        `when`(mockRepository.login("fake_user", "111")).thenReturn(null)

        val result = viewModel.performLogin("fake_user", "111")

        // Afirmaciones:
        assertFalse("El login debe fallar", result)
        assertTrue("El usuario debe ser nulo", viewModel.currentUser.value == null)
    }
}