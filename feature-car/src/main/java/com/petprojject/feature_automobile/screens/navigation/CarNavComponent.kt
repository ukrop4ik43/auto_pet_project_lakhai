package com.petprojject.feature_automobile.screens.navigation

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.petprojject.core.base.CollectSideEffect
import com.petprojject.core.navigation.routes.CarScreenRoutes
import com.petprojject.core.navigation.routes.CarScreenRoutes.History
import com.petprojject.core.navigation.routes.CarScreenRoutes.Manufacturers
import com.petprojject.core.navigation.routes.CarScreenRoutes.Models
import com.petprojject.core.navigation.routes.CarScreenRoutes.Start
import com.petprojject.core.navigation.routes.CarScreenRoutes.Summary
import com.petprojject.core.navigation.routes.CarScreenRoutes.WebOpener
import com.petprojject.core.navigation.routes.CarScreenRoutes.Years
import com.petprojject.feature_automobile.screens.history.HistoryContract
import com.petprojject.feature_automobile.screens.history.HistoryScreen
import com.petprojject.feature_automobile.screens.history.HistoryViewModel
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersScreen
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersViewModel
import com.petprojject.feature_automobile.screens.models.ModelsContract
import com.petprojject.feature_automobile.screens.models.ModelsScreen
import com.petprojject.feature_automobile.screens.models.ModelsViewModel
import com.petprojject.feature_automobile.screens.start.StartContract
import com.petprojject.feature_automobile.screens.start.StartScreen
import com.petprojject.feature_automobile.screens.start.StartViewModel
import com.petprojject.feature_automobile.screens.summary.SummaryContract
import com.petprojject.feature_automobile.screens.summary.SummaryScreen
import com.petprojject.feature_automobile.screens.summary.SummaryViewModel
import com.petprojject.feature_automobile.screens.webview.WebOpenerContract
import com.petprojject.feature_automobile.screens.webview.WebOpenerScreen
import com.petprojject.feature_automobile.screens.webview.WebOpenerViewModel
import com.petprojject.feature_automobile.screens.years.YearsContract
import com.petprojject.feature_automobile.screens.years.YearsScreen
import com.petprojject.feature_automobile.screens.years.YearsViewModel

@Composable
fun CarNavComponent(onAiSectionClick: () -> Unit) {
    val backStack = remember { mutableStateListOf<CarScreenRoutes>(Start) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Start> {
                val vm: StartViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()

                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        SummaryContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                        StartContract.SideEffect.GoToChooseYourCar -> backStack.add(
                            Manufacturers
                        )

                        StartContract.SideEffect.GoToHistory -> backStack.add(History)
                        StartContract.SideEffect.GoToAi -> {
                            onAiSectionClick()
                        }
                    }
                })


                StartScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<WebOpener> { entry ->
                val vm: WebOpenerViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(entry) {
                    vm.onAction(WebOpenerContract.UiAction.Init(entry.url))
                }
                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        SummaryContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                        StartContract.SideEffect.GoToChooseYourCar -> backStack.add(
                            Manufacturers
                        )

                        StartContract.SideEffect.GoToHistory -> backStack.add(History)
                    }
                })

                WebOpenerScreen(
                    state = uiState
                )
            }

            entry<History> { entry ->
                val vm: HistoryViewModel = hiltViewModel()
                val context = LocalContext.current
                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    vm.onAction(
                        HistoryContract.UiAction.Init
                    )
                }
                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        HistoryContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                        is HistoryContract.SideEffect.NavigateToWebOpener -> backStack.add(
                            WebOpener(it.url)
                        )

                        is HistoryContract.SideEffect.ShowToast -> {
                            Toast.makeText(context, it.text, 1000).show()
                        }
                    }
                })

                HistoryScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }


            entry<Summary> { entry ->
                val vm: SummaryViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(entry) {
                    vm.onAction(
                        SummaryContract.UiAction.Init(
                            manufacturer = entry.chosenManufacturer,
                            model = entry.chosenModel,
                            year = entry.chosenYear
                        )
                    )
                }
                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        SummaryContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                        SummaryContract.SideEffect.GoToStart -> backStack.add(Start)
                        is SummaryContract.SideEffect.GoToWebView -> backStack.add(
                            WebOpener(
                                it.url
                            )
                        )
                    }
                })

                SummaryScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<Models> { entry ->
                val vm: ModelsViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(entry) {
                    vm.onAction(ModelsContract.UiAction.Init(entry.chosenManufacturer))
                }

                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        is ModelsContract.SideEffect.NavigateToYearsScreen -> {
                            backStack.add(
                                Years(it.manufacturer, it.model)
                            )
                        }

                        ModelsContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                    }
                })

                ModelsScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<Years> { entry ->
                val vm: YearsViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(entry) {
                    vm.onAction(
                        YearsContract.UiAction.Init(
                            entry.chosenManufacturer, entry.chosenModel
                        )
                    )
                }

                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        is YearsContract.SideEffect.NavigateToResultScreen -> {
                            backStack.add(
                                Summary(
                                    chosenManufacturer = it.manufacturer,
                                    chosenModel = it.model,
                                    chosenYear = it.year
                                )
                            )
                        }

                        YearsContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                    }
                })

                YearsScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<Manufacturers> { entry ->
                val vm: ManufacturersViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(true) {
                    vm.onAction(ManufacturersContract.UiAction.Init)
                }

                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        is ManufacturersContract.SideEffect.NavigateToModelsScreen -> backStack.add(
                            Models(it.manufacturer)
                        )

                        ManufacturersContract.SideEffect.NavigateBack -> backStack.removeLastOrNull()
                    }
                })

                ManufacturersScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }
        })
}