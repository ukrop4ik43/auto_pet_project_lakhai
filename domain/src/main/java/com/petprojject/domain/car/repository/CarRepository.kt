package com.petprojject.domain.car.repository

import com.petprojject.domain.base.RetrofitResult
import com.petprojject.domain.car.model.ManufacturersData

interface CarRepository {
    suspend fun getManufacturers(
        page: Int = 0,
        pageSize: Int = 15
    ): RetrofitResult<ManufacturersData>

    suspend fun getModels(
        manufacturer: String
    ): RetrofitResult<Map<String, String>>

    suspend fun getModelYears(
      manufacturer: String,
      mainType: String
    ): RetrofitResult<Map<String, String>>


    fun filterModels(map: Map<String, String>, search: String): Map<String, String>
}