package com.petprojject.data.di

import com.petprojject.data.car.local.HistoryCarsDao
import com.petprojject.data.car.remote.CarApi
import com.petprojject.data.car.repository.CarRepositoryImpl
import com.petprojject.domain.car.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideBookRepository(apiService: CarApi, carsDao: HistoryCarsDao): CarRepository =
        CarRepositoryImpl(apiService = apiService, historyCarsDao = carsDao)
}