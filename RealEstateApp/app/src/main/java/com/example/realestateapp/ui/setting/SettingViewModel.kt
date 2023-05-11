package com.example.realestateapp.ui.setting

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.extension.writeStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class SettingUiState : UiState() {
    object InitView : SettingUiState()
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel<SettingUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(SettingUiState.InitView)

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.writeStoreLauncher(
                email = "",
                password = ""
            )
        }
        getUser().value = null
        AuthenticationObject.token = ""
    }
}
