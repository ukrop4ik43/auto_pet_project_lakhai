package com.petprojject.feature_ai.screens.ai_chat

interface AiMenuContract {
    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed interface UiAction {
        object OnBackClick : UiAction
        object OnShowChoiceAlternativesScreen : UiAction
        object OnGenerateConclusionClick : UiAction
        object OnCompareChoicesClick : UiAction
    }

    sealed interface SideEffect {
        object GoToChoiceAlternativesScreen : SideEffect
        object GoToGenerateConclusionClick : SideEffect
        object GoToCompareChoicesClick : SideEffect
        object GoBack : SideEffect

    }
}