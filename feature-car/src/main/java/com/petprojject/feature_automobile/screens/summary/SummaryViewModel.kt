package com.petprojject.feature_automobile.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.feature_automobile.domain.model.CarHistoryItem
import com.petprojject.feature_automobile.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel(),
    MVI<SummaryContract.UiState, SummaryContract.UiAction, SummaryContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: SummaryContract.UiAction) {
        when (uiAction) {
            is SummaryContract.UiAction.Init -> {
                updateUiState {
                    copy(
                        error = null,
                        manufacturer = uiAction.manufacturer,
                        year = uiAction.year,
                        model = uiAction.model
                    )
                }
            }

            SummaryContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                SummaryContract.SideEffect.GoBack
            )

            SummaryContract.UiAction.OnFinishClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    carRepository.saveCarToHistory(
                        CarHistoryItem(
                            id = null,
                            manufacturer = uiState.value.manufacturer.second,
                            year = uiState.value.year.second,
                            model = uiState.value.model.second
                        )
                    )
                }
                viewModelScope.emitSideEffect(
                    SummaryContract.SideEffect.GoToStart
                )
            }

            SummaryContract.UiAction.OnShowInGoogleClick -> viewModelScope.emitSideEffect(
                SummaryContract.SideEffect.GoToWebView(
                    carRepository.generateGoogleUrl(
                        manufacturer = uiState.value.manufacturer.second,
                        model = uiState.value.model.second,
                        year = uiState.value.year.second
                    )
                )
            )
        }
    }
}

private fun initialUiState() =
    SummaryContract.UiState(
        model = "" to "",
        manufacturer = "" to "",
        year = "" to ""
    )