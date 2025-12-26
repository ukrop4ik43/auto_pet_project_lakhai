package com.petprojject.core.base.retrofit

import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    transform: (T) -> R
): RetrofitResult<R> {
    return try {
        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                RetrofitResult.Success(transform(body))
            } else {
                RetrofitResult.Error("Empty body", response.code())
            }
        } else {
            RetrofitResult.Error(response.message(), response.code())
        }

    } catch (e: UnknownHostException) {
        RetrofitResult.Error("No internet connection")
    } catch (e: Exception) {
        RetrofitResult.Error(e.message ?: "Unknown error")
    }
}