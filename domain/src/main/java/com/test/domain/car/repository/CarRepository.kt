package com.test.domain.car.repository

import com.test.domain.book.base.RetrofitResult

interface CarRepository {
    suspend fun getManufacturers(
        page: Int = 0,
        pageSize: Int = 15
    ): RetrofitResult<Map<String, String>>

    suspend fun getModels(
        manufacturer: String
    ): RetrofitResult<Map<String, String>>

    suspend fun getModelYears(
      manufacturer: String,
      mainType: String
    ): RetrofitResult<Map<String, String>>


    fun filterModels(map: Map<String, String>, search: String): Map<String, String>
}