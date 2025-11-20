package com.test.data.di

import com.test.data.BuildConfig
import com.test.data.car.interceptor.QueryInterceptor
import com.test.data.car.remote.CarApi
import com.test.data.car.repository.CarRepositoryImpl
import com.test.domain.car.repository.CarRepository
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

    @Provides
    @Singleton
    fun provideBookRepository(apiService: CarApi): CarRepository =
        CarRepositoryImpl(apiService)

}