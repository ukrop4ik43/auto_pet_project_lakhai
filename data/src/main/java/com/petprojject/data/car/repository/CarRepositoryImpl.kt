package com.petprojject.data.car.repository

import com.petprojject.data.base.safeApiCall
import com.petprojject.data.car.local.datastore.CarDataStore
import com.petprojject.data.car.local.room.HistoryCarsDao
import com.petprojject.data.car.mapper.toDomain
import com.petprojject.data.car.mapper.toEntity
import com.petprojject.data.car.remote.CarApi
import com.petprojject.domain.base.RetrofitResult
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.model.ManufacturersData
import com.petprojject.domain.car.repository.CarRepository


class CarRepositoryImpl(
    private val apiService: CarApi,
    private val historyCarsDao: HistoryCarsDao,
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

    override suspend fun saveCarToHistory(
        carHistoryItem: CarHistoryItem
    ) {
        historyCarsDao.insertCarToHistory(carHistoryItem.toEntity())
    }

    override suspend fun getAllCarsHistory(): List<CarHistoryItem> {
        return historyCarsDao.getAllHistory().map { it.toDomain() }
    }

    override suspend fun deleteCarFromHistory(car: CarHistoryItem) {
        historyCarsDao.deleteCar(car = car.toEntity())
    }

    override suspend fun deleteAllCarsFromHistory() {
        historyCarsDao.deleteAllHistory()
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