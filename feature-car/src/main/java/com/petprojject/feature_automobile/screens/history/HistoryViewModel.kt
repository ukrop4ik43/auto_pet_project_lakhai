package com.petprojject.feature_automobile.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.domain.car.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val carRepository: CarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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
                viewModelScope.launch(ioDispatcher) {
                    carRepository.deleteCarFromHistory(uiAction.car)
                    getHistoryFromDb()
                }
            }

            HistoryContract.UiAction.OnClearClick -> {
                viewModelScope.launch(ioDispatcher) {
                    carRepository.deleteAllCarsFromHistory()
                    getHistoryFromDb()
                }
            }
        }
    }

    private fun getHistoryFromDb() {
        viewModelScope.launch(ioDispatcher) {
            updateUiState { copy(isLoading = true) }
            val newList = carRepository.getAllCarsHistory()
            updateUiState { copy(listOfHistory = newList, isLoading = false) }
        }
    }
}

private fun initialUiState() =
    HistoryContract.UiState(
        isLoading = false
    )