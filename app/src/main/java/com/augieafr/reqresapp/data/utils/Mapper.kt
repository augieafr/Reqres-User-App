package com.augieafr.reqresapp.data.utils

import com.augieafr.reqresapp.data.model.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.toReqresException() : ReqresException {
    val message = Gson().fromJson(errorBody()?.string(), ErrorResponse::class.java).error
    return when (code()) {
        400 -> ReqresException.BadRequest(message)
        401 -> ReqresException.Unauthorized(message)
        403 -> ReqresException.Forbidden(message)
        404 -> ReqresException.NotFound(message)
        500 -> ReqresException.InternalServerError(message)
        502 -> ReqresException.BadGateway(message)
        503 -> ReqresException.ServiceUnavailable(message)
        504 -> ReqresException.GatewayTimeout(message)
        else -> ReqresException.UnexpectedError
    }
}