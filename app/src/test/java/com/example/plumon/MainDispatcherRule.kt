package com.example.plumon



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    // Define el dispatcher que se usará para las pruebas
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        // Establece el TestDispatcher como el dispatcher principal (Dispatchers.Main)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        // Restaura el dispatcher principal original
        Dispatchers.resetMain()
    }
}