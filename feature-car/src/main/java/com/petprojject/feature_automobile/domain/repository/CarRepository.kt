package com.petprojject.feature_automobile.domain.repository

import com.petprojject.core.base.retrofit.RetrofitResult
import com.petprojject.feature_automobile.domain.model.CarHistoryItem
import com.petprojject.feature_automobile.domain.model.ManufacturersData

interface CarRepository {
    suspend fun getManufacturers(
        page: Int = 0, pageSize: Int = 15
    ): RetrofitResult<ManufacturersData>

    suspend fun getModels(
        manufacturer: String
    ): RetrofitResult<Map<String, String>>

    suspend fun getModelYears(
        manufacturer: String, mainType: String
    ): RetrofitResult<Map<String, String>>

    fun filterModels(map: Map<String, String>, search: String): Map<String, String>
    suspend fun saveCarToHistory(carHistoryItem: CarHistoryItem)
    suspend fun getAllCarsHistory(): List<CarHistoryItem>
    suspend fun deleteCarFromHistory(car: CarHistoryItem)
    suspend fun deleteAllCarsFromHistory()
    fun generateGoogleUrl(car: CarHistoryItem): String
    fun generateGoogleUrl(
        manufacturer: String,
        model: String,
        year: String,
    ): String

    suspend fun isInstructionsShowed(): Boolean
    suspend fun setInstructionsShowedTrue()
}