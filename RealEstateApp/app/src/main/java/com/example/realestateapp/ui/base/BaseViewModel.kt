package com.example.realestateapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.apiresult.ApiResultWrapper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 4/30/2023.
 */

abstract class BaseViewModel : ViewModel() {

    internal var showApiPopupError: (errorMessage: String) -> Unit = { _ -> }

    open fun <T> callAPIOnThread(
        funCallApis: MutableList<suspend () -> Flow<ApiResultWrapper<T>>>,
        apiSuccess: (ApiResultWrapper<T>) -> Unit,
        apiError: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val listAsync = mutableListOf<Deferred<Flow<ApiResultWrapper<T>>>>()
            funCallApis.forEach { funCallApi ->
                listAsync.add(async { funCallApi() })
            }
            val response = awaitAll(*listAsync.toTypedArray())
            response.forEach {
                it.collect { result ->
                    when (result) {
                        is ApiResultWrapper.Success -> {
                            apiSuccess(result)
                        }
                        is ApiResultWrapper.NullResponseError -> {
                            apiSuccess(result)
                        }
                        is ApiResultWrapper.ResponseCodeError -> {
                            apiError()
                            showApiPopupError(result.error)
                        }
                        is ApiResultWrapper.NetworkError -> {
                            apiError()
                            showApiPopupError(result.io.message ?: "")
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
