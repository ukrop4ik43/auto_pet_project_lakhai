package com.petprojject.feature_automobile.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.domain.car.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel(),
    MVI<HistoryContract.UiState, HistoryContract.UiAction, HistoryContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: HistoryContract.UiAction) {
        when (uiAction) {
            HistoryContract.UiAction.Init -> {
                getHistoryFromDb()
            }

            HistoryContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                HistoryContract.SideEffect.GoBack
            )

            HistoryContract.UiAction.TryAgain -> {
                getHistoryFromDb()
            }

            is HistoryContract.UiAction.DeleteItem -> {
                viewModelScope.launch(Dispatchers.IO) {
                    carRepository.deleteCarFromHistory(uiAction.car)
                }
            }
        }
    }

    private fun getHistoryFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { copy(isLoading = true) }
            val newList = carRepository.getAllCarsHistory()
            updateUiState { copy(isLoading = false) }
            updateUiState { copy(listOfHistory = newList) }
        }
    }
}

private fun initialUiState() =
    HistoryContract.UiState(
        isLoading = false
    )