package com.petprojject.feature_automobile


import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.screens.summary.SummaryContract
import com.petprojject.feature_automobile.screens.summary.SummaryViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
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
class SummaryViewModelTest {

    private lateinit var viewModel: SummaryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var carRepository: CarRepository

    @MockK
    lateinit var carHistoryRepository: CarHistoryRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { carHistoryRepository.saveCarToHistory(any()) } returns Unit

        viewModel = SummaryViewModel(
            carRepository = carRepository,
            carHistoryRepository = carHistoryRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init sets state correctly`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        val year = "2022" to "2022"
        viewModel.onAction(SummaryContract.UiAction.Init(manufacturer, model, year))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(manufacturer, state.manufacturer)
        assertEquals(model, state.model)
        assertEquals(year, state.year)
        assertNull(state.error)
    }

    @Test
    fun `OnFinishClick saves car to history and navigates to start`() = runTest {
        val manufacturer = "1" to "Audi"
        val model = "2" to "A4"
        val year = "2021" to "2021"
        viewModel.onAction(SummaryContract.UiAction.Init(manufacturer, model, year))
        val results = mutableListOf<SummaryContract.SideEffect>()
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { results.add(it) }
        }
        viewModel.onAction(SummaryContract.UiAction.OnFinishClick)
        advanceUntilIdle()
        assertTrue(results.any { it is SummaryContract.SideEffect.GoToStart })
        coVerify {
            carHistoryRepository.saveCarToHistory(match {
                it.manufacturer == "Audi" && it.model == "A4" && it.year == "2021"
            })
        }
        job.cancel()
    }

    @Test
    fun `OnShowInGoogleClick emits GoToWebView with correctly formatted URL`() = runTest {
        val expectedUrl = "https://google.com/q=BMW+X5+2022"
        every {
            carRepository.generateGoogleUrl("BMW", "X5", "2022")
        } returns expectedUrl
        viewModel.onAction(SummaryContract.UiAction.Init("1" to "BMW", "2" to "X5", "3" to "2022"))
        val results = mutableListOf<SummaryContract.SideEffect>()
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { results.add(it) }
        }
        viewModel.onAction(SummaryContract.UiAction.OnShowInGoogleClick)
        advanceUntilIdle()
        val effect = results.first() as SummaryContract.SideEffect.GoToWebView
        assertEquals(expectedUrl, effect.url)
        job.cancel()
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        val results = mutableListOf<SummaryContract.SideEffect>()
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { results.add(it) }
        }
        viewModel.onAction(SummaryContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(results.first() is SummaryContract.SideEffect.GoBack)
        job.cancel()
    }
}