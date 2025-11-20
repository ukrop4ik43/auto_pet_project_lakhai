package com.test.feature_automobile.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.core.base.MVI
import com.test.core.base.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class SummaryViewModel @Inject constructor() : ViewModel(),
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
        }
    }
}

private fun initialUiState() =
    SummaryContract.UiState(
        model = "" to "",
        manufacturer = "" to "",
        year = "" to ""
    )