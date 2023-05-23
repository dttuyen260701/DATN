package com.example.realestateapp.ui.pickaddress

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.repository.AppRepository
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

    data class GetDistrictSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()

    data class GetWardSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()

    data class GetStreetSuccess(val data: MutableList<ItemChoose>) : PickAddressUiState()
}

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<PickAddressUiState>() {
    companion object {
        internal var districtChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set
        internal var wardChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set
        internal var streetChosen: MutableState<ItemChoose> = mutableStateOf(DEFAULT_ITEM_CHOSEN)
            private set

        internal fun clearDataChosen() {
            districtChosen.value = DEFAULT_ITEM_CHOSEN
            wardChosen.value = DEFAULT_ITEM_CHOSEN
            streetChosen.value = DEFAULT_ITEM_CHOSEN
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
                    if (it.body.indexOf(districtChosen.value) != -1) {
                        it.body[it.body.indexOf(districtChosen.value)].isSelected = true
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

    internal fun getWards(filter: String, onDoneApi: () -> Unit) {
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getWards(districtId = districtChosen.value.id.toString()),
                ), apiSuccess = {
                    if (it.body.indexOf(wardChosen.value) != -1) {
                        it.body[it.body.indexOf(wardChosen.value)].isSelected = true
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

    internal fun getStreets(filter: String, onDoneApi: () -> Unit) {
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getStreets(filter = filter),
                ), apiSuccess = {
                    if (it.body.indexOf(streetChosen.value) != -1) {
                        it.body[it.body.indexOf(streetChosen.value)].isSelected = true
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

    internal fun onChoiceData(itemChoose: ItemChoose, key: String) {
        when (key) {
            Constants.DefaultField.FIELD_DISTRICT -> {
                districtChosen.value = itemChoose
            }
            Constants.DefaultField.FIELD_WARD -> {
                wardChosen.value = itemChoose
            }
            Constants.DefaultField.FIELD_STREET -> {
                streetChosen.value = itemChoose
            }
            else -> {}
        }
    }
}