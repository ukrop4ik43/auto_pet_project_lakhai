package com.petprojject.autolakhai.ui.navigation.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelEntry : NavKey {
    @Serializable
    data object Ai : TopLevelEntry

    @Serializable
    data object Car : TopLevelEntry
}

