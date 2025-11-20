package com.test.feature_automobile.screens.models


interface ModelsContract {
    data class UiState(
        val manufacturer: Pair<String, String>,
        val originalModelsMap: Map<String, String>,
        val modelsMapForShow: Map<String, String>,
        val isLoading: Boolean = true,
        val error: String? = null,
        val searchText: String = ""
    )

    sealed interface UiAction {
        class Init(val manufacturer: Pair<String, String>) : UiAction
        object TryAgain : UiAction
        object OnBackClick : UiAction
        class OnModelClick(val model: Pair<String, String>) : UiAction
        class OnSearchTextChange(val newText: String) : UiAction
    }

    sealed interface SideEffect {
        class NavigateToYearsScreen(
            val manufacturer: Pair<String, String>,
            val model: Pair<String, String>
        ) : SideEffect

        object GoBack : SideEffect
    }
}