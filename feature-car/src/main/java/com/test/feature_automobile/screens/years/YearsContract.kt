package com.test.feature_automobile.screens.years


interface YearsContract {
    data class UiState(
        val manufacturer: Pair<String, String>,
        val model: Pair<String, String>,
        val yearsMap: Map<String, String>,
        val isLoading: Boolean = true,
        val error: String? = null,
    )

    sealed interface UiAction {
        class Init(val manufacturer: Pair<String, String>,val model: Pair<String, String>) : UiAction
        object TryAgain : UiAction
        object OnBackClick : UiAction
        class OnYearClick(val year: Pair<String, String>) : UiAction
    }

    sealed interface SideEffect {
        class NavigateToResultScreen(
            val manufacturer: Pair<String, String>,
            val model: Pair<String, String>,
            val year: Pair<String, String>
        ) : SideEffect

        object GoBack : SideEffect
    }
}