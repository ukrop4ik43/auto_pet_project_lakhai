package com.petprojject.feature_ai.screens.alternatives

import com.petprojject.domain.car.model.CarHistoryItem

interface AlternativesContract {
    data class UiState(
        val isLoading: Boolean = false,
        val listOfHistory: List<CarHistoryItem> = emptyList(),
        val response: String = "",
        val error: String? = null,
    )

    sealed interface UiAction {
        object OnBackClick : UiAction
        class OnItemClick(val item: CarHistoryItem) : UiAction
        object Init : UiAction
        object TryAgain : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
    }
}