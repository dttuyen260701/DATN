package com.example.realestateapp.ui.home

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.extension.readStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val application: Application
) : BaseViewModel<HomeUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(HomeUiState.InitView)
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var realEstatesLatest = mutableStateListOf<RealEstateList>()
    internal var realEstatesMostView = mutableStateListOf<RealEstateList>()
    internal var realEstatesHighestPrice = mutableStateListOf<RealEstateList>()
    internal var realEstatesLowestPrice = mutableStateListOf<RealEstateList>()

    internal fun backgroundSignIn() {
        uiState.value = HomeUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.readStoreLauncher(onReadSuccess = { email, pass ->
                viewModelScope.launch {
                    callAPIOnThread(
                        funCallApis = mutableListOf(
                            appRepository.signIn(
                                email = email, password = pass, showLoading = false
                            )
                        ), apiSuccess = {
                            getUser().value = it.body
                            AuthenticationObject.token = it.body?.token ?: ""
                        }, onDoneCallApi = {
                            uiState.value = HomeUiState.DoneSignInBackground
                        }, showDialog = false
                    )
                }
            }, onErrorAction = {
                viewModelScope.launch {
                    uiState.value = HomeUiState.DoneSignInBackground
                }
            })
        }
    }

    internal fun getTypes() {
        uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(funCallApis = mutableListOf(
                appRepository.getTypes(showLoading = false),
            ), apiSuccess = {
                uiState.value = HomeUiState.GetTypesSuccess(it.body)
            }, apiError = {
                uiState.value = HomeUiState.Error
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
        uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            val typePropertyIds = typesData.filter { it.isSelected }.map { it.id }
            callAPIOnThread(funCallApis = mutableListOf(
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
                uiState.value = when {
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
                uiState.value = HomeUiState.Error
            }, showDialog = false
            )
        }
    }
}
