package com.petprojject.data.car.model


data class ManufacturersDto(
    val page: Int,
    val pageSize: Int,
    val totalPageCount: Int,
    val wkda: Map<String, String>
)