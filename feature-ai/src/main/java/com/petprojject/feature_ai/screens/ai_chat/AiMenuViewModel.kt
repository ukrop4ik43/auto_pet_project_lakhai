package com.petprojject.feature_ai.screens.ai_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AiMenuViewModel @Inject constructor(
) : ViewModel(),
    MVI<AiMenuContract.UiState, AiMenuContract.UiAction, AiMenuContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: AiMenuContract.UiAction) {
        when (uiAction) {
            AiMenuContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                AiMenuContract.SideEffect.GoBack
            )

            AiMenuContract.UiAction.OnCompareChoicesClick -> viewModelScope.emitSideEffect(
                AiMenuContract.SideEffect.GoToCompareChoicesClick
            )

            AiMenuContract.UiAction.OnGenerateConclusionClick -> viewModelScope.emitSideEffect(
                AiMenuContract.SideEffect.GoToGenerateConclusionClick
            )

            AiMenuContract.UiAction.OnShowChoiceAlternativesScreen -> viewModelScope.emitSideEffect(
                AiMenuContract.SideEffect.GoToChoiceAlternativesScreen
            )
        }
    }
}

private fun initialUiState() =
    AiMenuContract.UiState(
        isLoading = false
    )