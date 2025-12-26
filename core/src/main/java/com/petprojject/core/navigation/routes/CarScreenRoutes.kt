package com.petprojject.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface CarScreenRoutes : NavKey {
    @Serializable
    data object Manufacturers : CarScreenRoutes

    @Serializable
    data class Models(val chosenManufacturer: Pair<String, String>) : CarScreenRoutes

    @Serializable
    data class Years(
        val chosenManufacturer: Pair<String, String>,
        val chosenModel: Pair<String, String>
    ) : CarScreenRoutes

    @Serializable
    data class Summary(
        val chosenManufacturer: Pair<String, String>,
        val chosenModel: Pair<String, String>,
        val chosenYear: Pair<String, String>
    ) : CarScreenRoutes

    @Serializable
    data object Start : CarScreenRoutes

    @Serializable
    data object History : CarScreenRoutes

    @Serializable
    data class WebOpener(val url: String) : CarScreenRoutes

}