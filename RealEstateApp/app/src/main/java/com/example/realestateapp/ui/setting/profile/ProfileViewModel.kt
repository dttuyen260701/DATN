package com.example.realestateapp.ui.setting.profile

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.enums.GenderOption
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.User
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.DefaultField.FIELD_GENDER
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
 * Created by tuyen.dang on 5/11/2023.
 */

sealed class ProfileUiState : UiState() {
    object InitView : ProfileUiState()

    object Loading : ProfileUiState()

    object Error : ProfileUiState()

    object Done : ProfileUiState()

    data class GetInformationUserSuccess(val data: User) : ProfileUiState()

    data class UpdateInformationUserSuccess(val data: User) : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : BaseViewModel<ProfileUiState>() {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(ProfileUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var firstClick = mutableStateOf(true)
    internal var imgUrl = mutableStateOf("")
    internal var name = mutableStateOf("")
    internal var dateChosen = mutableStateOf("")
    internal var genderChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var genderOptions = mutableStateListOf<ItemChoose>()
    internal var detailAddress = mutableStateOf("")

    internal fun updateInformationUser() {
        viewModelScope.launch(Dispatchers.IO) {
            getUser().value?.id?.let { id ->
                PickAddressViewModel.run {
                    callAPIOnThread(
                        response = mutableListOf(
                            appRepository.updateUser(
                                userId = id,
                                fullName = name.value,
                                dateOfBirth = dateChosen.value,
                                gender = genderChosen.value.id,
                                addressDetail = detailStreet.value,
                                wardId = wardChosen.value.id,
                                districtId = districtChosen.value.id,
                                newImage = imgUrl.value
                            )
                        ),
                        apiSuccess = {
                            uiStateValue.value = it.body?.let {
                                ProfileUiState.UpdateInformationUserSuccess(it)
                            } ?: ProfileUiState.Error
                        },
                        apiError = {

                        }
                    )
                }
            }
        }
    }

    internal fun getInformationUser() {
        viewModelScope.launch(Dispatchers.IO) {
            getUser().value?.id.let { id ->
                callAPIOnThread(
                    response = mutableListOf(
                        appRepository.getInformationUser(id ?: DEFAULT_ID_POST)
                    ),
                    apiSuccess = {
                        uiStateValue.value =
                            ProfileUiState.GetInformationUserSuccess(it.body)
                    },
                    apiError = {

                    }
                )
            }
        }
    }

    internal fun getDataChoice(key: String, onDone: () -> Unit) {
        when (key) {
            FIELD_GENDER -> {
                genderOptions.run {
                    clear()
                    addAll(
                        GenderOption.values().map { gender ->
                            gender.value.isSelected =
                                (gender.value.id == genderChosen.value.id)
                            gender.value
                        }.toMutableList()
                    )
                }
            }
        }
        onDone()
    }
}
