package com.example.stockprice.utils

sealed class ResultState<T> {
    class Loading<T> : ResultState<T>()
    class Error<T>(val throwable: Throwable) : ResultState<T>()
    class Success<T>(val data: T) : ResultState<T>()
}
