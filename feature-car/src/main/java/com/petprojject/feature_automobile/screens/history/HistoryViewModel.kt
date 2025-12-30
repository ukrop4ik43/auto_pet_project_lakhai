package com.petprojject.feature_automobile.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.domain.base.AppResources
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.R
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val carHistoryRepository: CarHistoryRepository,
    private val appResources: AppResources,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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
                viewModelScope.launch(ioDispatcher) {
                    runBlocking {
                        carHistoryRepository.deleteCarFromHistory(uiAction.car)
                    }
                    getHistoryFromDb()
                }
            }

            HistoryContract.UiAction.OnClearClick -> {
                viewModelScope.launch(ioDispatcher) {
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
        viewModelScope.launch(ioDispatcher) {
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