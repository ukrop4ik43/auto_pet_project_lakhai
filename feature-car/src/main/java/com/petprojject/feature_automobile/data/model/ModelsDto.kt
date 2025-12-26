package com.petprojject.feature_automobile.data.model


data class ModelsDto(
    val page: Int,
    val pageSize: Int,
    val totalPageCount: Int,
    val wkda: Map<String, String>
)