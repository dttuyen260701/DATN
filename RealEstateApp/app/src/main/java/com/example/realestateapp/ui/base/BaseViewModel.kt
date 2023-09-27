package com.example.realestateapp.ui.base

import android.util.*
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

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

abstract class BaseViewModel<US : UiEffect>(
    protected var appRepository: AppRepository
) : ViewModel() {
    companion object {
        private val user = mutableStateOf<User?>(null)

        private val isLoading = mutableStateOf(false)

        private var dialogType: MutableState<TypeDialog> = mutableStateOf(TypeDialog.Hide)

        private var requestPermission: (MutableList<String>) -> Unit = { _ -> }

        private var grantedPermission: (Map<String, Boolean>) -> Unit = {}

        private var choiceImageAndUploadPhoto: () -> Unit = { }

        private var onStartUpload: () -> Unit = {}

        private var onDoneUpload: (String) -> Unit = {}

        private var pagingModel = PagingModel()

        private var listenNotification: (idUser: Int) -> Unit = { _ -> }
    }

    protected abstract val uiEffectValue: MutableStateFlow<UiEffect>
    internal abstract val uiEffect: StateFlow<UiEffect>

    internal fun updateUiStateDone() {
        uiEffectValue.value = Done
    }

    internal fun getPagingModel() = pagingModel

    internal fun listenNotificationInvoke(idUser: Int) {
        listenNotification(idUser)
    }

    internal fun MainActivityViewModel.setListenNotification(func: (Int) -> Unit) {
        this.run {
            listenNotification = func
        }
    }

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
        function: () -> Unit
    ) {
        this.run {
            choiceImageAndUploadPhoto = function
        }
    }

    internal fun uploadImageAndGetURL(
        onStart: () -> Unit,
        onDone: (String) -> Unit
    ) {
        onStartUpload = onStart
        onDoneUpload = onDone
        choiceImageAndUploadPhoto()
    }

    internal fun uploadImage(image: File) {
        viewModelScope.launch(Dispatchers.IO) {
            onStartUpload()
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.uploadImage(image)
                ),
                apiSuccess = {
                    onDoneUpload(it.body)
                },
                apiError = {
                    onDoneUpload("")
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

    open suspend fun <T> callAPIOnThread(
        response: MutableList<Flow<ApiResultWrapper<T>>>,
        apiSuccess: (ResponseAPI<out T>) -> Unit,
        apiError: () -> Unit = {},
        onDoneCallApi: () -> Unit = {},
        isShowLoading: Boolean = true,
        isFinishLoading: Boolean = true,
        showDialog: Boolean = true
    ) {
        viewModelScope.launch {
            isLoading.value = isShowLoading
            response.map {
                async {
                    it.collect { result ->
                        Log.e("TTT", "callAPIOnThread: $result", )
                        when (result) {
                            is ApiResultWrapper.Success -> {
                                apiSuccess(result.value)
                            }

                            is ApiResultWrapper.ResponseCodeError -> {
                                apiError()
                                if (showDialog) {
                                    showDialog(
                                        dialog = TypeDialog.ErrorDialog(result.error)
                                    )
                                }
                            }

                            is ApiResultWrapper.NetworkError -> {
                                apiError()
                                if (showDialog) {
                                    showDialog(
                                        dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.NOT_FOUND_INTERNET)
                                    )
                                }
                            }

                            else -> {
                                apiError()
                                if (showDialog) {
                                    showDialog(
                                        dialog = TypeDialog.ErrorDialog(Constants.MessageErrorAPI.INTERNAL_SERVER_ERROR)
                                    )
                                }
                            }
                        }
                    }
                }
            }.awaitAll()

            if (isFinishLoading) isLoading.value = false
            onDoneCallApi()
        }
    }
}
