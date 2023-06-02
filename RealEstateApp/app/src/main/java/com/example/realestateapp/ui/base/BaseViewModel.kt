package com.example.realestateapp.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.PagingModel
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.MainActivityViewModel
import com.example.realestateapp.util.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

@Stable
sealed interface TypeDialog {
    object Hide : TypeDialog

    data class ErrorDialog(val message: String) : TypeDialog

    data class MessageDialog(val title: String, val message: String, val btnText: String) :
        TypeDialog

    data class ConfirmDialog(
        val title: String? = null,
        val message: String,
        val negativeBtnText: String,
        val onBtnNegativeClick: () -> Unit,
        val positiveBtnText: String,
        val onBtnPositiveClick: () -> Unit,
    ) : TypeDialog

    data class ChoiceDataDialog(
        val title: String,
        val loadData: (String, () -> Unit) -> Unit,
        val isEnableSearchFromApi: Boolean,
        val onItemClick: (ItemChoose) -> Unit,
        val data: MutableList<ItemChoose>
    ) : TypeDialog

    data class ShowImageDialog(
        val data: MutableList<Image>,
        val currentIndex: Int
    ) : TypeDialog
}

abstract class BaseViewModel<US : UiState> : ViewModel() {
    companion object {
        private val user = mutableStateOf<User?>(null)

        private val isLoading = mutableStateOf(false)

        private var dialogType: MutableState<TypeDialog> = mutableStateOf(TypeDialog.Hide)

        private var requestPermission: (MutableList<String>) -> Unit = { _ -> }

        private var grantedPermission: (Map<String, Boolean>) -> Unit = {}

        private var choiceImageAndUploadPhoto:
                    (onStart: () -> Unit, onDone: (String) -> Unit) -> Unit = { _, _ -> }

        private var pagingModel = PagingModel()
    }

    abstract var uiState: MutableState<UiState>

    @Inject
    lateinit var appRepository: AppRepository

    internal fun getPagingModel() = pagingModel

    internal fun updatePagingModel(
        totalRecordsNew: Int = 0,
        totalPageNew: Int = 1
    ) {
        pagingModel.run {
            totalPage = totalPageNew
            totalRecords = totalRecordsNew
            if (pageIndex <= totalPage)
                pageIndex += 1
        }
    }

    internal fun resetPaging() {
        pagingModel = PagingModel()
    }

    internal fun requestPermissionListener(
        permission: MutableList<String>,
        onGranted: (Map<String, Boolean>) -> Unit
    ) {
        grantedPermission = onGranted
        requestPermission(permission)
    }

    internal fun MainActivityViewModel.setUploadImageAndGetURL(
        function: (onStart: () -> Unit, onDone: (String) -> Unit) -> Unit
    ) {
        this.run {
            choiceImageAndUploadPhoto = function
        }
    }

    internal fun uploadImageAndGetURL(
        onStart: () -> Unit,
        onDone: (String) -> Unit
    ) {
        choiceImageAndUploadPhoto(onStart, onDone)
    }

    internal fun uploadImage(image: File, onDone: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.uploadImage(image)
                ),
                apiSuccess = {
                    onDone(it.body)
                },
                apiError = {
                    onDone("")
                }
            )
        }
    }

    internal fun MainActivityViewModel.setRequestPermissionListener(requestPermissionNew: (MutableList<String>) -> Unit) {
        this.run {
            requestPermission = requestPermissionNew
        }
    }

    internal fun MainActivityViewModel.onGrantedPermission(result: Map<String, Boolean>) {
        this.run {
            grantedPermission(result)
        }
    }

    internal fun getUser() = user

    internal fun getIsLoading() = isLoading

    internal fun getDialogType() = dialogType

    internal fun showDialog(dialog: TypeDialog) {
        dialogType.value = dialog
    }

    open fun <T> callAPIOnThread(
        funCallApis: MutableList<Flow<ApiResultWrapper<T>>>,
        apiSuccess: (ResponseAPI<out T>) -> Unit,
        apiError: () -> Unit = {},
        onDoneCallApi: () -> Unit = {},
        showDialog: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val listAsync = mutableListOf<Deferred<Flow<ApiResultWrapper<T>>>>()

            funCallApis.forEach { funCallApi ->
                listAsync.add(async { funCallApi })
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
                            if (showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(result.error)
                            )
                        }
                        is ApiResultWrapper.NetworkError -> {
                            isLoading.value = false
                            apiError()
                            if (showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.NOT_FOUND_INTERNET)
                            )
                        }
                        else -> {
                            isLoading.value = false
                            apiError()
                            if (showDialog) showDialog(
                                dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.INTERNAL_SERVER_ERROR)
                            )
                        }
                    }
                }
            }

            onDoneCallApi()
        }
    }
}
