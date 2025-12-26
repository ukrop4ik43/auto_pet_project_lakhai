package com.petprojject.autolakhai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.petprojject.autolakhai.ui.navigation.model.TopLevelEntry
import com.petprojject.feature_ai.navigation.AiNavComponent
import com.petprojject.feature_automobile.screens.navigation.CarNavComponent


@Composable
fun NavHost() {
    val backStack = remember { mutableStateListOf<TopLevelEntry>(TopLevelEntry.Car) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<TopLevelEntry.Car> {
                CarNavComponent({ backStack.add(TopLevelEntry.Ai) })
            }
            entry<TopLevelEntry.Ai> {
                AiNavComponent(onBackToCar = { backStack.removeLastOrNull() })
            }
        }
    )
}