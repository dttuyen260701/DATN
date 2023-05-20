package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/16/2023.
 */

sealed class RealEstateDetailUiState : UiState() {
    object InitView : RealEstateDetailUiState()

    object Error : RealEstateDetailUiState()

    data class GetRealEstateDetailSuccess(val data: String) :
        RealEstateDetailUiState()

    data class GetSamePriceSuccess(val data: MutableList<RealEstateList>) :
        RealEstateDetailUiState()

    data class GetClusterSuccess(val data: MutableList<RealEstateList>) : RealEstateDetailUiState()
}

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<RealEstateDetailUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(RealEstateDetailUiState.InitView)
    internal var realEstatesSamePrice = mutableStateListOf<RealEstateList>()
    internal var realEstatesCluster = mutableStateListOf<RealEstateList>()

    internal fun getRealEstateDetail(
        realEstateId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 10,
                        isMostView = false,
                        typePropertyIds = mutableListOf(),
                        isHighestPrice = false,
                        isLowestPrice = false,
                        isLatest = true,
                        userId = getUser().value?.id ?: 0,
                        showLoading = true
                    )
                ),
                apiSuccess = {
                    uiState.value = RealEstateDetailUiState.GetRealEstateDetailSuccess("")
                },
                apiError = {
                    uiState.value = RealEstateDetailUiState.Error
                }
            )
        }
    }

    internal fun getRealEstatesSamePrice(
        price: Float,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 10,
                        isMostView = false,
                        typePropertyIds = mutableListOf(),
                        isHighestPrice = false,
                        isLowestPrice = false,
                        isLatest = true,
                        userId = getUser().value?.id ?: 0,
                        showLoading = true
                    )
                ),
                apiSuccess = {
                    uiState.value =
                        RealEstateDetailUiState.GetSamePriceSuccess(
                            it.body.items ?: mutableListOf()
                        )
                },
                apiError = {
                    uiState.value = RealEstateDetailUiState.Error
                }
            )
        }
    }

    internal fun getRealEstatesCluster(
        cluster: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 10,
                        isMostView = false,
                        typePropertyIds = mutableListOf(),
                        isHighestPrice = false,
                        isLowestPrice = false,
                        isLatest = true,
                        userId = getUser().value?.id ?: 0,
                        showLoading = true
                    )
                ),
                apiSuccess = {
                    uiState.value =
                        RealEstateDetailUiState.GetClusterSuccess(it.body.items ?: mutableListOf())
                },
                apiError = {
                    uiState.value = RealEstateDetailUiState.Error
                }
            )
        }
    }
}
