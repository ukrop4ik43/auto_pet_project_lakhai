package com.test.feature_automobile.screens.years

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.core.base.MVI
import com.test.core.base.mvi
import com.test.core.di.IoDispatcher
import com.test.domain.book.base.RetrofitResult
import com.test.domain.car.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class YearsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(),
    MVI<YearsContract.UiState, YearsContract.UiAction, YearsContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: YearsContract.UiAction) {
        when (uiAction) {
            is YearsContract.UiAction.Init -> {
                updateUiState {
                    copy(
                        error = null,
                        isLoading = true,
                        manufacturer = uiAction.manufacturer,
                        model = uiAction.model,
                        yearsMap = emptyMap(),
                    )
                }
                collectYears()
            }

            YearsContract.UiAction.TryAgain -> {
                updateUiState {
                    copy(
                        error = null,
                        isLoading = true
                    )
                }
                collectYears()
            }

            YearsContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                YearsContract.SideEffect.GoBack
            )

            is YearsContract.UiAction.OnYearClick -> {
                viewModelScope.emitSideEffect(
                    YearsContract.SideEffect.NavigateToResultScreen(
                        year = uiAction.year,
                        model = uiState.value.model,
                        manufacturer = uiState.value.manufacturer
                    )
                )
            }
        }
    }


    private fun collectYears(manufacturerId: String? = null, modelId: String? = null) {
        viewModelScope.launch(ioDispatcher) {
            when (val yearsMap = carRepository.getModelYears(
                manufacturer = manufacturerId ?: uiState.value.manufacturer.first,
                mainType = modelId ?: uiState.value.model.first
            )) {
                is RetrofitResult.Error -> updateUiState {
                    copy(
                        error = yearsMap.message,
                        isLoading = false,
                    )
                }

                RetrofitResult.Loading -> updateUiState { copy(isLoading = true) }
                is RetrofitResult.Success<Map<String, String>> -> updateUiState {
                    copy(
                        yearsMap = yearsMap.data,
                        isLoading = false,
                    )
                }
            }
        }
    }
}

private fun initialUiState() =
    YearsContract.UiState(
        yearsMap = emptyMap(),
        manufacturer = "" to "",
        model = "" to ""
    )