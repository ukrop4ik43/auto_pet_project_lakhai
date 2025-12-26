package com.petprojject.feature_automobile.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petprojject.feature_automobile.data.model.CarHistoryItemEntity

@Database(entities = [CarHistoryItemEntity::class], version = 1)
abstract class CarsHistoryDb : RoomDatabase() {
    abstract fun historyCarsDao(): HistoryCarsDao
}