package com.petprojject.feature_automobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars_history")
data class CarHistoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val manufacturer: String,
    val model: String,
    val year: String
)