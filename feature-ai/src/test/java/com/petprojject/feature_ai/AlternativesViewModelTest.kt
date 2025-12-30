package com.petprojject.feature_ai

import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_ai.domain.AiGeneratorRepository
import com.petprojject.feature_ai.screens.alternatives.AlternativesContract
import com.petprojject.feature_ai.screens.alternatives.AlternativesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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
class AlternativesViewModelTest {

    @MockK
    lateinit var aiGeneratorRepository: AiGeneratorRepository
    @MockK
    lateinit var carHistoryRepository: CarHistoryRepository

    private lateinit var viewModel: AlternativesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()

        viewModel = AlternativesViewModel(
            aiGeneratorRepository = aiGeneratorRepository,
            carHistoryRepository = carHistoryRepository,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init resets state and loads history from db`() = runTest {
        val history = listOf(
            CarHistoryItem(id = 1, "Toyota", "Corolla", "2022"),
            CarHistoryItem(id = 2, "Honda", "Civic", "2023")
        )
        coEvery { carHistoryRepository.getAllCarsHistory() } returns history
        viewModel.onAction(AlternativesContract.UiAction.Init)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(history, state.listOfHistory)
        assertEquals("", state.response)
        assertFalse(state.isLoading)
        coVerify(exactly = 1) { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnItemClick fetches alternatives from ai repository`() = runTest {
        val selectedCar = CarHistoryItem(id = 1, "Toyota", "Corolla", "2022")
        val mockAlternatives = "1. Honda Civic\n2. Mazda 3\n3. Hyundai Elantra"
        coEvery { aiGeneratorRepository.getCarAlternatives(selectedCar) } returns mockAlternatives
        viewModel.onAction(AlternativesContract.UiAction.OnItemClick(selectedCar))
        assertTrue(viewModel.uiState.value.isLoading)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(mockAlternatives, state.response)
        assertFalse(state.isLoading)
        coVerify(exactly = 1) { aiGeneratorRepository.getCarAlternatives(selectedCar) }
    }

    @Test
    fun `TryAgain resets state and reloads history`() = runTest {
        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()
        viewModel.onAction(AlternativesContract.UiAction.TryAgain)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals("", state.response)
        coVerify(exactly = 1) { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: AlternativesContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { received = it }
        }
        viewModel.onAction(AlternativesContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is AlternativesContract.SideEffect.GoBack)
        job.cancel()
    }
}