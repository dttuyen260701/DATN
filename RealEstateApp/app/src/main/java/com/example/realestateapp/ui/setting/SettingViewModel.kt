package com.example.realestateapp.ui.setting

import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class SettingUiState : UiState() {
    object InitView : SettingUiState()
}

@HiltViewModel
class SettingViewModel @Inject constructor(

) : BaseViewModel<SettingUiState>() {
    override var uiState: UiState = SettingUiState.InitView

    internal fun signOut() {
        getUser().value = null
        AuthenticationObject.token = ""
    }
}
