package com.petprojject.feature_automobile.screens.history

import com.petprojject.domain.car.model.CarHistoryItem

interface HistoryContract {
    data class UiState(
        val listOfHistory: List<CarHistoryItem> = emptyList(),
        val isLoading: Boolean = true,
        val error: String? = null,
    )

    sealed interface UiAction {
        object Init : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
    }
}