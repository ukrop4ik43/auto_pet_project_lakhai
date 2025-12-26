package com.petprojject.feature_ai.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.petprojject.core.base.CollectSideEffect
import com.petprojject.core.navigation.routes.AiScreenRoutes
import com.petprojject.core.navigation.routes.CarScreenRoutes
import com.petprojject.core.navigation.routes.CarScreenRoutes.History
import com.petprojject.core.navigation.routes.CarScreenRoutes.Manufacturers
import com.petprojject.core.navigation.routes.CarScreenRoutes.Start
import com.petprojject.feature_ai.screens.ai_chat.AiMenuContract
import com.petprojject.feature_ai.screens.ai_chat.AiMenuScreen
import com.petprojject.feature_ai.screens.ai_chat.AiMenuViewModel

@Composable
fun AiNavComponent(onBackToCar:()->Unit) {
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
                            //TODO
                        }

                        AiMenuContract.SideEffect.GoToGenerateConclusionClick -> {
                            //TODO
                        }
                    }
                })

                AiMenuScreen(
                    uiState = uiState, onAction = vm::onAction
                )
            }
        }
    )
}