package com.petprojject.feature_automobile.di

import android.content.Context
import com.petprojject.feature_automobile.data.local.datastore.CarDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideCarDataStore(@ApplicationContext context: Context): CarDataStore =
        CarDataStore(context)
}