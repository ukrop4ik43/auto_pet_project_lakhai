package com.petprojject.data.di

import android.content.Context
import com.petprojject.data.base.AndroidResources
import com.petprojject.data.car.local.datastore.CarDataStore
import com.petprojject.data.car.local.room.HistoryCarsDao
import com.petprojject.data.car.remote.CarApi
import com.petprojject.data.car.repository.CarRepositoryImpl
import com.petprojject.domain.base.AppResources
import com.petprojject.domain.car.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourcesModule {
    @Provides
    @Singleton
    fun provideAppResources(
        @ApplicationContext context: Context
    ): AppResources = AndroidResources(context)
}