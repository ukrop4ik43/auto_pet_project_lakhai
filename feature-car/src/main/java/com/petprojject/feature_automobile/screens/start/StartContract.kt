package com.petprojject.feature_automobile.screens.start

interface StartContract {
    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed interface UiAction {
        object OnChooseYourCarClick : UiAction
        object OnHistoryClick : UiAction
        object OnAiClick : UiAction
    }

    sealed interface SideEffect {
        object GoToHistory : SideEffect
        object GoToChooseYourCar : SideEffect
        object GoToAi:SideEffect
    }
}