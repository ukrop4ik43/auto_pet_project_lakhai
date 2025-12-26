package com.petprojject.feature_ai.screens.compare

import com.petprojject.domain.car.model.CarHistoryItem

interface CompareContract {
    data class UiState(
        val isLoading: Boolean = false,
        val listOfHistory: List<CarHistoryItem> = emptyList(),
        val listOfIndexesChosenItems: List<Int> = emptyList(),
        val response: String = "",
        val error: String? = null,
    )

    sealed interface UiAction {
        object OnBackClick : UiAction
        class OnItemClick(val index: Int) : UiAction
        object Init : UiAction
        object TryAgain : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
    }
}