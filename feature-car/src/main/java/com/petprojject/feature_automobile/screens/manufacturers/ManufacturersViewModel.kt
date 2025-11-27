package com.petprojject.feature_automobile.screens.manufacturers

import com.petprojject.domain.car.repository.CarRepository
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract.SideEffect.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petprojject.core.base.MVI
import com.petprojject.core.base.mvi
import com.petprojject.core.di.IoDispatcher
import com.petprojject.domain.book.base.RetrofitResult
import com.petprojject.domain.car.model.ManufacturersData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ManufacturersViewModel @Inject constructor(
    private val carRepository: CarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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
                if (uiState.value.isLoading || uiState.value.totalPages+1 == uiState.value.page) return
                collectManufacturers()
            }
        }
    }


    private fun collectManufacturers() {
        viewModelScope.launch(ioDispatcher) {
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