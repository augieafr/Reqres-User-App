package com.augieafr.reqresapp.data.utils

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val exceptionType: ReqresException) : ResultState<T>()
    class Loading<T> : ResultState<T>()
}