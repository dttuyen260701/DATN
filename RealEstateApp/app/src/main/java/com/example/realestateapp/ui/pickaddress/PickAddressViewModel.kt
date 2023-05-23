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
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_CHOSEN
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
}

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<PickAddressUiState>() {
    companion object {
        internal var districtIdChosen: Int = DEFAULT_ID_CHOSEN
            private set
        internal var wardIdChosen: Int = DEFAULT_ID_CHOSEN
            private set
        internal var streetIdChosen: Int = DEFAULT_ID_CHOSEN
            private set
    }

    override var uiState: MutableState<UiState> = mutableStateOf(PickAddressUiState.InitView)
    internal var districtsData = mutableStateListOf<ItemChoose>()
    internal var wardsData = mutableStateListOf<ItemChoose>()

    internal fun getDistrictData(filter: String) {
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(funCallApis = mutableListOf(
                appRepository.getTypes(),
            ), apiSuccess = {
                it.body.map { itemChoose ->
                    itemChoose.isSelected = (itemChoose.id == districtIdChosen)
                }.toMutableList()
                uiState.value = PickAddressUiState.GetDistrictSuccess(it.body)
            }, apiError = {
                uiState.value = PickAddressUiState.Error
            }, showDialog = false
            )
        }
    }

    internal fun getWardData(filter: String) {
        uiState.value = PickAddressUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(funCallApis = mutableListOf(
                appRepository.getWards(districtId = districtIdChosen.toString()),
            ), apiSuccess = {
                uiState.value = PickAddressUiState.GetDistrictSuccess(it.body)
            }, apiError = {
                uiState.value = PickAddressUiState.Error
            }, showDialog = false
            )
        }
    }

    internal fun onChoiceData(data: MutableList<ItemChoose>, itemChoose: ItemChoose, key: String) {
        when (key) {
            Constants.DefaultField.FIELD_DISTRICT -> {
                districtIdChosen = itemChoose.id
            }
            Constants.DefaultField.FIELD_WARD -> {
                wardIdChosen = itemChoose.id
            }
            Constants.DefaultField.FIELD_STREET -> {
                streetIdChosen = itemChoose.id
            }
            else -> {}
        }
        val oldIndex = data.indexOfFirst { it.isSelected }
        val newIndex = data.indexOf(itemChoose)
        if (oldIndex != newIndex) {
            if (oldIndex != -1)
                data[oldIndex] =
                    data[oldIndex].copy(isSelected = false)
            data[newIndex] = data[newIndex].copy(isSelected = true)
        }
    }
}