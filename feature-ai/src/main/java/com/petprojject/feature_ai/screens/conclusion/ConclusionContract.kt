package com.petprojject.feature_ai.screens.conclusion

interface ConclusionContract {
    data class UiState(
        val isLoading: Boolean = false,
        val response: String = ""
    )

    sealed interface UiAction {
        object OnBackClick : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
    }
}