package com.example.realestateapp.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.User
import com.example.realestateapp.util.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by tuyen.dang on 4/30/2023.
 */

@Stable
sealed interface TypeDialog {
    object Hide : TypeDialog

    class ErrorDialog(val message: String) : TypeDialog

    class MessageDialog(val title: String, val message: String, val btnText: String) :
        TypeDialog

    class ConfirmDialog(
        val title: String? = null,
        val message: String,
        val negativeBtnText: String,
        val onBtnNegativeClick: () -> Unit,
        val positiveBtnText: String,
        val onBtnPositiveClick: () -> Unit,
    ) : TypeDialog
}

abstract class BaseViewModel<US : UiState> : ViewModel() {
    companion object {
        private val user = mutableStateOf<User?>(null)

        private val isLoading = mutableStateOf(false)

        private var dialogType: MutableState<TypeDialog> = mutableStateOf(TypeDialog.Hide)
    }

    abstract var uiState: MutableState<UiState>

    internal fun getUser() = user

    internal fun getIsLoading() = isLoading

    internal fun getDialogType() = dialogType

    internal fun showDialog(dialog: TypeDialog) {
        dialogType.value = dialog
    }

    open fun <T> callAPIOnThread(
        funCallApis: MutableList<suspend () -> Flow<ApiResultWrapper<T>>>,
        apiSuccess: (ResponseAPI<out T>) -> Unit,
        apiError: () -> Unit = {},
        showDialog: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val listAsync = mutableListOf<Deferred<Flow<ApiResultWrapper<T>>>>()
            funCallApis.forEach { funCallApi ->
                listAsync.add(async { funCallApi() })
            }
            val response = awaitAll(*listAsync.toTypedArray())
            response.forEach {
                it.collect { result ->
                    when (result) {
                        is ApiResultWrapper.Loading -> {
                            isLoading.value = true
                        }
                        is ApiResultWrapper.Success -> {
                            isLoading.value = false
                            withContext(Dispatchers.Main) {
                                apiSuccess(result.value)
                            }
                        }
                        is ApiResultWrapper.ResponseCodeError -> {
                            isLoading.value = false
                            apiError()
                            if(showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(result.error)
                            )
                        }
                        is ApiResultWrapper.NetworkError -> {
                            isLoading.value = false
                            apiError()
                            if(showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.NOT_FOUND_INTERNET)
                            )
                        }
                        else -> {
                            isLoading.value = false
                            if(showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.INTERNAL_SERVER_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }
}
