package com.example.realestateapp.ui.pickaddress

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/22/2023.
 */

sealed class PickAddressUiState : UiState() {
    object InitView : PickAddressUiState()

    object Loading : PickAddressUiState()

    object Error : PickAddressUiState()

    object Done : PickAddressUiState()

    data class GetDistrictSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()

    data class GetWardSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()

    data class GetStreetSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()
}

@HiltViewModel
class PickAddressViewModel @Inject constructor(
) : BaseViewModel<PickAddressUiState>() {
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

    override var uiState: MutableState<UiState> = mutableStateOf(PickAddressUiState.InitView)
    internal var districtsData = mutableStateListOf<ItemChoose>()
    internal var wardsData = mutableStateListOf<ItemChoose>()
    internal var streetsData = mutableStateListOf<ItemChoose>()

    internal fun getDistricts(filter: String, onDoneApi: () -> Unit) {
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getDistricts(),
                ), apiSuccess = {
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == districtChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiState.value = PickAddressUiState.GetDistrictSuccess(it.body)
                }, apiError = {
                    uiState.value = PickAddressUiState.Error
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
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getWards(
                        districtId = districtChosen.value.id.toString(),
                        showLoading = showLoading
                    ),
                ), apiSuccess = {
                    onApiSuccess(it.body)
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == wardChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiState.value = PickAddressUiState.GetWardSuccess(it.body)
                }, apiError = {
                    uiState.value = PickAddressUiState.Error
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
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getStreets(
                        districtId = districtChosen.value.id.toString(),
                        filter = filter,
                        showLoading = showLoading
                    ),
                ), apiSuccess = {
                    onApiSuccess(it.body)
                    val indexSelected =
                        it.body.indexOfFirst { item -> item.id == districtChosen.value.id }
                    if (indexSelected != -1) {
                        it.body[indexSelected].isSelected = true
                    }
                    uiState.value = PickAddressUiState.GetStreetSuccess(it.body)
                }, apiError = {
                    uiState.value = PickAddressUiState.Error
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