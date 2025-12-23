package com.petprojject.feature_automobile.screens.webview

interface WebOpenerContract {
    data class UiState(
        val url: String = ""
    )

    sealed interface UiAction {
        class Init(val url: String) : UiAction
    }

    sealed interface SideEffect
}