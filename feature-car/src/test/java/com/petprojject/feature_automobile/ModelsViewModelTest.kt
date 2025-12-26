package com.petprojject.feature_automobile

import com.petprojject.core.base.retrofit.RetrofitResult
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.screens.models.ModelsContract
import com.petprojject.feature_automobile.screens.models.ModelsViewModel
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
class ModelsViewModelTest {

    @MockK
    lateinit var carRepository: CarRepository

    private lateinit var viewModel: ModelsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ModelsViewModel(carRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `Init repo returns success`() = runTest {
        val manufacturer = "130" to "BMW"
        val models = mapOf("1" to "X5", "2" to "M3")
        coEvery { carRepository.getModels(manufacturer.first) } returns
                RetrofitResult.Success(models)
        viewModel.onAction(ModelsContract.UiAction.Init(manufacturer))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(manufacturer, state.manufacturer)
        assertEquals(models, state.originalModelsMap)
        assertEquals(models, state.modelsMapForShow)
        coVerify(exactly = 1) { carRepository.getModels("130") }
    }


    @Test
    fun `Init repo returns error`() = runTest {
        val manufacturer = "130" to "BMW"
        val errorMessage = "Network Error"
        coEvery { carRepository.getModels(manufacturer.first) } returns
                RetrofitResult.Error(errorMessage)
        viewModel.onAction(ModelsContract.UiAction.Init(manufacturer))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.error)
        assertEquals(emptyMap<String, String>(), state.modelsMapForShow)
        coVerify(exactly = 1) { carRepository.getModels("130") }
    }


    @Test
    fun `TryAgain after error returns success`() = runTest {
        val manufacturer = "130" to "BMW"
        val models = mapOf("1" to "X5")
        coEvery { carRepository.getModels(any()) } returnsMany listOf(
            RetrofitResult.Error("Network Error"),
            RetrofitResult.Success(models)
        )
        viewModel.onAction(ModelsContract.UiAction.Init(manufacturer))
        advanceUntilIdle()
        viewModel.onAction(ModelsContract.UiAction.TryAgain)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertNull(state.error)
        assertFalse(state.isLoading)
        assertEquals(models, state.originalModelsMap)
        assertEquals(models, state.modelsMapForShow)
        coVerify(exactly = 2) { carRepository.getModels("130") }
    }


    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: ModelsContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                received = effect
            }
        }
        viewModel.onAction(ModelsContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is ModelsContract.SideEffect.GoBack)
        job.cancel()
    }


    @Test
    fun `OnModelClick emits NavigateToYearsScreen`() = runTest {
        val manufacturer = "130" to "BMW"
        val model = "1" to "X5"
        viewModel.updateUiState {
            copy(manufacturer = manufacturer)
        }
        var received: ModelsContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect { effect ->
                received = effect
            }
        }
        viewModel.onAction(ModelsContract.UiAction.OnModelClick(model))
        advanceUntilIdle()
        assertTrue(received is ModelsContract.SideEffect.NavigateToYearsScreen)
        val nav = received as ModelsContract.SideEffect.NavigateToYearsScreen
        assertEquals(manufacturer, nav.manufacturer)
        assertEquals(model, nav.model)
        job.cancel()
    }
}