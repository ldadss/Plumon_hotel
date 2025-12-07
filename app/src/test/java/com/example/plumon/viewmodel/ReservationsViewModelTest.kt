package com.example.plumon.viewmodel

import com.example.plumon.MainDispatcherRule
import com.example.plumon.model.Reserva
import com.example.plumon.repository.FakeHotelRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ReservationsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ReservationsViewModel
    private lateinit var fakeRepository: FakeHotelRepository

    @Before
    fun setup() {
        // Use the Fake repository instead of a mock
        fakeRepository = FakeHotelRepository()
        viewModel = ReservationsViewModel(fakeRepository)

        // Pre-populate the fake repository with a consistent state for each test
        val initialReservas = listOf(
            Reserva("R001", "Ariel", "101", LocalDate.parse("2025-12-01"), LocalDate.parse("2025-12-05"), "ocupada"),
            Reserva("R002", "Bella", "102", LocalDate.parse("2025-12-02"), LocalDate.parse("2025-12-06"), "pendiente")
        )
        fakeRepository.addInitialReservas(initialReservas)
    }

    @Test
    fun createReserva_addsNewReservaToList() = runTest {
        val initialSize = viewModel.reservas.size
        val newGuestName = "Cenicienta"

        viewModel.createReserva(newGuestName, "301", "2025-12-10", "2025-12-12")

        val currentReservas = viewModel.reservas
        assertEquals("The list size should increase by one", initialSize + 1, currentReservas.size)
        val addedReserva = currentReservas.last()
        assertEquals("The new reserva should have the correct guest name", newGuestName, addedReserva.huesped)
        assertEquals("The new reserva should have status 'pendiente'", "pendiente", addedReserva.estado)
    }

    @Test
    fun markAsCheckedIn_updatesReservationStatusToOcupada() = runTest {
        val reservaId = "R002"
        viewModel.markAsCheckedIn(reservaId)

        val updatedReserva = viewModel.reservas.find { it.id == reservaId }
        assertNotNull("The reservation should still exist", updatedReserva)
        assertEquals("The reservation status should be 'ocupada'", "ocupada", updatedReserva?.estado)
    }

    @Test
    fun getNextPendingReserva_returnsFirstPending() = runTest {
        // Add another pending reservation to ensure it gets the *first* one
        val futureDate = LocalDate.parse("2026-01-01")
        fakeRepository.addReserva(
            Reserva("R004", "Otro Huesped", "401", futureDate, futureDate.plusDays(2), "pendiente")
        )

        val nextReserva = viewModel.getNextPendingReserva()

        assertNotNull("A pending reservation should be found", nextReserva)
        assertEquals("The ID of the next pending reservation should be R002", "R002", nextReserva?.id)
    }
}