package com.test.feature_ai.di

import com.test.feature_ai.data.AiGeneratorRepositoryImpl
import com.test.feature_ai.domain.AiGeneratorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AiModule {
    @Provides
    fun provideAiGeneratorRepository(): AiGeneratorRepository = AiGeneratorRepositoryImpl()
}