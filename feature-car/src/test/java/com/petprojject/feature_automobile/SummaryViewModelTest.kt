package com.petprojject.feature_automobile


import com.petprojject.domain.car.repository.CarRepository
import com.petprojject.feature_automobile.screens.summary.SummaryContract
import com.petprojject.feature_automobile.screens.summary.SummaryViewModel
import io.mockk.MockKAnnotations
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SummaryViewModel(carRepository)
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
        assertFalse(state.isLoading)
        assertEquals(manufacturer, state.manufacturer)
        assertEquals(model, state.model)
        assertEquals(year, state.year)
        assertNull(state.error)
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: SummaryContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                received = effect
            }
        }
        viewModel.onAction(SummaryContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is SummaryContract.SideEffect.GoBack)
        job.cancel()
    }
}