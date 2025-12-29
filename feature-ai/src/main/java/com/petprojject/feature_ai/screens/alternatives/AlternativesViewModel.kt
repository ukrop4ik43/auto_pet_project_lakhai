package com.petprojject.feature_ai.screens.alternatives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_ai.domain.AiGeneratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class AlternativesViewModel @Inject constructor(
    private val aiGeneratorRepository: AiGeneratorRepository,
    private val carHistoryRepository: CarHistoryRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(),
    MVI<AlternativesContract.UiState, AlternativesContract.UiAction, AlternativesContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: AlternativesContract.UiAction) {
        when (uiAction) {
            AlternativesContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                AlternativesContract.SideEffect.GoBack
            )

            AlternativesContract.UiAction.Init -> {
                updateUiState {
                    copy(
                        isLoading = true,
                        listOfHistory = emptyList(),
                        response = ""
                    )
                }
                getHistoryFromDb()
            }

            is AlternativesContract.UiAction.OnItemClick -> {
                updateUiState { copy(isLoading = true) }
                viewModelScope.launch(ioDispatcher) {
                    val comparison = aiGeneratorRepository.getCarAlternatives(
                        uiAction.item
                    )
                    updateUiState {
                        copy(
                            response = comparison, isLoading = false
                        )
                    }
                }
            }

            AlternativesContract.UiAction.TryAgain -> {
                updateUiState {
                    copy(
                        isLoading = true,
                        listOfHistory = emptyList(),
                        response = ""
                    )
                }
                getHistoryFromDb()
            }
        }
    }

    private fun getHistoryFromDb() {
        viewModelScope.launch(ioDispatcher) {
            updateUiState { copy(isLoading = true) }
            val newList = carHistoryRepository.getAllCarsHistory()
            updateUiState { copy(listOfHistory = newList, isLoading = false) }
        }
    }

}

private fun initialUiState() = AlternativesContract.UiState()

