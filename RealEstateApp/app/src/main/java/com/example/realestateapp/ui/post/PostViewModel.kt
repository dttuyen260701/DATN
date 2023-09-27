package com.example.realestateapp.ui.post

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.enums.Direction
import com.example.realestateapp.data.enums.Juridical
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import com.example.realestateapp.ui.home.realestatedetail.RealEstateDetailUiEffect
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.ComboOptions.BRONZE
import com.example.realestateapp.util.Constants.ComboOptions.GOLD
import com.example.realestateapp.util.Constants.ComboOptions.SILVER
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_POST
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
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

sealed class PostUiEffect : UiEffect() {
    object InitView : PostUiEffect()

    object Loading : PostUiEffect()

    object Error : PostUiEffect()

    object Done : PostUiEffect()

    object GetComboOptionsDone : PostUiEffect()

    data class SubmitPostSuccess(val data: Boolean) : PostUiEffect()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : PostUiEffect()

    data class GetSearchDataSuccess(val data: MutableList<RealEstateList>) : PostUiEffect()

    data class GetPredictPriceSuccess(
        val data: Float,
        val isForSubmit: Boolean
    ) : PostUiEffect()

    data class GetRealEstateDetailSuccess(val data: RealEstateDetail) : PostUiEffect()
}

@HiltViewModel
class PostViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<PostUiEffect>(appRepository) {
    override var uiEffectValue: MutableStateFlow<UiEffect> = MutableStateFlow(PostUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()
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
    internal var comboOptionChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var comboOptionsBronze = mutableStateListOf<ItemChoose>()
    internal var comboOptionsSilver = mutableStateListOf<ItemChoose>()
    internal var comboOptionsGold = mutableStateListOf<ItemChoose>()
    internal var postId = mutableStateOf(DEFAULT_ID_POST)
    internal var postStatus = mutableStateOf(0)
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
        comboOptionChosen.value = DEFAULT_ITEM_CHOSEN
    }

    internal fun updatePost() {
        viewModelScope.launch(Dispatchers.IO) {
            if (postId.value != DEFAULT_ID_POST) {
                PickAddressViewModel.run {
                    callAPIOnThread(
                        response = mutableListOf(
                            appRepository.updatePost(
                                idPost = postId.value,
                                title = title.value,
                                description = description.value,
                                price = price.value.toDouble() * 1_000_000,
                                width = width.value.toDouble(),
                                acreage = square.value.toDouble(),
                                parkingSpace = isHaveCarParking.value,
                                streetInFront = streetInFront.value.toDouble(),
                                length = length.value.toDouble(),
                                bedroomNumber = bedroom.value.toInt(),
                                diningRoom = if (isHaveDiningRoom.value) 1 else 0,
                                kitchen = if (isHaveKitchenRoom.value) 1 else 0,
                                rooftop = isHaveRooftop.value,
                                floorNumber = floor.value.toInt(),
                                legalTypeId = juridicalChosen.value.id,
                                detailAddress = detailStreet.value,
                                districtId = districtChosen.value.id,
                                wardId = wardChosen.value.id,
                                streetId = streetChosen.value.id,
                                longitude = longitude,
                                latitude = latitude,
                                listNewImages = images.map { it.url }.toMutableList(),
                                propertyTypeId = typeChosen.value.id,
                                comboOptionId = comboOptionChosen.value.id
                            )
                        ),
                        apiSuccess = {
                            uiEffectValue.value = PostUiEffect.SubmitPostSuccess(it.isSuccess)
                        },
                        apiError = {
                            uiEffectValue.value = PostUiEffect.SubmitPostSuccess(false)
                        }
                    )
                }
            }
        }
    }

