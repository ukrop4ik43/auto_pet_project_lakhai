package com.petprojject.feature_automobile.screens.manufacturers


interface ManufacturersContract {
    data class UiState(
        val manufacturersMap: Map<String, String>,
        val isLoading: Boolean = true,
        val error: String? = null,
        val page: Int = 0,
        val totalPages: Int = 0
    )

    sealed interface UiAction {
        object Init : UiAction
        object TryAgain : UiAction
        object OnBottomReached : UiAction
        class OnManufacturerClick(val manufacturer: Pair<String, String>) : UiAction
    }

    sealed interface SideEffect {
        class NavigateToModelsScreen(val manufacturer: Pair<String, String>) : SideEffect
    }
}