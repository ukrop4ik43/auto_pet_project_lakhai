package com.petprojject.data.car.mapper

import com.petprojject.feature_automobile.data.model.CarHistoryItemEntity
import com.petprojject.domain.car.model.CarHistoryItem

fun CarHistoryItem.toEntity(): CarHistoryItemEntity =
    CarHistoryItemEntity(
        id = this.id ?: 0,
        manufacturer = this.manufacturer,
        model = this.model,
        year = this.year
    )

fun CarHistoryItemEntity.toDomain(): CarHistoryItem =
    CarHistoryItem(
        id = this.id,
        manufacturer = this.manufacturer,
        model = this.model,
        year = this.year
    )
