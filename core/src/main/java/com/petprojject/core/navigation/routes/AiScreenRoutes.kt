package com.petprojject.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable

sealed interface AiScreenRoutes : NavKey {
    @Serializable
    data object Menu : AiScreenRoutes
}
