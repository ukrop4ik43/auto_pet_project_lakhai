package com.petprojject.domain.car.repository

import com.petprojject.domain.car.model.CarHistoryItem

interface CarHistoryRepository {
    suspend fun saveCarToHistory(carHistoryItem: CarHistoryItem)
    suspend fun getAllCarsHistory(): List<CarHistoryItem>
    suspend fun deleteCarFromHistory(car: CarHistoryItem)
    suspend fun deleteAllCarsFromHistory()
}