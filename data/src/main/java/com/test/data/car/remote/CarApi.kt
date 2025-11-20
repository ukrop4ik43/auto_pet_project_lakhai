package com.test.data.car.remote

import com.test.data.car.model.ManufacturersDto
import com.test.data.car.model.ModelsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi {
    @GET("/v1/car-types/manufacturer")
    suspend fun getManufacturers(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = 15
    ): Response<ManufacturersDto>

    @GET("/v1/car-types/main-types")
    suspend fun getCarModels(
        @Query("manufacturer") manufacturer: String,
    ): Response<ModelsDto>


    @GET("/v1/car-types/built-dates")
    suspend fun getModelYears(
        @Query("manufacturer") manufacturer: String,
        @Query("main-type") mainType: String
    ): Response<ModelsDto>
}