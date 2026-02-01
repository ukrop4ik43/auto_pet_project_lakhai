package com.petprojject.feature_ai

import com.petprojject.feature_ai.domain.AiGeneratorRepository
import com.petprojject.feature_ai.screens.conclusion.ConclusionContract
import com.petprojject.feature_ai.screens.conclusion.ConclusionViewModel
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
class ConclusionViewModelTest {

    @MockK
    lateinit var aiGeneratorRepository: AiGeneratorRepository

    private lateinit var viewModel: ConclusionViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ConclusionViewModel(
            aiGeneratorRepository = aiGeneratorRepository,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Init loads conclusion from ai repository`() = runTest {
        val expectedResponse = "You are a car enthusiast who loves German engineering."
        coEvery { aiGeneratorRepository.getConclusionAboutUser() } returns expectedResponse
        viewModel.onAction(ConclusionContract.UiAction.Init)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(expectedResponse, state.response)
        coVerify(exactly = 1) { aiGeneratorRepository.getConclusionAboutUser() }
    }

    @Test
    fun `OnBackClick emits GoBack side effect`() = runTest {
        var received: ConclusionContract.SideEffect? = null
        val job = launch(testDispatcher) {
            viewModel.sideEffect.collect {
                received = it
            }
        }
        viewModel.onAction(ConclusionContract.UiAction.OnBackClick)
        advanceUntilIdle()
        assertTrue(received is ConclusionContract.SideEffect.GoBack)
        job.cancel()
    }
}