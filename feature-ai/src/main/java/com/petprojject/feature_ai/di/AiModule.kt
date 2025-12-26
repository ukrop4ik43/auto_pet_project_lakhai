package com.petprojject.feature_ai.di

import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_ai.data.AiGeneratorRepositoryImpl
import com.petprojject.feature_ai.domain.AiGeneratorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AiModule {
    @Provides
    fun provideAiGeneratorRepository(carHistoryRepository: CarHistoryRepository): AiGeneratorRepository =
        AiGeneratorRepositoryImpl(carHistoryRepository)
}