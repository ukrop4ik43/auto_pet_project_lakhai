package com.petprojject.feature_automobile.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.domain.base.AppResources
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_automobile.R
import com.petprojject.feature_automobile.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val carHistoryRepository: CarHistoryRepository,
    private val appResources: AppResources,
) : ViewModel(),
    MVI<HistoryContract.UiState, HistoryContract.UiAction, HistoryContract.SideEffect> by mvi(
        initialUiState()
    ) {

    init {
        viewModelScope.launch {
            if (!carRepository.isInstructionsShowed()) {
                viewModelScope.emitSideEffect(
                    HistoryContract.SideEffect.ShowToast(appResources.getString(R.string.you_can_swipe_to_delete_item))
                )
                carRepository.setInstructionsShowedTrue()
            }
        }
    }

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
                viewModelScope.launch{
                    runBlocking {
                        carHistoryRepository.deleteCarFromHistory(uiAction.car)
                    }
                    getHistoryFromDb()
                }
            }

            HistoryContract.UiAction.OnClearClick -> {
                viewModelScope.launch {
                    carHistoryRepository.deleteAllCarsFromHistory()
                    getHistoryFromDb()
                }
            }

            is HistoryContract.UiAction.OnItemClick -> viewModelScope.emitSideEffect(
                HistoryContract.SideEffect.NavigateToWebOpener(
                    carRepository.generateGoogleUrl(
                        uiAction.car
                    )
                )
            )
        }
    }

    private fun getHistoryFromDb() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            val newList = carHistoryRepository.getAllCarsHistory()
            updateUiState { copy(listOfHistory = newList, isLoading = false) }
        }
    }
}

private fun initialUiState() =
    HistoryContract.UiState(
        isLoading = false
    )