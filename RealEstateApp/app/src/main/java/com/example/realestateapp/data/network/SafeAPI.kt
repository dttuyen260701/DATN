package com.example.realestateapp.data.network

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.util.Constants.MessageErrorAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by tuyen.dang on 4/30/2023.
 */

open class SafeAPI {
    suspend fun <T> callApi(
        apiFunction: suspend () -> Response<ResponseAPI<T>>
    ): ApiResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                val res = apiFunction()
                when (res.code()) {
                    200 -> {
                        res.body()?.let {
                            ApiResultWrapper.Success(it)
                        } ?: ApiResultWrapper.NullResponseError
                    }
                    400 -> {
                        ApiResultWrapper.ResponseCodeError(MessageErrorAPI.INVALID_INPUT_ERROR)
                    }
                    401 -> {
                        ApiResultWrapper.ResponseCodeError(MessageErrorAPI.AUTHENTICATION_ERROR)
                    }
                    404 -> {
                        ApiResultWrapper.ResponseCodeError(MessageErrorAPI.NOT_FOUND_ERROR)
                    }
                    else -> {
                        ApiResultWrapper.ResponseCodeError(MessageErrorAPI.INTERNAL_SERVER_ERROR)
                    }
                }
            } catch (e: SocketTimeoutException) {
                ApiResultWrapper.TimeOutError(e)
            } catch (e: IOException) {
                ApiResultWrapper.NetworkError(e)
            } catch (e: Exception) {
                ApiResultWrapper.ExceptionError
            }
        }
    }
}
