package com.example.realestateapp.ui.setting

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.extension.writeStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
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

sealed class SettingUiState : UiState() {
    object InitView : SettingUiState()

    object SignOutSuccess : SettingUiState()
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel<SettingUiState>() {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(SettingUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.writeStoreLauncher(
                email = "",
                password = ""
            )
        }
        getUser().value = null
        AuthenticationObject.token = ""
        uiStateValue.value = SettingUiState.SignOutSuccess
    }
}
