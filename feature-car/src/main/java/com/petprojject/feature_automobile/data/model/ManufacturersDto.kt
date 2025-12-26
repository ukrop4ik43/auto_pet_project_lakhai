package com.petprojject.feature_automobile.data.model


data class ManufacturersDto(
    val page: Int,
    val pageSize: Int,
    val totalPageCount: Int,
    val wkda: Map<String, String>
)