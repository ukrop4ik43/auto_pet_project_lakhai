package com.test.feature_automobile.screens.summary


interface SummaryContract {
    data class UiState(
        val manufacturer: Pair<String, String>,
        val model: Pair<String, String>,
        val year: Pair<String, String>,
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    sealed interface UiAction {
        class Init(
            val manufacturer: Pair<String, String>,
            val model: Pair<String, String>,
            val year: Pair<String, String>
        ) :
            UiAction

        object OnBackClick : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
    }
}