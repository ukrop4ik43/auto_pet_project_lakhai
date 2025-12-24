package com.petprojject.data.car.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petprojject.data.car.model.CarHistoryItemEntity

@Database(entities = [CarHistoryItemEntity::class], version = 1)
abstract class CarsHistoryDb : RoomDatabase() {
    abstract fun historyCarsDao(): HistoryCarsDao
}