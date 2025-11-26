package com.petprojject.feature_automobile.screens.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.domain.book.base.RetrofitResult
import com.petprojject.domain.car.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class ModelsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(),
    MVI<ModelsContract.UiState, ModelsContract.UiAction, ModelsContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: ModelsContract.UiAction) {
        when (uiAction) {
            is ModelsContract.UiAction.Init -> {
                updateUiState {
                    copy(
                        error = null,
                        isLoading = true,
                        manufacturer = uiAction.manufacturer,
                        searchText = "",
                        modelsMapForShow = emptyMap(),
                        originalModelsMap = emptyMap()
                    )
                }
                collectModels(uiAction.manufacturer.first)
            }

            ModelsContract.UiAction.TryAgain -> {
                updateUiState {
                    copy(
                        error = null,
                        isLoading = true
                    )
                }
                collectModels()
            }

            ModelsContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                ModelsContract.SideEffect.GoBack
            )

            is ModelsContract.UiAction.OnModelClick -> viewModelScope.emitSideEffect(
                ModelsContract.SideEffect.NavigateToYearsScreen(
                    manufacturer = uiState.value.manufacturer,
                    model = uiAction.model
                )
            )

            is ModelsContract.UiAction.OnSearchTextChange -> {
                updateUiState {
                    copy(
                        searchText = uiAction.newText,
                        modelsMapForShow = carRepository.filterModels(
                            uiState.value.originalModelsMap,
                            uiAction.newText
                        )
                    )
                }
            }
        }
    }

    private fun collectModels(manufacturerId: String? = null) {
        viewModelScope.launch(ioDispatcher) {
            when (val carsMap = carRepository.getModels(
                manufacturer = manufacturerId ?: uiState.value.manufacturer.first
            )) {
                is RetrofitResult.Error -> updateUiState {
                    copy(
                        error = carsMap.message,
                        isLoading = false,
                    )
                }

                RetrofitResult.Loading -> updateUiState { copy(isLoading = true) }
                is RetrofitResult.Success<Map<String, String>> -> updateUiState {
                    copy(
                        modelsMapForShow = carsMap.data,
                        originalModelsMap = carsMap.data,
                        isLoading = false,
                    )
                }
            }
        }
    }
}

private fun initialUiState() =
    ModelsContract.UiState(
        originalModelsMap = emptyMap(),
        manufacturer = "" to "",
        modelsMapForShow = emptyMap()
    )