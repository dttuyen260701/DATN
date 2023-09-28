package com.example.realestateapp.ui.setting.changepass

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.PASSWORD
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import com.example.realestateapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/11/2023.
 */

sealed class ChangePassUiEffect : UiEffect() {
    object InitView : ChangePassUiEffect()

    data class ChangePassSuccess(val data: Boolean) : ChangePassUiEffect()
}

@HiltViewModel
class ChangePassViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<ChangePassUiEffect>(appRepository) {
    override var uiEffectValue: MutableStateFlow<UiEffect> =
        MutableStateFlow(ChangePassUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()

    internal val oldPass = mutableStateOf("")
    internal val newPass = mutableStateOf("")
    internal val newPassRepeat = mutableStateOf("")
    internal var firstClick = mutableStateOf(true)

    internal fun changePassword() {
        getUser().value?.id?.let {
            viewModelScope.launch {
                callAPIOnThread(
                    requests = mutableListOf(
                        appRepository.changePassWord(
                            idUser = it,
                            oldPassword = oldPass.value,
                            newPassword = newPass.value
                        )
                    ),
                    apiSuccess = {
                        uiEffectValue.value = ChangePassUiEffect.ChangePassSuccess(it.isSuccess)
                    }
                )
            }
        }
    }

    internal fun validPassWord(pass: String): String =
        if (PASSWORD.matches(pass) || firstClick.value) "" else Constants.ValidData.INVALID_PASSWORD
}