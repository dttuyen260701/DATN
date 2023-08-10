package com.example.realestateapp.ui.home.realestatedetail

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.R
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.view.RealEstateProperty
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants.DefaultValue.REAL_ESTATE_DEFAULT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/16/2023.
 */

sealed class RealEstateDetailUiState : UiState() {
    object InitView : RealEstateDetailUiState()

    object Error : RealEstateDetailUiState()

    object Done : RealEstateDetailUiState()

    data class GetRealEstateDetailSuccess(val data: RealEstateDetail) :
        RealEstateDetailUiState()

    data class GetSamePriceSuccess(val data: MutableList<RealEstateList>) :
        RealEstateDetailUiState()

    data class GetClusterSuccess(val data: MutableList<RealEstateList>) :
        RealEstateDetailUiState()
}

@HiltViewModel
class RealEstateDetailViewModel @Inject constructor(
    private val application: Application, appRepository: AppRepository
) : BaseViewModel<RealEstateDetailUiState>(appRepository) {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(RealEstateDetailUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var realEstateItem: MutableState<RealEstateDetail> =
        mutableStateOf(REAL_ESTATE_DEFAULT)
    internal val realEstateProperty = mutableStateListOf<RealEstateProperty>()
    internal val realEstatesSamePrice = mutableStateListOf<RealEstateList>()
    internal val realEstatesCluster = mutableStateListOf<RealEstateList>()
    internal var firstClick = mutableStateOf(true)
    internal var description = mutableStateOf("")

    internal fun createReport(idPost: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getUser().value?.id?.let {
                callAPIOnThread(
                    response = mutableListOf(
                        appRepository.createReport(
                            postId = idPost,
                            reporterId = it,
                            description = description.value
                        )
                    ),
                    apiSuccess = {
                        uiStateValue.value = RealEstateDetailUiState.Done
                    },
                    apiError = {
                        uiStateValue.value = RealEstateDetailUiState.Error
                    }
                )
            }
        }
    }

    internal fun updateSavedPost(
        idPost: Int,
        onSuccess: (Int) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.updateSavePost(
                        idPost = idPost,
                        idUser = getUser().value?.id ?: 0
                    )
                ),
                apiSuccess = {
                    onSuccess(idPost)
                }
            )
        }
    }

    internal fun getRealEstateDetail(
        realEstateId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getPostDetailById(
                        idPost = realEstateId.toString(),
                        idUser = getUser().value?.id?.toString() ?: ""
                    )
                ),
                apiSuccess = { response ->
                    uiStateValue.value =
                        RealEstateDetailUiState.GetRealEstateDetailSuccess(response.body)

                    response.body.run {
                        realEstateProperty.run {
                            add(
                                RealEstateProperty(
                                    title = propertyTypeName,
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.RealEstateType
                                    )
                                )
                            )
                            add(
                                RealEstateProperty(
                                    title = legalName,
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.Legal
                                    )
                                )
                            )
                            add(
                                RealEstateProperty(
                                    title = application.getString(
                                        R.string.squareUnit,
                                        square.toString()
                                    ),
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.Square
                                    )
                                )
                            )
                            add(
                                RealEstateProperty(
                                    title = nameDirection,
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.Compass
                                    )
                                )
                            )
                            floors?.let {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.floorsTitle,
                                            it.toString()
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.Floors
                                        )
                                    )
                                )
                            }
                            streetInFront?.let {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.unitMTitle,
                                            it.toString()
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.StreetInFront
                                        )
                                    )
                                )
                            }
                            add(
                                RealEstateProperty(
                                    title = application.getString(
                                        R.string.unitMTitle,
                                        width.toString()
                                    ),
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.Width
                                    )
                                )
                            )
                            add(
                                RealEstateProperty(
                                    title = application.getString(
                                        R.string.unitMTitle,
                                        length.toString()
                                    ),
                                    leadingIcon = AppIcon.DrawableResourceIcon(
                                        RealEstateIcon.Length
                                    )
                                )
                            )
                            bedrooms?.let {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.numberBedRoomTitle,
                                            it.toString()
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.Bed
                                        )
                                    )
                                )
                            }
                            if (kitchen == 1) {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.kitchenTitle
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.Kitchen
                                        )
                                    )
                                )
                            }
                            if (diningRoom == 1) {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.diningRoomTitle
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.DiningRoom
                                        )
                                    )
                                )
                            }
                            if (rooftop == true) {
                                add(
                                    RealEstateProperty(
                                        title = application.getString(
                                            R.string.rooftopTitle
                                        ),
                                        leadingIcon = AppIcon.DrawableResourceIcon(
                                            RealEstateIcon.Rooftop
                                        )
                                    )
                                )
                            }
                        }
                    }
                },
                apiError = {
                    uiStateValue.value = RealEstateDetailUiState.Error
                }
            )
        }
    }

    internal fun getRealEstatesSamePrice(
        realEstateId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getPostSamePrice(
                        idPost = realEstateId.toString(),
                        idUser = getUser().value?.id?.toString() ?: ""
                    )
                ),
                apiSuccess = {
                    uiStateValue.value =
                        RealEstateDetailUiState.GetSamePriceSuccess(it.body)
                },
                apiError = {
                    uiStateValue.value = RealEstateDetailUiState.Error
                }
            )
        }
    }

    internal fun getRealEstatesCluster(
        realEstateId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getPostSameCluster(
                        idPost = realEstateId.toString(),
                        idUser = getUser().value?.id?.toString() ?: ""
                    )
                ),
                apiSuccess = {
                    uiStateValue.value =
                        RealEstateDetailUiState.GetClusterSuccess(it.body)
                },
                apiError = {
                    uiStateValue.value = RealEstateDetailUiState.Error
                }
            )
        }
    }
}
