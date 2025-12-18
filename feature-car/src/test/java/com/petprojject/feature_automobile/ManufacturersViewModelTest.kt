package com.petprojject.feature_automobile

import com.petprojject.domain.base.RetrofitResult
import com.petprojject.domain.car.repository.CarRepository
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersViewModel
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
class ManufacturersViewModelTest {

    @MockK
    lateinit var carRepository: CarRepository

    private lateinit var viewModel: ManufacturersViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ManufacturersViewModel(carRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init repo returns success`() = runTest {
        val manufacturers = mapOf("130" to "BMW", "160" to "Chevrolet")
        coEvery { carRepository.getManufacturers(page = 0) } returns
                RetrofitResult.Success(manufacturers)
        viewModel.onAction(ManufacturersContract.UiAction.Init)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(manufacturers, state.manufacturersMap)
        assertNull(state.error)
        assertEquals(1, state.page)
        coVerify(exactly = 1) { carRepository.getManufacturers(0) }
    }

    @Test
    fun `Init repo returns error`() = runTest {
        val errorMessage = "Network Error"
        coEvery { carRepository.getManufacturers(page = 0) } returns
                RetrofitResult.Error(errorMessage)
        viewModel.onAction(ManufacturersContract.UiAction.Init)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(emptyMap<String, String>(), state.manufacturersMap)
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.error)
        coVerify(exactly = 1) { carRepository.getManufacturers(0) }
    }

    @Test
    fun `TryAgain error then success`() = runTest {
        val manufacturers = mapOf("1" to "Mazda")
        coEvery { carRepository.getManufacturers(any()) } returnsMany listOf(
            RetrofitResult.Error("Network Error"),
            RetrofitResult.Success(manufacturers)
        )
        viewModel.onAction(ManufacturersContract.UiAction.Init)
        advanceUntilIdle()
        viewModel.onAction(ManufacturersContract.UiAction.TryAgain)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(manufacturers, state.manufacturersMap)
        assertNull(state.error)
        assertFalse(state.isLoading)
        assertEquals(1, state.page)
        coVerify(exactly = 2) { carRepository.getManufacturers(0) }
    }

    @Test
    fun `OnBottomReached loads next page when not loading`() = runTest {
        val page0 = mapOf("1" to "BMW")
        val page1 = mapOf("2" to "Audi")
        coEvery { carRepository.getManufacturers(0) } returns
                RetrofitResult.Success(page0)
        coEvery { carRepository.getManufacturers(1) } returns
                RetrofitResult.Success(page1)
        viewModel.onAction(ManufacturersContract.UiAction.Init)
        advanceUntilIdle()
        viewModel.onAction(ManufacturersContract.UiAction.OnBottomReached)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(
            mapOf("1" to "BMW", "2" to "Audi"),
            state.manufacturersMap
        )
        assertEquals(2, state.page)
        coVerify(exactly = 1) { carRepository.getManufacturers(0) }
        coVerify(exactly = 1) { carRepository.getManufacturers(1) }
    }

    @Test
    fun `OnBottomReached does nothing when isLoading true`() = runTest {
        val result = RetrofitResult.Loading
        coEvery { carRepository.getManufacturers(0) } returns result
        viewModel.onAction(ManufacturersContract.UiAction.Init)
        advanceUntilIdle()
        viewModel.onAction(ManufacturersContract.UiAction.OnBottomReached)
        advanceUntilIdle()
        coVerify(exactly = 1) { carRepository.getManufacturers(0) }
    }

    @Test
    fun `OnManufacturerClick emits NavigateToModelsScreen`() = runTest {
        val pair = "130" to "BMW"
        var receivedEffect: ManufacturersContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                receivedEffect = effect
            }
        }
        viewModel.onAction(ManufacturersContract.UiAction.OnManufacturerClick(pair))
        advanceUntilIdle()
        assertTrue(receivedEffect is ManufacturersContract.SideEffect.NavigateToModelsScreen)
        assertEquals(pair, (receivedEffect as ManufacturersContract.SideEffect.NavigateToModelsScreen).manufacturer)
        job.cancel()
    }
}