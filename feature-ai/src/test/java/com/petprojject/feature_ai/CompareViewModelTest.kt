package com.petprojject.feature_ai

import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_ai.domain.AiGeneratorRepository
import com.petprojject.feature_ai.screens.compare.CompareContract
import com.petprojject.feature_ai.screens.compare.CompareViewModel
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
class CompareViewModelTest {

    @MockK
    lateinit var aiGeneratorRepository: AiGeneratorRepository
    @MockK
    lateinit var carHistoryRepository: CarHistoryRepository

    private lateinit var viewModel: CompareViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()

        viewModel = CompareViewModel(
            aiGeneratorRepository = aiGeneratorRepository,
            carHistoryRepository = carHistoryRepository,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init resets state and fetches history from database`() = runTest {
        val history = listOf(
            CarHistoryItem(id = 1, "Tesla", "Model 3", "2024"),
            CarHistoryItem(id = 2, "Ford", "Mustang", "1967")
        )
        coEvery { carHistoryRepository.getAllCarsHistory() } returns history
        viewModel.onAction(CompareContract.UiAction.Init)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(history, state.listOfHistory)
        assertTrue(state.listOfIndexesChosenItems.isEmpty())
        assertEquals("", state.response)
        assertFalse(state.isLoading)
        coVerify(exactly = 1) { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnItemClick toggles item selection correctly`() = runTest {
        val history = listOf(CarHistoryItem(id = 1, "Audi", "A4", "2022"))
        coEvery { carHistoryRepository.getAllCarsHistory() } returns history
        viewModel.onAction(CompareContract.UiAction.Init)
        advanceUntilIdle()
        viewModel.onAction(CompareContract.UiAction.OnItemClick(index = 0))
        assertEquals(listOf(0), viewModel.uiState.value.listOfIndexesChosenItems)
        viewModel.onAction(CompareContract.UiAction.OnItemClick(index = 0))
        assertTrue(viewModel.uiState.value.listOfIndexesChosenItems.isEmpty())
        coVerify(exactly = 1) { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnItemClick triggers AI comparison when two items are selected`() = runTest {
        val car1 = CarHistoryItem(id = 1, "BMW", "M3", "2023")
        val car2 = CarHistoryItem(id = 2, "Mercedes", "C63", "2023")
        val history = listOf(car1, car2)
        val aiComparisonResult = "The BMW is more agile, while the Mercedes is more comfortable."
        coEvery { carHistoryRepository.getAllCarsHistory() } returns history
        coEvery { aiGeneratorRepository.compareCars(car1, car2) } returns aiComparisonResult
        viewModel.onAction(CompareContract.UiAction.Init)
        advanceUntilIdle()
        viewModel.onAction(CompareContract.UiAction.OnItemClick(0))
        viewModel.onAction(CompareContract.UiAction.OnItemClick(1))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(aiComparisonResult, state.response)
        assertFalse(state.isLoading)
        coVerify(exactly = 1) { aiGeneratorRepository.compareCars(car1, car2) }
    }

    @Test
    fun `TryAgain clears previous results and reloads history`() = runTest {
        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()
        viewModel.onAction(CompareContract.UiAction.TryAgain)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertTrue(state.listOfIndexesChosenItems.isEmpty())
        assertEquals("", state.response)
        coVerify(exactly = 1) { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: CompareContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { received = it }
        }
        viewModel.onAction(CompareContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is CompareContract.SideEffect.GoBack)
        job.cancel()
    }
}