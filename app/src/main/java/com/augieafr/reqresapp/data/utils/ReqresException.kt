package com.augieafr.reqresapp.data.utils

sealed class ReqresException(override val message: String) : Exception() {
    class BadRequest(message: String) : ReqresException(message)
    class Unauthorized(message: String) : ReqresException(message)
    class Forbidden(message: String) : ReqresException(message)
    class NotFound(message: String) : ReqresException(message)
    class InternalServerError(message: String) : ReqresException(message)
    class BadGateway(message: String) : ReqresException(message)
    class ServiceUnavailable(message: String) : ReqresException(message)
    class GatewayTimeout(message: String) : ReqresException(message)
    data object UnexpectedError : ReqresException("Unexpected Error Occurred")
    data object EmptyResultException : ReqresException("There is no data available")
}