    internal fun createPost() {
        viewModelScope.launch(Dispatchers.IO) {
            getUser().value?.id?.let {
                PickAddressViewModel.run {

                    callAPIOnThread(
                        response = mutableListOf(
                            appRepository.createPost(
                                title = title.value,
                                description = description.value,
                                ownerId = it,
                                price = price.value.toDouble() * 1_000_000,
                                suggestedPrice = priceSuggest.value.toDouble() * 1_000_000,
                                directionId = directionChosen.value.id,
                                width = width.value.toDouble(),
                                acreage = square.value.toDouble(),
                                parkingSpace = isHaveCarParking.value,
                                streetInFront = streetInFront.value.toDouble(),
                                length = length.value.toDouble(),
                                bedroomNumber = bedroom.value.toInt(),
                                kitchen = if (isHaveKitchenRoom.value) 1 else 0,
                                rooftop = isHaveRooftop.value,
                                floorNumber = floor.value.toInt(),
                                diningRoom = if (isHaveDiningRoom.value) 1 else 0,
                                legalTypeId = juridicalChosen.value.id,
                                isOwner = true,
                                detail = detailStreet.value,
                                provinceId = 1,
                                districtId = districtChosen.value.id,
                                wardId = wardChosen.value.id,
                                streetId = streetChosen.value.id,
                                longitude = longitude,
                                latitude = latitude,
                                images = images.map { it.url }.toMutableList(),
                                propertyTypeId = typeChosen.value.id,
                                cluster = cluster,
                                comboOptionId = comboOptionChosen.value.id
                            )
                        ),
                        apiSuccess = {
                            uiEffectValue.value = PostUiEffect.SubmitPostSuccess(it.isSuccess)
                        },
                        apiError = {
                            uiEffectValue.value = PostUiEffect.SubmitPostSuccess(false)
                        }
                    )
                }
            }
        }
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

    internal fun onComboOptionChoice(comboOption: ItemChoose) {
        var oldIndex = comboOptionsBronze.indexOfFirst { it.isSelected }
        if (oldIndex != -1) {
            comboOptionsBronze[oldIndex] = comboOptionsBronze[oldIndex].copy(isSelected = false)
        }
        oldIndex = comboOptionsSilver.indexOfFirst { it.isSelected }
        if (oldIndex != -1) {
            comboOptionsSilver[oldIndex] = comboOptionsSilver[oldIndex].copy(isSelected = false)
        }
        oldIndex = comboOptionsGold.indexOfFirst { it.isSelected }
        if (oldIndex != -1) {
            comboOptionsGold[oldIndex] = comboOptionsGold[oldIndex].copy(isSelected = false)
        }

        var newIndex = comboOptionsBronze.indexOfFirst { it.id == comboOption.id }
        if (newIndex == -1) {
            newIndex = comboOptionsSilver.indexOfFirst { it.id == comboOption.id }
            if (newIndex == -1) {
                newIndex = comboOptionsGold.indexOfFirst { it.id == comboOption.id }
                comboOptionsGold[newIndex] = comboOptionsGold[newIndex].copy(isSelected = true)
            } else {
                comboOptionsSilver[newIndex] = comboOptionsSilver[newIndex].copy(isSelected = true)
            }
        } else comboOptionsBronze[newIndex] = comboOptionsBronze[newIndex].copy(isSelected = true)
    }

    internal fun getComboOptions(showLoading: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getComboOptions()
                ),
                apiSuccess = { result ->
                    result.body.map {
                        val item = ItemChoose(
                            id = it.id,
                            name = it.dayNumber.toString(),
                            score = it.amount.toInt(),
                            isSelected = (it.id == comboOptionChosen.value.id)
                        )
                        if (it.id == comboOptionChosen.value.id)
                            comboOptionChosen.value = item
                        when (it.comboOptionTypeId) {
                            BRONZE -> {
                                comboOptionsBronze.add(item)
                            }
                            SILVER -> {
                                comboOptionsSilver.add(item)
                            }
                            GOLD -> {
                                comboOptionsGold.add(item)
                            }
                            else -> {}
                        }
                    }
                    uiEffectValue.value = PostUiEffect.GetComboOptionsDone
                },
                apiError = {

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
                    uiEffectValue.value =
                        PostUiEffect.GetRealEstateDetailSuccess(response.body)
                },
                apiError = {
                    uiEffectValue.value = RealEstateDetailUiEffect.Error
                }
            )
        }
    }

    internal fun getPredictPrice(
        isForSubmit: Boolean = false
    ) {
        uiEffectValue.value = PostUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
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
                    uiEffectValue.value = PostUiEffect.GetPredictPriceSuccess(it.body.result, isForSubmit)
                },
                apiError = {

                }
            )
        }
    }

    internal fun getTypes(onDone: () -> Unit) {
        uiEffectValue.value = PostUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getTypes(),
                ), apiSuccess = {
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == typeChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiEffectValue.value = PostUiEffect.GetTypesSuccess(it.body)
                }, apiError = {
                    uiEffectValue.value = PostUiEffect.Error
                }, onDoneCallApi = onDone, showDialog = false
            )
        }
    }

    internal fun getPosts(
        isMyRecords: Boolean,
        filter: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uiEffectValue.value = PostUiEffect.Loading
            callAPIOnThread(
                response = mutableListOf(
                    if (isMyRecords) {
                        appRepository.getPostCreatedByUser(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter
                        )
                    } else {
                        appRepository.getPostSaved(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter
                        )
                    }
                ), apiSuccess = {
                    uiEffectValue.value =
                        PostUiEffect.GetSearchDataSuccess(it.body.items ?: mutableListOf())
                }, apiError = {
                    uiEffectValue.value = PostUiEffect.Error
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
                            juridical.value.isSelected =
                                (juridical.value.id == juridicalChosen.value.id)
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
                            direction.value.isSelected =
                                (direction.value.id == directionChosen.value.id)
                            direction.value
                        }.toMutableList()
                    )
                }
            }
        }
        onDone()
    }
}
