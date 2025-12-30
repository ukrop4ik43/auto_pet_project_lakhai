package com.petprojject.feature_ai.screens.conclusion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.feature_ai.domain.AiGeneratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ConclusionViewModel @Inject constructor(
    private val aiGeneratorRepository: AiGeneratorRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : ViewModel(),
    MVI<ConclusionContract.UiState, ConclusionContract.UiAction, ConclusionContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: ConclusionContract.UiAction) {
        when (uiAction) {
            ConclusionContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                ConclusionContract.SideEffect.GoBack
            )

            ConclusionContract.UiAction.Init -> {
                viewModelScope.launch(ioDispatcher) {
                    updateUiState { copy(isLoading = true) }
                    val aiResponse = aiGeneratorRepository.getConclusionAboutUser()
                    updateUiState { copy(response = aiResponse, isLoading = false) }
                }
            }
        }
    }
}

private fun initialUiState() = ConclusionContract.UiState(
)