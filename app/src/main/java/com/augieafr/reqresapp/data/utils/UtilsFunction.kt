package com.augieafr.reqresapp.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T> executeRequest(
    crossinline action: suspend (FlowCollector<ResultState<T>>) -> Unit
) =
    flow {
        emit(ResultState.Loading())
        action(this)
    }.catch {
        emit(ResultState.Error(ReqresException.UnexpectedError))
    }.flowOn(Dispatchers.IO)