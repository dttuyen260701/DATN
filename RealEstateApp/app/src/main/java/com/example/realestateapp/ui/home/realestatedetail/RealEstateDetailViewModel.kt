package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants.DefaultValue.REAL_ESTATE_DEFAULT
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

    data class GetRealEstateDetailSuccess(val data: RealEstateDetail) :
        RealEstateDetailUiState()
}

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<RealEstateDetailUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(RealEstateDetailUiState.InitView)
    internal var realEstateItem: MutableState<RealEstateDetail> = mutableStateOf(REAL_ESTATE_DEFAULT)
    internal var realEstatesSamePrice = mutableStateListOf<RealEstateList>()
    internal var realEstatesCluster = mutableStateListOf<RealEstateList>()

    internal fun getRealEstateDetail(
        realEstateId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPostDetailById(
                        idPost = realEstateId.toString(),
                        idUser = getUser().value?.id?.toString() ?: ""
                    )
                ),
                apiSuccess = {
                    uiState.value = RealEstateDetailUiState.GetRealEstateDetailSuccess(it.body)
                },
                apiError = {
                    uiState.value = RealEstateDetailUiState.Error
                }
            )
        }
    }
}
