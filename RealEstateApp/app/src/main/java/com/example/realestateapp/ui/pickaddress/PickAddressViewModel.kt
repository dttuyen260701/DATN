package com.example.realestateapp.ui.pickaddress

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/22/2023.
 */

sealed class PickAddressUiState : UiState() {
    object InitView : PickAddressUiState()
}

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<PickAddressUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PickAddressUiState.InitView)

}