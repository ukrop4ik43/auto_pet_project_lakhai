package com.petprojject.feature_automobile.di

import com.petprojject.feature_automobile.BuildConfig
import com.petprojject.feature_automobile.data.interceptor.QueryInterceptor
import com.petprojject.feature_automobile.data.remote.CarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(QueryInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(providesOkHttpClient(providesHttpLoggingInterceptor()))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CarApi =
        retrofit.create(CarApi::class.java)
}