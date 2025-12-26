package com.petprojject.feature_automobile.data.repository

import com.petprojject.core.base.retrofit.RetrofitResult
import com.petprojject.core.base.retrofit.safeApiCall
import com.petprojject.feature_automobile.data.local.datastore.CarDataStore
import com.petprojject.feature_automobile.data.remote.CarApi
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_automobile.domain.model.ManufacturersData
import com.petprojject.feature_automobile.domain.repository.CarRepository

class CarRepositoryImpl(
    private val apiService: CarApi,
    private val carDataStore: CarDataStore
) : CarRepository {
    override suspend fun getManufacturers(
        page: Int,
        pageSize: Int
    ): RetrofitResult<ManufacturersData> {
        return safeApiCall(
            call = { apiService.getManufacturers(page = page, pageSize = pageSize) },
            transform = {
                ManufacturersData(mapOfManufacturers = it.wkda, totalPages = it.totalPageCount)
            }
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

    override fun generateGoogleUrl(car: CarHistoryItem): String {
        return "$GOOGLE_BASE_URL_WITH_QUERY${car.manufacturer}+${car.model}+${car.year}"

    }

    override fun generateGoogleUrl(
        manufacturer: String,
        model: String,
        year: String
    ): String {
        return "$GOOGLE_BASE_URL_WITH_QUERY$manufacturer+$model+$year"
    }

    override suspend fun isInstructionsShowed(): Boolean {
        return carDataStore.getIsInstructionsOpened()
    }

    override suspend fun setInstructionsShowedTrue() {
        carDataStore.setInstructionsOpenedToTrue()
    }

    fun String.normalize() = this.trim().lowercase()

    companion object {
        private const val GOOGLE_BASE_URL_WITH_QUERY = "https://www.google.com/search?q="
    }
}