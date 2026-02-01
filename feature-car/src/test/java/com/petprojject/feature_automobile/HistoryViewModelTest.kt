package com.petprojject.feature_automobile

import com.petprojject.domain.base.AppResources
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.screens.history.HistoryContract
import com.petprojject.feature_automobile.screens.history.HistoryViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
    @MockK
    lateinit var carHistoryRepository: CarHistoryRepository
    @MockK
    lateinit var appResources: AppResources

    private lateinit var viewModel: HistoryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { carRepository.isInstructionsShowed() } returns true
        coEvery { carRepository.setInstructionsShowedTrue() } just Runs
        every { appResources.getString(any()) } returns "Swipe to delete"

        viewModel = HistoryViewModel(
            carRepository = carRepository,
            carHistoryRepository = carHistoryRepository,
            appResources = appResources,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init loads history from db`() = runTest {
        val history = listOf(CarHistoryItem(id = 1, "Audi", "5", "2024"))
        coEvery { carHistoryRepository.getAllCarsHistory() } returns history
        viewModel.onAction(HistoryContract.UiAction.Init)
        advanceUntilIdle()
        assertEquals(history, viewModel.uiState.value.listOfHistory)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `OnItemClick emits NavigateToWebOpener side effect`() = runTest {
        val car = CarHistoryItem(id = 1, "BMW", "X5", "2022")
        val expectedUrl = "https://google.com/q=BMW"
        every { carRepository.generateGoogleUrl(car) } returns expectedUrl
        val sideEffects = mutableListOf<HistoryContract.SideEffect>()
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { sideEffects.add(it) }
        }
        viewModel.onAction(HistoryContract.UiAction.OnItemClick(car))
        advanceUntilIdle()
        val effect = sideEffects.firstOrNull { it is HistoryContract.SideEffect.NavigateToWebOpener }
        assertEquals(expectedUrl, (effect as HistoryContract.SideEffect.NavigateToWebOpener).url)
        job.cancel()
    }

    @Test
    fun `DeleteItem deletes item and reloads history`() = runTest {
        val car = CarHistoryItem(id = 1, "Audi", "5", "2024")
        coEvery { carHistoryRepository.deleteCarFromHistory(car) } just Runs
        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()
        viewModel.onAction(HistoryContract.UiAction.DeleteItem(car))
        advanceUntilIdle()
        coVerify(exactly = 1) { carHistoryRepository.deleteCarFromHistory(car) }
        coVerify { carHistoryRepository.getAllCarsHistory() }
        assertTrue(viewModel.uiState.value.listOfHistory.isEmpty())
    }

    @Test
    fun `OnClearClick clears and reloads history`() = runTest {
        coEvery { carHistoryRepository.deleteAllCarsFromHistory() } just Runs
        coEvery { carHistoryRepository.getAllCarsHistory() } returns emptyList()
        viewModel.onAction(HistoryContract.UiAction.OnClearClick)
        advanceUntilIdle()
        coVerify(exactly = 1) { carHistoryRepository.deleteAllCarsFromHistory() }
        coVerify { carHistoryRepository.getAllCarsHistory() }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: HistoryContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { received = it }
        }
        viewModel.onAction(HistoryContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is HistoryContract.SideEffect.GoBack)
        job.cancel()
    }
}