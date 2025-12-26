package com.petprojject.feature_automobile.di

import com.petprojject.feature_automobile.data.local.datastore.CarDataStore
import com.petprojject.feature_automobile.data.remote.CarApi
import com.petprojject.feature_automobile.data.repository.CarRepositoryImpl
import com.petprojject.feature_automobile.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideCarRepository(
        apiService: CarApi, carDataStore: CarDataStore
    ): CarRepository = CarRepositoryImpl(
        apiService = apiService, carDataStore = carDataStore
    )
}