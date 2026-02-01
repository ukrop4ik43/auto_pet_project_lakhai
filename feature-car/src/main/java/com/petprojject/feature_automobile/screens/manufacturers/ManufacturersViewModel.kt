package com.petprojject.feature_automobile.screens.manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.base.retrofit.RetrofitResult
import com.petprojject.feature_automobile.domain.model.ManufacturersData
import com.petprojject.feature_automobile.domain.repository.CarRepository
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract.SideEffect.NavigateBack
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract.SideEffect.NavigateToModelsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ManufacturersViewModel @Inject constructor(
    private val carRepository: CarRepository,
) : ViewModel(),
    MVI<ManufacturersContract.UiState, ManufacturersContract.UiAction, ManufacturersContract.SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(uiAction: ManufacturersContract.UiAction) {
        when (uiAction) {
            ManufacturersContract.UiAction.Init -> {
                collectManufacturers()
            }

            is ManufacturersContract.UiAction.OnManufacturerClick -> viewModelScope.emitSideEffect(
                NavigateToModelsScreen(manufacturer = uiAction.manufacturer)
            )

            ManufacturersContract.UiAction.TryAgain -> {
                updateUiState {
                    copy(
                        error = null,
                        isLoading = true
                    )
                }
                collectManufacturers()
            }

            ManufacturersContract.UiAction.OnBottomReached -> {
                if (uiState.value.isLoading || uiState.value.totalPages + 1 == uiState.value.page) return
                collectManufacturers()
            }

            ManufacturersContract.UiAction.OnBackClick -> viewModelScope.emitSideEffect(
                NavigateBack
            )
        }
    }


    private fun collectManufacturers() {
        viewModelScope.launch {
            when (val carsMap = carRepository.getManufacturers(page = uiState.value.page)) {
                is RetrofitResult.Error -> updateUiState {
                    copy(
                        error = carsMap.message,
                        isLoading = false,
                    )
                }

                RetrofitResult.Loading -> updateUiState { copy(isLoading = true) }
                is RetrofitResult.Success<ManufacturersData> -> updateUiState {
                    copy(
                        manufacturersMap = uiState.value.manufacturersMap + carsMap.data.mapOfManufacturers,
                        isLoading = false,
                        page = uiState.value.page + 1,
                        totalPages = carsMap.data.totalPages
                    )
                }
            }
        }
    }
}

private fun initialUiState() = ManufacturersContract.UiState(manufacturersMap = emptyMap())