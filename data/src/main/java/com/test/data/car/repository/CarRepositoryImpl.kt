package com.test.data.car.repository

import com.test.data.base.safeApiCall
import com.test.data.car.remote.CarApi
import com.test.domain.book.base.RetrofitResult
import com.test.domain.car.repository.CarRepository
import java.net.UnknownHostException


class CarRepositoryImpl(private val apiService: CarApi) : CarRepository {
    override suspend fun getManufacturers(
        page: Int,
        pageSize: Int
    ): RetrofitResult<Map<String, String>> {
        return safeApiCall(
            call = { apiService.getManufacturers(page = page, pageSize = pageSize) },
            transform = { it.wkda }
        )
    }

    override suspend fun getModels(manufacturer: String): RetrofitResult<Map<String, String>> {
        return safeApiCall(
            call = { apiService.getCarModels(manufacturer) },
            transform = { it.wkda }
        )
    }

    override suspend fun getModelYears(
        manufacturer: String,
        mainType: String
    ): RetrofitResult<Map<String, String>> {
        return safeApiCall(
            call = { apiService.getModelYears(manufacturer, mainType) },
            transform = { it.wkda }
        )
    }

    override fun filterModels(
        map: Map<String, String>,
        search: String
    ): Map<String, String> {
        if (search.isBlank()) return map
        val s = search.normalize()
        return map.filter { (_, value) ->
            value.normalize().contains(s)
        }
    }

    fun String.normalize() = this.trim().lowercase()

}