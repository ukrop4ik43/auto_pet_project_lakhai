package com.petprojject.data.car.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.petprojject.data.car.model.CarHistoryItemEntity

@Dao
interface HistoryCarsDao {
    @Query("SELECT * FROM cars_history")
    suspend fun getAllHistory(): List<CarHistoryItemEntity>

    @Insert
    suspend fun insertCarToHistory(car: CarHistoryItemEntity)

    @Delete
    suspend fun deleteCar(car: CarHistoryItemEntity)

    @Query("DELETE FROM cars_history")
    suspend fun deleteAllHistory()
}