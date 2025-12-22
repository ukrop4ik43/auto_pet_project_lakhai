package com.petprojject.feature_automobile.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
) : ViewModel(),
    MVI<StartContract.UiState, StartContract.UiAction, StartContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: StartContract.UiAction) {
        when (uiAction) {
            StartContract.UiAction.OnChooseYourCarClick -> viewModelScope.emitSideEffect(
                StartContract.SideEffect.GoToChooseYourCar
            )

            StartContract.UiAction.OnHistoryClick -> viewModelScope.emitSideEffect(
                StartContract.SideEffect.GoToHistory
            )
        }
    }
}

private fun initialUiState() =
    StartContract.UiState(
        isLoading = false
    )