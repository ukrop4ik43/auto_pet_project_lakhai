package com.petprojject.data.di

import android.content.Context
import androidx.room.Room
import com.petprojject.data.car.repository.CarHistoryRepositoryImpl
import com.petprojject.data.car.room.CarsHistoryDb
import com.petprojject.data.car.room.HistoryCarsDao
import com.petprojject.domain.car.repository.CarHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CarsHistoryDb =
        Room.databaseBuilder(
            context,
            CarsHistoryDb::class.java,
            "cars_history_db"
        ).build()


    @Provides
    fun provideCarDao(database: CarsHistoryDb): HistoryCarsDao =
        database.historyCarsDao()

    @Provides
    fun provideCarRepository(
        historyCarsDao: HistoryCarsDao
    ): CarHistoryRepository = CarHistoryRepositoryImpl(
        historyCarsDao
    )
}