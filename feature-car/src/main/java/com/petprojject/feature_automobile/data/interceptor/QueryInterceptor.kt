package com.petprojject.feature_automobile.data.interceptor

import com.petprojject.feature_automobile.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("wa_key",
                BuildConfig.API_KEY
            )
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
