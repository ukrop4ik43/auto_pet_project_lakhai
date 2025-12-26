package com.petprojject.feature_automobile.domain.model

data class CarHistoryItem(
    val id: Long?,
    val manufacturer: String,
    val model: String,
    val year: String
)