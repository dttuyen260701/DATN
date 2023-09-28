package com.example.realestateapp.ui.setting

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.writeStoreLauncher
import com.example.realestateapp.ui.base.*
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

sealed class SettingUiEffect : UiEffect() {
    object InitView : SettingUiEffect()

    object SignOutSuccess : SettingUiEffect()
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val application: Application, appRepository: AppRepository
) : BaseViewModel<SettingUiEffect>(appRepository) {
    override var uiEffectValue: MutableStateFlow<UiEffect> = MutableStateFlow(SettingUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()

    internal fun showSignOutDialog(
        message: String,
        negativeBtnText: String,
        positiveBtnText: String
    ) {
        showDialog(
            dialog = TypeDialog.ConfirmDialog(
                message = message,
                negativeBtnText = negativeBtnText,
                onBtnNegativeClick = {},
                positiveBtnText = positiveBtnText,
                onBtnPositiveClick = ::signOut
            )
        )
    }

    private fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.writeStoreLauncher(
                email = "",
                password = ""
            )
        }
        setUser(null)
        AuthenticationObject.token = ""
        uiEffectValue.value = SettingUiEffect.SignOutSuccess
    }
}
