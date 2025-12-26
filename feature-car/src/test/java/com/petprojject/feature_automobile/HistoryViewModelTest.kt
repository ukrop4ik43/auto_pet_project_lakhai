package com.petprojject.feature_automobile

import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.screens.history.HistoryContract
import com.petprojject.feature_automobile.screens.history.HistoryViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @MockK
    lateinit var carRepository: CarRepository

    private lateinit var viewModel: HistoryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HistoryViewModel(carRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init loads history from db`() = runTest {
        val history = listOf(
            CarHistoryItem(id = 1, manufacturer = "Audi", model = "5", year = "2024"),
            CarHistoryItem(id = 2, manufacturer = "BMW", model = "6", year = "2023")
        )

        coEvery { carRepository.getAllCarsHistory() } returns history

        viewModel.onAction(HistoryContract.UiAction.Init)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(history, state.listOfHistory)

        coVerify(exactly = 1) { carRepository.getAllCarsHistory() }
    }

    @Test
    fun `TryAgain reloads history`() = runTest {
        val history =
            listOf(CarHistoryItem(id = 1, manufacturer = "Audi", model = "5", year = "2024"))

        coEvery { carRepository.getAllCarsHistory() } returns history

        viewModel.onAction(HistoryContract.UiAction.TryAgain)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(history, state.listOfHistory)

        coVerify(exactly = 1) { carRepository.getAllCarsHistory() }
    }

    @Test
    fun `DeleteItem deletes item and reloads history`() = runTest {
        val car = CarHistoryItem(id = 1, manufacturer = "Audi", model = "5", year = "2024")
        val historyAfterDelete = emptyList<CarHistoryItem>()

        coEvery { carRepository.deleteCarFromHistory(car) } just Runs
        coEvery { carRepository.getAllCarsHistory() } returns historyAfterDelete

        viewModel.onAction(HistoryContract.UiAction.DeleteItem(car))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(historyAfterDelete, state.listOfHistory)

        coVerify(exactly = 1) { carRepository.deleteCarFromHistory(car) }
        coVerify(exactly = 1) { carRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnClearClick clears history and reloads`() = runTest {
        coEvery { carRepository.deleteAllCarsFromHistory() } just Runs
        coEvery { carRepository.getAllCarsHistory() } returns emptyList()

        viewModel.onAction(HistoryContract.UiAction.OnClearClick)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.listOfHistory.isEmpty())

        coVerify(exactly = 1) { carRepository.deleteAllCarsFromHistory() }
        coVerify(exactly = 1) { carRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: HistoryContract.SideEffect? = null

        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect {
                received = it
            }
        }

        viewModel.onAction(HistoryContract.UiAction.OnBackClick)
        advanceUntilIdle()

        assertTrue(received is HistoryContract.SideEffect.GoBack)
        job.cancel()
    }
}
