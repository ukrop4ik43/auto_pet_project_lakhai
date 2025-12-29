package com.petprojject.feature_ai.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.petprojject.core.base.CollectSideEffect
import com.petprojject.core.navigation.routes.AiScreenRoutes
import com.petprojject.feature_ai.screens.compare.CompareContract
import com.petprojject.feature_ai.screens.compare.CompareScreen
import com.petprojject.feature_ai.screens.compare.CompareViewModel
import com.petprojject.feature_ai.screens.conclusion.ConclusionContract
import com.petprojject.feature_ai.screens.conclusion.ConclusionScreen
import com.petprojject.feature_ai.screens.conclusion.ConclusionViewModel
import com.petprojject.feature_ai.screens.menu.AiMenuContract
import com.petprojject.feature_ai.screens.menu.AiMenuScreen
import com.petprojject.feature_ai.screens.menu.AiMenuViewModel

@Composable
fun AiNavComponent(onBackToCar: () -> Unit) {
    val backStack = remember { mutableStateListOf<AiScreenRoutes>(AiScreenRoutes.Menu) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AiScreenRoutes.Menu> {
                val vm: AiMenuViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()

                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        AiMenuContract.SideEffect.GoBack -> {
                            onBackToCar()
                        }

                        AiMenuContract.SideEffect.GoToChoiceAlternativesScreen -> {
                            //TODO
                        }

                        AiMenuContract.SideEffect.GoToCompareChoicesClick -> {
                            backStack.add(AiScreenRoutes.Compare)
                        }

                        AiMenuContract.SideEffect.GoToGenerateConclusionClick -> {
                            backStack.add(AiScreenRoutes.Conclusion)
                        }
                    }
                })

                AiMenuScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<AiScreenRoutes.Conclusion> {
                val vm: ConclusionViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    vm.onAction(ConclusionContract.UiAction.Init)
                }
                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        ConclusionContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                    }
                })

                ConclusionScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }

            entry<AiScreenRoutes.Compare> {
                val vm: CompareViewModel = hiltViewModel()

                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    vm.onAction(CompareContract.UiAction.Init)
                }
                CollectSideEffect(sideEffect = vm.sideEffect, onSideEffect = {
                    when (it) {
                        CompareContract.SideEffect.GoBack -> backStack.removeLastOrNull()
                    }
                })

                CompareScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }
        }
    )
}