package com.example.realestateapp.ui.home

import android.Manifest
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.readStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class HomeUiState : UiState() {
    object InitView : HomeUiState()

    object Loading : HomeUiState()

    object Error : HomeUiState()

    object Done : HomeUiState()

    object DoneSignInBackground : HomeUiState()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : HomeUiState()

    data class GetLatestSuccess(val data: MutableList<RealEstateList>) : HomeUiState()

    data class GetMostViewSuccess(val data: MutableList<RealEstateList>) : HomeUiState()

    data class GetHighestPriceSuccess(val data: MutableList<RealEstateList>) : HomeUiState()

    data class GetLowestPriceSuccess(val data: MutableList<RealEstateList>) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application, appRepository: AppRepository
) : BaseViewModel<HomeUiState>(appRepository) {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(HomeUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var realEstatesLatest = mutableStateListOf<RealEstateList>()
    internal var realEstatesMostView = mutableStateListOf<RealEstateList>()
    internal var realEstatesHighestPrice = mutableStateListOf<RealEstateList>()
    internal var realEstatesLowestPrice = mutableStateListOf<RealEstateList>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    internal fun backgroundSignIn() {
        uiStateValue.value = HomeUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.readStoreLauncher(onReadSuccess = { email, pass ->
                viewModelScope.launch {
                    callAPIOnThread(
                        response = mutableListOf(
                            appRepository.signIn(
                                email = email, password = pass, showLoading = false
                            )
                        ), apiSuccess = {
                            getUser().value = it.body
                            AuthenticationObject.token = it.body?.token ?: ""
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                requestPermissionListener(
                                    permission = mutableListOf(
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                ) { results ->
                                    if (results.entries.all { item -> item.value }) {
                                        listenNotificationInvoke(getUser().value?.id ?: -1)
                                    }
                                }
                            } else {
                                listenNotificationInvoke(getUser().value?.id ?: -1)
                            }
                        }, onDoneCallApi = {
                            uiStateValue.value = HomeUiState.DoneSignInBackground
                        }, showDialog = false
                    )
                }
            }, onErrorAction = {
                viewModelScope.launch {
                    uiStateValue.value = HomeUiState.DoneSignInBackground
                }
            })
        }
    }

    internal fun getTypes() {
        uiStateValue.value = HomeUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(response = mutableListOf(
                appRepository.getTypes(showLoading = false),
            ), apiSuccess = {
                uiStateValue.value = HomeUiState.GetTypesSuccess(it.body)
            }, apiError = {
                uiStateValue.value = HomeUiState.Error
            }, showDialog = false
            )
        }
    }

    internal fun getPostsWOptions(
        isMostView: Boolean = false,
        isLatest: Boolean = false,
        isHighestPrice: Boolean = false,
        isLowestPrice: Boolean = false,
        showLoading: Boolean = false
    ) {
        uiStateValue.value = HomeUiState.Loading
        viewModelScope.launch {
            val typePropertyIds = typesData.filter { it.isSelected }.map { it.id }
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 10,
                        isMostView = isMostView,
                        typePropertyIds = typePropertyIds.toMutableList(),
                        isHighestPrice = isHighestPrice,
                        isLowestPrice = isLowestPrice,
                        isLatest = isLatest,
                        userId = getUser().value?.id ?: 0,
                        showLoading = showLoading
                    )
                ), apiSuccess = {
                    uiStateValue.value = when {
                        isLatest -> HomeUiState.GetLatestSuccess(it.body.items ?: mutableListOf())
                        isMostView -> HomeUiState.GetMostViewSuccess(
                            it.body.items ?: mutableListOf()
                        )
                        isHighestPrice -> HomeUiState.GetHighestPriceSuccess(
                            it.body.items ?: mutableListOf()
                        )
                        isLowestPrice -> HomeUiState.GetLowestPriceSuccess(
                            it.body.items ?: mutableListOf()
                        )
                        else -> HomeUiState.Error
                    }
                }, apiError = {
                    uiStateValue.value = HomeUiState.Error
                }, showDialog = false
            )
        }
    }
}
