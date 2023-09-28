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
import com.example.realestateapp.ui.base.UiEffect
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

sealed class HomeUiEffect : UiEffect() {
    object InitView : HomeUiEffect()

    object Loading : HomeUiEffect()

    object Error : HomeUiEffect()

    object Done : HomeUiEffect()

    object DoneSignInBackground : HomeUiEffect()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : HomeUiEffect()

    data class GetLatestSuccess(val data: MutableList<RealEstateList>) : HomeUiEffect()

    data class GetMostViewSuccess(val data: MutableList<RealEstateList>) : HomeUiEffect()

    data class GetHighestPriceSuccess(val data: MutableList<RealEstateList>) : HomeUiEffect()

    data class GetLowestPriceSuccess(val data: MutableList<RealEstateList>) : HomeUiEffect()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application, appRepository: AppRepository
) : BaseViewModel<HomeUiEffect>(appRepository) {
    override var uiEffectValue: MutableStateFlow<UiEffect> = MutableStateFlow(HomeUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var realEstatesLatest = mutableStateListOf<RealEstateList>()
    internal var realEstatesMostView = mutableStateListOf<RealEstateList>()
    internal var realEstatesHighestPrice = mutableStateListOf<RealEstateList>()
    internal var realEstatesLowestPrice = mutableStateListOf<RealEstateList>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    internal fun backgroundSignIn() {
        uiEffectValue.value = HomeUiEffect.Loading
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.readStoreLauncher(onReadSuccess = { email, pass ->
                viewModelScope.launch {
                    callAPIOnThread(
                        requests = mutableListOf(
                            appRepository.signIn(
                                email = email, password = pass
                            )
                        ), apiSuccess = {
                            setUser(it.body)
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
                            uiEffectValue.value = HomeUiEffect.DoneSignInBackground
                        }, showDialog = false
                    )
                }
            }, onErrorAction = {
                viewModelScope.launch {
                    uiEffectValue.value = HomeUiEffect.DoneSignInBackground
                }
            })
        }
    }

    internal fun getTypes() {
        uiEffectValue.value = HomeUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(requests = mutableListOf(
                appRepository.getTypes(),
            ), apiSuccess = {
                uiEffectValue.value = HomeUiEffect.GetTypesSuccess(it.body)
            }, apiError = {
                uiEffectValue.value = HomeUiEffect.Error
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
        uiEffectValue.value = HomeUiEffect.Loading
        viewModelScope.launch {
            val typePropertyIds = typesData.filter { it.isSelected }.map { it.id }
            callAPIOnThread(
                requests = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 10,
                        isMostView = isMostView,
                        typePropertyIds = typePropertyIds.toMutableList(),
                        isLatest = isLatest,
                        isHighestPrice = isHighestPrice,
                        isLowestPrice = isLowestPrice,
                        userId = getUser().value?.id ?: 0
                    )
                ), apiSuccess = {
                    uiEffectValue.value = when {
                        isLatest -> HomeUiEffect.GetLatestSuccess(it.body.items ?: mutableListOf())
                        isMostView -> HomeUiEffect.GetMostViewSuccess(
                            it.body.items ?: mutableListOf()
                        )

                        isHighestPrice -> HomeUiEffect.GetHighestPriceSuccess(
                            it.body.items ?: mutableListOf()
                        )

                        isLowestPrice -> HomeUiEffect.GetLowestPriceSuccess(
                            it.body.items ?: mutableListOf()
                        )

                        else -> HomeUiEffect.Error
                    }
                }, apiError = {
                    uiEffectValue.value = HomeUiEffect.Error
                }, showDialog = false,
                isShowLoading = showLoading
            )
        }
    }
}
