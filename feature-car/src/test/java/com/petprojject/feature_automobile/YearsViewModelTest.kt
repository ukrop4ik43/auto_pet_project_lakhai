package com.petprojject.feature_automobile

import com.petprojject.domain.book.base.RetrofitResult
import com.petprojject.domain.car.repository.CarRepository
import com.petprojject.feature_automobile.screens.years.YearsContract
import com.petprojject.feature_automobile.screens.years.YearsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class YearsViewModelTest {

    @MockK
    lateinit var carRepository: CarRepository

    private lateinit var viewModel: YearsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = YearsViewModel(carRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init repo returns success`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        val years = mapOf("2020" to "2020", "2021" to "2021")
        coEvery { carRepository.getModelYears(manufacturer.first, model.first) } returns
                RetrofitResult.Success(years)
        viewModel.onAction(YearsContract.UiAction.Init(manufacturer, model))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(manufacturer, state.manufacturer)
        assertEquals(model, state.model)
        assertEquals(years, state.yearsMap)
        coVerify(exactly = 1) { carRepository.getModelYears("130", "1") }
    }

    @Test
    fun `Init repo returns error`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        val errorMessage = "Network Error"
        coEvery { carRepository.getModelYears(manufacturer.first, model.first) } returns
                RetrofitResult.Error(errorMessage)
        viewModel.onAction(YearsContract.UiAction.Init(manufacturer, model))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.error)
        assertEquals(emptyMap<String, String>(), state.yearsMap)
        coVerify(exactly = 1) { carRepository.getModelYears("130", "1") }
    }

    @Test
    fun `TryAgain after error returns success`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        val years = mapOf("2022" to "2022")
        coEvery { carRepository.getModelYears(any(), any()) } returnsMany listOf(
            RetrofitResult.Error("Network Error"),
            RetrofitResult.Success(years)
        )
        viewModel.onAction(YearsContract.UiAction.Init(manufacturer, model))
        advanceUntilIdle()
        viewModel.onAction(YearsContract.UiAction.TryAgain)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertNull(state.error)
        assertFalse(state.isLoading)
        assertEquals(years, state.yearsMap)
        coVerify(exactly = 2) { carRepository.getModelYears("130", "1") }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: YearsContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                received = effect
            }
        }
        viewModel.onAction(YearsContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is YearsContract.SideEffect.GoBack)
        job.cancel()
    }

    @Test
    fun `OnYearClick emits NavigateToResultScreen`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        val year = "2022" to "2022"
        viewModel.updateUiState {
            copy(manufacturer = manufacturer, model = model)
        }
        var received: YearsContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                received = effect
            }
        }
        viewModel.onAction(YearsContract.UiAction.OnYearClick(year))
        advanceUntilIdle()
        assertTrue(received is YearsContract.SideEffect.NavigateToResultScreen)
        val nav = received as YearsContract.SideEffect.NavigateToResultScreen
        assertEquals(manufacturer, nav.manufacturer)
        assertEquals(model, nav.model)
        assertEquals(year, nav.year)
        job.cancel()
    }
}