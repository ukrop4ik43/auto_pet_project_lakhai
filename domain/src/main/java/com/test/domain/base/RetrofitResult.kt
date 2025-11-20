package com.test.domain.book.base

sealed class RetrofitResult<out T> {
    data class Success<out T>(val data: T) : RetrofitResult<T>()
    data class Error(val message: String, val code: Int? = null) : RetrofitResult<Nothing>()
    object Loading : RetrofitResult<Nothing>()
}