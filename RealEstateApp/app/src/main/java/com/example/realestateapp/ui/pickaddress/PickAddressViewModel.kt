package com.example.realestateapp.ui.pickaddress

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/22/2023.
 */

sealed class PickAddressUiEffect : UiEffect() {
    object InitView : PickAddressUiEffect()

    object Loading : PickAddressUiEffect()

    object Error : PickAddressUiEffect()

    object Done : PickAddressUiEffect()

    data class GetDistrictSuccess(val data: MutableList<ItemChoose>) : PickAddressUiEffect()

    data class GetWardSuccess(val data: MutableList<ItemChoose>) : PickAddressUiEffect()

    data class GetStreetSuccess(val data: MutableList<ItemChoose>) : PickAddressUiEffect()
}

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<PickAddressUiEffect>(appRepository) {
    companion object {
        internal var districtChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set
        internal var wardChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set
        internal var streetChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set
        internal var longitude: Double = 0.0
        internal var latitude: Double = 0.0
        internal var detailStreet = mutableStateOf("")

        internal fun clearDataChosen() {
            districtChosen.value = DEFAULT_ITEM_CHOSEN
            wardChosen.value = DEFAULT_ITEM_CHOSEN
            streetChosen.value = DEFAULT_ITEM_CHOSEN
            longitude = 0.0
            latitude = 0.0
        }
    }

    override var uiEffectValue: MutableStateFlow<UiEffect> =
        MutableStateFlow(PickAddressUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()
    internal var districtsData = mutableStateListOf<ItemChoose>()
    internal var wardsData = mutableStateListOf<ItemChoose>()
    internal var streetsData = mutableStateListOf<ItemChoose>()

    internal fun getDistricts(filter: String, onDoneApi: () -> Unit) {
        uiEffectValue.value = PickAddressUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(
                requests = mutableListOf(
                    appRepository.getDistricts(),
                ), apiSuccess = {
                    val indexSelected =
                    it.body.indexOfFirst { item -> item.id == districtChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiEffectValue.value = PickAddressUiEffect.GetDistrictSuccess(it.body)
                }, apiError = {
                    uiEffectValue.value = PickAddressUiEffect.Error
                },
                onDoneCallApi = onDoneApi,
                showDialog = false
            )
        }
    }

    internal fun getWards(
        onApiSuccess: (MutableList<ItemChoose>) -> Unit = {},
        showLoading: Boolean = false,
        onDoneApi: () -> Unit
    ) {
        uiEffectValue.value = PickAddressUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(
                requests = mutableListOf(
                    appRepository.getWards(
                        districtId = districtChosen.value.id.toString()
                    ),
                ), apiSuccess = {
                    onApiSuccess(it.body)
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == wardChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiEffectValue.value = PickAddressUiEffect.GetWardSuccess(it.body)
                }, apiError = {
                    uiEffectValue.value = PickAddressUiEffect.Error
                },
                onDoneCallApi = onDoneApi,
                showDialog = false
            )
        }
    }

    internal fun getStreets(
        filter: String,
        showLoading: Boolean = false,
        onApiSuccess: (MutableList<ItemChoose>) -> Unit = {},
        onDoneApi: () -> Unit
    ) {
        uiEffectValue.value = PickAddressUiEffect.Loading
        viewModelScope.launch {
            callAPIOnThread(
                requests = mutableListOf(
                    appRepository.getStreets(
                        districtId = districtChosen.value.id.toString(),
                        filter = filter
                    ),
                ), apiSuccess = {
                    onApiSuccess(it.body)
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == districtChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiEffectValue.value = PickAddressUiEffect.GetStreetSuccess(it.body)
                }, apiError = {
                    uiEffectValue.value = PickAddressUiEffect.Error
                },
                onDoneCallApi = onDoneApi,
                showDialog = false
            )
        }
    }

    internal fun updateChoiceData(
        nameDistrict: String?,
        nameWard: String?,
        nameStreet: String?
    ) {
        nameDistrict?.let { district ->
            districtChosen.value =
                districtsData.firstOrNull { it.name.contains(district, true) }
                    ?: DEFAULT_ITEM_CHOSEN
            if (districtChosen.value != DEFAULT_ITEM_CHOSEN) {
                nameWard?.let {
                    getWards(
                        showLoading = true,
                        onApiSuccess = { data ->
                            wardChosen.value =
                                data.firstOrNull {
                                    it.name.contains(
                                        nameWard, true
                                    )
                                } ?: ItemChoose(
                                    id = -1,
                                    name = nameWard,
                                    score = 1
                                )
                        }
                    ) {}
                }
                nameStreet?.let {
                    getStreets(
                        nameStreet.replace("Đường ", ""),
                        showLoading = true,
                        onApiSuccess = { data ->
                            streetChosen.value =
                                data.firstOrNull {
                                    it.name.contains(
                                        nameStreet.replace(
                                            "Đường ",
                                            ""
                                        ),
                                        true
                                    )
                                } ?: ItemChoose(
                                    id = -1,
                                    name = nameStreet,
                                    score = 1
                                )
                        }
                    ) {}
                }
            }
        }
    }

    internal fun onChoiceData(itemChoose: ItemChoose, key: String) {
        when (key) {
            Constants.DefaultField.FIELD_DISTRICT -> {
                districtChosen.value = itemChoose.copy(isSelected = false)
                wardChosen.value = DEFAULT_ITEM_CHOSEN
                streetChosen.value = DEFAULT_ITEM_CHOSEN
            }

            Constants.DefaultField.FIELD_WARD -> {
                wardChosen.value = itemChoose.copy(isSelected = false)
            }

            Constants.DefaultField.FIELD_STREET -> {
                streetChosen.value = itemChoose.copy(isSelected = false)
                detailStreet.value = ""
            }

            else -> {}
        }
    }
}