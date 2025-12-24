package com.petprojject.data.di

import android.content.Context
import androidx.room.Room
import com.petprojject.data.car.local.datastore.CarDataStore
import com.petprojject.data.car.local.room.CarsHistoryDb
import com.petprojject.data.car.local.room.HistoryCarsDao
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
    fun provideCarDataStore(@ApplicationContext context: Context): CarDataStore =
        CarDataStore(context)

    @Provides
    fun provideCarDao(database: CarsHistoryDb): HistoryCarsDao =
        database.historyCarsDao()

}