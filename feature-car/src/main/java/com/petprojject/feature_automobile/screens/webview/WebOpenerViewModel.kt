package com.petprojject.feature_automobile.screens.webview

import androidx.lifecycle.ViewModel
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class WebOpenerViewModel @Inject constructor() : ViewModel(),
    MVI<WebOpenerContract.UiState, WebOpenerContract.UiAction, WebOpenerContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: WebOpenerContract.UiAction) {
        when (uiAction) {
            is WebOpenerContract.UiAction.Init -> {
                updateUiState {
                    copy(url = uiAction.url)
                }
            }
        }
    }
}

private fun initialUiState() = WebOpenerContract.UiState()