package com.petprojject.autolakhai.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.petprojject.core.navigation.routes.Screen
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersScreen
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersViewModel
import com.petprojject.feature_automobile.screens.models.ModelsContract
import com.petprojject.feature_automobile.screens.models.ModelsScreen
import com.petprojject.feature_automobile.screens.models.ModelsViewModel
import com.petprojject.feature_automobile.screens.summary.SummaryContract
import com.petprojject.feature_automobile.screens.summary.SummaryScreen
import com.petprojject.feature_automobile.screens.summary.SummaryViewModel
import com.petprojject.feature_automobile.screens.years.YearsContract
import com.petprojject.feature_automobile.screens.years.YearsScreen
import com.petprojject.feature_automobile.screens.years.YearsViewModel


@Composable
fun NavHost() {
    val backStack = remember { mutableStateListOf<Any>(Screen.Manufacturers) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Screen.Summary -> NavEntry(key) {
                    val vm: SummaryViewModel = hiltViewModel()

                    val uiState by vm.uiState.collectAsState()
                    LaunchedEffect(key) {
                        vm.onAction(
                            SummaryContract.UiAction.Init(
                                manufacturer = key.chosenManufacturer,
                                model = key.chosenModel,
                                year = key.chosenYear
                            )
                        )
                    }
                    LaunchedEffect(vm.sideEffect) {
                        vm.sideEffect.collect {
                            when (it) {
                                SummaryContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                            }
                        }
                    }
                    SummaryScreen(
                        uiState = uiState,
                        onAction = vm::onAction
                    )
                }

                is Screen.Models -> NavEntry(key) {
                    val vm: ModelsViewModel = hiltViewModel()

                    val uiState by vm.uiState.collectAsState()
                    LaunchedEffect(key) {
                        vm.onAction(ModelsContract.UiAction.Init(key.chosenManufacturer))
                    }
                    LaunchedEffect(vm.sideEffect) {
                        vm.sideEffect.collect {
                            when (it) {
                                is ModelsContract.SideEffect.NavigateToYearsScreen -> {
                                    backStack.add(
                                        Screen.Years(it.manufacturer, it.model)
                                    )
                                }

                                ModelsContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                            }
                        }
                    }
                    ModelsScreen(
                        uiState = uiState,
                        onAction = vm::onAction
                    )
                }

                is Screen.Years -> NavEntry(key) {
                    val vm: YearsViewModel = hiltViewModel()

                    val uiState by vm.uiState.collectAsState()
                    LaunchedEffect(key) {
                        vm.onAction(
                            YearsContract.UiAction.Init(
                                key.chosenManufacturer,
                                key.chosenModel
                            )
                        )
                    }
                    LaunchedEffect(vm.sideEffect) {
                        vm.sideEffect.collect {
                            when (it) {
                                is YearsContract.SideEffect.NavigateToResultScreen -> {
                                    backStack.add(
                                        Screen.Summary(
                                            chosenManufacturer = it.manufacturer,
                                            chosenModel = it.model,
                                            chosenYear = it.year
                                        )
                                    )
                                }

                                YearsContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                            }
                        }
                    }
                    YearsScreen(
                        uiState = uiState,
                        onAction = vm::onAction
                    )
                }

                is Screen.Manufacturers -> NavEntry(key) {
                    val vm: ManufacturersViewModel = hiltViewModel()

                    val uiState by vm.uiState.collectAsState()
                    LaunchedEffect(true) {
                        vm.onAction(ManufacturersContract.UiAction.Init)
                    }
                    LaunchedEffect(vm.sideEffect) {
                        vm.sideEffect.collect {
                            when (it) {
                                is ManufacturersContract.SideEffect.NavigateToModelsScreen -> backStack.add(
                                    Screen.Models(it.manufacturer)
                                )
                            }
                        }
                    }
                    ManufacturersScreen(
                        uiState = uiState,
                        onAction = vm::onAction
                    )
                }

                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}