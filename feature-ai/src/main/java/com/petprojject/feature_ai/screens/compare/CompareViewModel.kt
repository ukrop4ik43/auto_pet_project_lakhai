package com.petprojject.feature_ai.screens.compare

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
class CompareViewModel @Inject constructor(
    private val aiGeneratorRepository: AiGeneratorRepository,
    private val carHistoryRepository: CarHistoryRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(),
    MVI<CompareContract.UiState, CompareContract.UiAction, CompareContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: CompareContract.UiAction) {
        when (uiAction) {
            CompareContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                CompareContract.SideEffect.GoBack
            )

            CompareContract.UiAction.Init -> {
                getHistoryFromDb()
            }

            is CompareContract.UiAction.OnItemClick -> {
                val mutableStateFromTheIndexes: MutableList<Int> =
                    uiState.value.listOfIndexesChosenItems.toMutableList()
                if (mutableStateFromTheIndexes.contains(uiAction.index)) {
                    mutableStateFromTheIndexes.remove(element = uiAction.index)
                } else {
                    mutableStateFromTheIndexes.add(element = uiAction.index)
                }

                updateUiState {
                    copy(listOfIndexesChosenItems = mutableStateFromTheIndexes.toList())
                }
            }

            CompareContract.UiAction.TryAgain -> {
                //TODO
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

private fun initialUiState() = CompareContract.UiState(
)