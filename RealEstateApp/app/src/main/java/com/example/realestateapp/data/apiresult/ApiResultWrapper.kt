package com.example.realestateapp.data.apiresult

import java.io.IOException
import java.net.SocketTimeoutException

sealed class ApiResultWrapper<out T> {
    data class Success<out T>(val value: ResponseAPI<out T>) : ApiResultWrapper<T>()

    data class ResponseCodeError(val error: String) : ApiResultWrapper<Nothing>()

    data class NetworkError(val io: IOException) : ApiResultWrapper<Nothing>()

    data class TimeOutError(val error: SocketTimeoutException) : ApiResultWrapper<Nothing>()

    object NullResponseError : ApiResultWrapper<Nothing>()

    object ExceptionError : ApiResultWrapper<Nothing>()

    object Loading : ApiResultWrapper<Nothing>()
}
