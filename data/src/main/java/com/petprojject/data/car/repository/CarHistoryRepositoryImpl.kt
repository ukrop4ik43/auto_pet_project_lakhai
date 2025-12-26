package com.petprojject.data.car.repository

import com.petprojject.data.car.mapper.toDomain
import com.petprojject.data.car.mapper.toEntity
import com.petprojject.data.car.room.HistoryCarsDao
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository

class CarHistoryRepositoryImpl(private val carsDao: HistoryCarsDao) : CarHistoryRepository {
    override suspend fun saveCarToHistory(
        carHistoryItem: CarHistoryItem
    ) {
        carsDao.insertCarToHistory(carHistoryItem.toEntity())
    }

    override suspend fun getAllCarsHistory(): List<CarHistoryItem> {
        return carsDao.getAllHistory().map { it.toDomain() }
    }

    override suspend fun deleteCarFromHistory(car: CarHistoryItem) {
        carsDao.deleteCar(car = car.toEntity())
    }

    override suspend fun deleteAllCarsFromHistory() {
        carsDao.deleteAllHistory()
    }
}