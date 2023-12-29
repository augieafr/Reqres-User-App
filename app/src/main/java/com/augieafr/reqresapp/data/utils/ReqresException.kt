package com.augieafr.reqresapp.data.utils

sealed class ReqresException(val message: String) {
    class BadRequest(message: String) : ReqresException(message)
    class Unauthorized(message: String) : ReqresException(message)
    class Forbidden(message: String) : ReqresException(message)
    class NotFound(message: String) : ReqresException(message)
    class InternalServerError(message: String) : ReqresException(message)
    class BadGateway(message: String) : ReqresException(message)
    class ServiceUnavailable(message: String) : ReqresException(message)
    class GatewayTimeout(message: String) : ReqresException(message)
    data object UnexpectedError : ReqresException("Unexpected Error")
    data object EmptyResultException : ReqresException("Empty Result")
}