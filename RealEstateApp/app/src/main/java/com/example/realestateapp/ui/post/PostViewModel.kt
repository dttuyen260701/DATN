package com.example.realestateapp.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.enums.Direction
import com.example.realestateapp.data.enums.Juridical
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.ui.home.realestatedetail.RealEstateDetailUiState
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class PostUiState : UiState() {
    object InitView : PostUiState()

    object Loading : PostUiState()

    object Error : PostUiState()

    object Done : PostUiState()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : PostUiState()

    data class GetSearchDataSuccess(val data: MutableList<RealEstateList>) : PostUiState()

    data class GetPredictPriceSuccess(val data: Float) : PostUiState()

    data class GetRealEstateDetailSuccess(val data: RealEstateDetail) : PostUiState()
}

@HiltViewModel
class PostViewModel @Inject constructor(

) : BaseViewModel<PostUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PostUiState.InitView)
    internal var firstClick = mutableStateOf(true)
    internal var filter = mutableStateOf("")
    internal var isNavigateAnotherScr = mutableStateOf(true)
    internal var searchResult = mutableStateListOf<RealEstateList>()
    internal var detailAddress = mutableStateOf("")
    internal var typeChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var juridicalChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var juridicalOptions = mutableStateListOf<ItemChoose>()
    internal var directionChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var directionOptions = mutableStateListOf<ItemChoose>()
    internal var square = mutableStateOf("")
    internal var floor = mutableStateOf("")
    internal var bedroom = mutableStateOf("")
    internal var streetInFront = mutableStateOf("")
    internal var width = mutableStateOf("")
    internal var length = mutableStateOf("")
    internal var isHaveCarParking = mutableStateOf(false)
    internal var isHaveRooftop = mutableStateOf(false)
    internal var isHaveKitchenRoom = mutableStateOf(false)
    internal var isHaveDiningRoom = mutableStateOf(false)
    internal var title = mutableStateOf("")
    internal var description = mutableStateOf("")
    internal var isUpLoading = mutableStateOf(false)
    internal var images = mutableStateListOf<Image>()
    internal var priceSuggest = mutableStateOf("")
    internal var price = mutableStateOf("")
    private var cluster = 0

    internal fun resetData() {
        firstClick.value = true
        detailAddress.value = ""
        typeChosen.value = DEFAULT_ITEM_CHOSEN
        juridicalChosen.value = DEFAULT_ITEM_CHOSEN
        directionChosen.value = DEFAULT_ITEM_CHOSEN
        square.value = ""
        floor.value = ""
        bedroom.value = ""
        streetInFront.value = ""
        width.value = ""
        length.value = ""
        isHaveCarParking.value = false
        isHaveRooftop.value = false
        isHaveKitchenRoom.value = false
        isHaveDiningRoom.value = false
        title.value = ""
        description.value = ""
        images.clear()
        priceSuggest.value = ""
        price.value = ""
    }

    internal fun isValidateData(): Boolean =
        detailAddress.value.trim().isNotEmpty()
                && typeChosen.value != DEFAULT_ITEM_CHOSEN
                && juridicalChosen.value != DEFAULT_ITEM_CHOSEN
                && directionChosen.value != DEFAULT_ITEM_CHOSEN
                && square.value.trim().isNotEmpty()
                && floor.value.trim().isNotEmpty()
                && bedroom.value.trim().isNotEmpty()
                && streetInFront.value.trim().isNotEmpty()
                && width.value.trim().isNotEmpty()
                && length.value.trim().isNotEmpty()
                && PickAddressViewModel.districtChosen.value != DEFAULT_ITEM_CHOSEN
                && PickAddressViewModel.wardChosen.value != DEFAULT_ITEM_CHOSEN
                && PickAddressViewModel.streetChosen.value != DEFAULT_ITEM_CHOSEN

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
                apiSuccess = { response ->
                    uiState.value =
                        PostUiState.GetRealEstateDetailSuccess(response.body)
                },
                apiError = {
                    uiState.value = RealEstateDetailUiState.Error
                }
            )
        }
    }

    internal fun getPredictPrice() {
        uiState.value = PostUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPredictPrice(
                        bedRoom = bedroom.value.toInt(),
                        width = width.value.toFloat(),
                        acreage = square.value.toFloat(),
                        length = square.value.toFloat(),
                        floorNumber = floor.value.toInt(),
                        kitchen = if (isHaveKitchenRoom.value) 1 else 0,
                        diningRoom = if (isHaveDiningRoom.value) 1 else 0,
                        propertyTypeId = typeChosen.value.id,
                        legalTypeId = juridicalChosen.value.id,
                        carParking = isHaveCarParking.value,
                        directionId = directionChosen.value.id,
                        rooftop = isHaveRooftop.value,
                        districtId = PickAddressViewModel.districtChosen.value.id,
                        wardId = PickAddressViewModel.wardChosen.value.id,
                        streetId = PickAddressViewModel.streetChosen.value.id,
                        widthRoad = streetInFront.value.toFloat()
                    )
                ),
                apiSuccess = {
                    cluster = it.body.cluster
                    uiState.value = PostUiState.GetPredictPriceSuccess(it.body.result)
                },
                apiError = {

                }
            )
        }
    }

    internal fun getTypes(onDone: () -> Unit) {
        uiState.value = PostUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getTypes(showLoading = false),
                ), apiSuccess = {
                    if (it.body.indexOf(typeChosen.value) != -1) {
                        it.body[it.body.indexOf(typeChosen.value)].isSelected = true
                    }
                    uiState.value = PostUiState.GetTypesSuccess(it.body)
                }, apiError = {
                    uiState.value = PostUiState.Error
                }, onDoneCallApi = onDone, showDialog = false
            )
        }
    }

    internal fun getPosts(
        isMyRecords: Boolean,
        filter: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = PostUiState.Loading
            callAPIOnThread(
                funCallApis = mutableListOf(
                    if (isMyRecords) {
                        appRepository.getPostCreatedByUser(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter,
                            showLoading = false
                        )
                    } else {
                        appRepository.getPostSaved(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter,
                            showLoading = false
                        )
                    }
                ), apiSuccess = {
                    uiState.value =
                        PostUiState.GetSearchDataSuccess(it.body.items ?: mutableListOf())
                }, apiError = {
                    uiState.value = PostUiState.Error
                }
            )
        }

    }

    internal fun getDataChoice(key: String, onDone: () -> Unit) {
        when (key) {
            FIELD_JURIDICAL -> {
                juridicalOptions.run {
                    clear()
                    addAll(
                        Juridical.values().map { juridical ->
                            juridical.value.isSelected = (juridical.value == juridicalChosen.value)
                            juridical.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_DIRECTION -> {
                directionOptions.run {
                    clear()
                    addAll(
                        Direction.values().map { direction ->
                            direction.value.isSelected = (direction.value == directionChosen.value)
                            direction.value
                        }.toMutableList()
                    )
                }
            }
        }
        onDone()
    }
}
