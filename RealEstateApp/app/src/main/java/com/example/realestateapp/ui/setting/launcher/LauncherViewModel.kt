package com.example.realestateapp.ui.setting.launcher

import android.app.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.realestateapp.data.repository.*
import com.example.realestateapp.extension.*
import com.example.realestateapp.ui.base.*
import com.example.realestateapp.util.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

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

    internal fun btnSignInClick(enableBtnSignIn: Boolean) {
        if (enableBtnSignIn && !firstClick.value) signInUser()
        firstClick.value = false
    }

    internal fun signInUser() {
        viewModelScope.launch {
            callAPIOnThread(
                requests = mutableListOf(
                    appRepository.signIn(
                        email = email.value,
                        password = password.value
                    )
                ),
                apiSuccess = {
                    setUser(it.body)
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
                requests = mutableListOf(
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
