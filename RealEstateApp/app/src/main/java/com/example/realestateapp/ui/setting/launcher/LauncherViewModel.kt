package com.example.realestateapp.ui.setting.launcher

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.EMAIL_ADDRESS
import com.example.realestateapp.extension.PASSWORD
import com.example.realestateapp.extension.writeStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import com.example.realestateapp.util.AuthenticationObject
import com.example.realestateapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class LauncherUiEffect : UiEffect() {
    object InitView : LauncherUiEffect()

    object Error : LauncherUiEffect()

    object SignInSuccess : LauncherUiEffect()

    object SignUpSuccess : LauncherUiEffect()
}

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val application: Application, appRepository: AppRepository
) : BaseViewModel<LauncherUiEffect>(appRepository) {
    override val uiEffectValue: MutableStateFlow<UiEffect> =
        MutableStateFlow(LauncherUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            LauncherUiEffect.InitView
        )

    internal var email = mutableStateOf("")
    internal var password = mutableStateOf("")
    internal var firstClick = mutableStateOf(true)

    internal fun signInUser() {
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.signIn(
                        email = email.value,
                        password = password.value
                    )
                ),
                apiSuccess = {
                    getUser().value = it.body
                    AuthenticationObject.token = it.body?.token ?: ""
                    if (it.isSuccess) {
                        viewModelScope.launch {
                            application.baseContext.writeStoreLauncher(
                                email = email.value,
                                password = password.value
                            )
                        }
                    }
                    uiEffectValue.value = LauncherUiEffect.SignInSuccess
                },
                apiError = {
                    uiEffectValue.value = LauncherUiEffect.Error
                }
            )
        }
    }

    internal fun signUpUser(
        name: String,
        phone: String,
    ) {
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.signUp(
                        name = name,
                        phone = phone,
                        email = email.value,
                        password = password.value
                    )
                ),
                apiSuccess = {
                    uiEffectValue.value = LauncherUiEffect.SignUpSuccess
                },
                apiError = {
                    uiEffectValue.value = LauncherUiEffect.Error
                }
            )
        }
    }

    internal fun validEmail(mail: String): String =
        if (EMAIL_ADDRESS.matches(mail) || firstClick.value) "" else Constants.ValidData.INVALID_EMAIL

    internal fun validPassWord(pass: String): String =
        if (PASSWORD.matches(pass) || firstClick.value) "" else Constants.ValidData.INVALID_PASSWORD
}
