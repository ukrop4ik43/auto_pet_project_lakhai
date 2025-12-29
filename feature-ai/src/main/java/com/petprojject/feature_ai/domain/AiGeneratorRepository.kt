package com.petprojject.feature_ai.domain

import com.petprojject.domain.car.model.CarHistoryItem

interface AiGeneratorRepository {
    suspend fun getResponseFromAi(request: String): String
    suspend fun getConclusionAboutUser(): String
    suspend fun compareCars(firstCar: CarHistoryItem, secondCar: CarHistoryItem): String
    suspend fun getCarAlternatives(car: CarHistoryItem): String

}