package com.petprojject.feature_ai.screens.conclusion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ConclusionViewModel @Inject constructor(

) : ViewModel(),
    MVI<ConclusionContract.UiState, ConclusionContract.UiAction, ConclusionContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: ConclusionContract.UiAction) {
        when (uiAction) {
            ConclusionContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                ConclusionContract.SideEffect.GoBack
            )

        }
    }
}

private fun initialUiState() =
    ConclusionContract.UiState(
    )