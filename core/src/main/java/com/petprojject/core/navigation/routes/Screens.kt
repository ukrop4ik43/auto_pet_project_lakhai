package com.petprojject.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {
    @Serializable
    data object Manufacturers : Screen

    @Serializable
    data class Models(val chosenManufacturer: Pair<String, String>) : Screen

    @Serializable
    data class Years(
        val chosenManufacturer: Pair<String, String>,
        val chosenModel: Pair<String, String>
    ) : Screen

    @Serializable
    data class Summary(
        val chosenManufacturer: Pair<String, String>,
        val chosenModel: Pair<String, String>,
        val chosenYear: Pair<String, String>
    ) : Screen

    @Serializable
    data object Start : Screen

    @Serializable
    data object History : Screen

    @Serializable
    data class WebOpener(val url:String) : Screen

}