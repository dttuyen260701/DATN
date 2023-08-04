package com.example.realestateapp.ui.setting.changepass

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.R
import com.example.realestateapp.extension.PASSWORD
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
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

sealed class ChangePassUiState : UiState() {
    object InitView : ChangePassUiState()

    data class ChangePassSuccess(val data: Boolean) : ChangePassUiState()
}

@HiltViewModel
class ChangePassViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel<ChangePassUiState>() {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(ChangePassUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()

    internal val oldPass = mutableStateOf("")
    internal val newPass = mutableStateOf("")
    internal val newPassRepeat = mutableStateOf("")
    internal var firstClick = mutableStateOf(true)

    internal fun changePassword() {
        getUser().value?.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                callAPIOnThread(
                    response = mutableListOf(
                        appRepository.changePassWord(
                            idUser = it,
                            oldPassword = oldPass.value,
                            newPassword = newPass.value
                        )
                    ),
                    apiSuccess = {
                        uiStateValue.value = ChangePassUiState.ChangePassSuccess(it.isSuccess)
                    }
                )
            }
        }
    }

    internal fun validPassWord(pass: String): String =
        if (PASSWORD.matches(pass) || firstClick.value) "" else application.getString(R.string.passwordError)
}