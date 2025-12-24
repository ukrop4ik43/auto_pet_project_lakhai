package com.test.feature_ai.screens.ai_chat

interface AiChatContract {
    data class UiState(
        val query: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    sealed interface UiAction {
        object OnBackClick : UiAction
        object OnFinishClick : UiAction
        object OnShowInGoogleClick : UiAction
    }

    sealed interface SideEffect {
        object GoBack : SideEffect
        object GoToStart : SideEffect
        class GoToWebView(val url: String) : SideEffect
    }
}