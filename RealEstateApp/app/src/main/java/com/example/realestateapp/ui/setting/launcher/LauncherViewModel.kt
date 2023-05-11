package com.example.realestateapp.ui.setting.launcher

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.R
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.EMAIL_ADDRESS
import com.example.realestateapp.extension.PASSWORD
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

sealed class LauncherUiState : UiState() {
    object InitView : LauncherUiState()
}

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val application: Application
) : BaseViewModel<LauncherUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(LauncherUiState.InitView)

    internal val email = mutableStateOf("")
    internal val password = mutableStateOf("")
    internal var firstClick = mutableStateOf(true)

    internal fun signInUser(
        onSignInSuccess: () -> Unit
    ) {
        callAPIOnThread(
            funCallApis = mutableListOf({
                appRepository.signIn(
                    email = email.value,
                    password = password.value
                )
            }),
            apiSuccess = {
                getUser().value = it.body
                AuthenticationObject.token = it.body?.token ?: ""
                onSignInSuccess()
                viewModelScope.launch(Dispatchers.IO) {
                    application.baseContext.writeStoreLauncher(
                        email = email.value,
                        password = password.value
                    )
                }
            }
        )
    }

    internal fun signUpUser(
        name: String,
        phone: String,
        onSignUpSuccess: () -> Unit
    ) {
        callAPIOnThread(
            funCallApis = mutableListOf({
                appRepository.signUp(
                    name = name,
                    phone = phone,
                    email = email.value,
                    password = password.value
                )
            }),
            apiSuccess = {
                onSignUpSuccess()
            }
        )
    }

    internal fun validEmail(mail: String): String =
        if (EMAIL_ADDRESS.matches(mail) || firstClick.value) "" else application.getString(R.string.emailError)

    internal fun validPassWord(pass: String): String =
        if (PASSWORD.matches(pass) || firstClick.value) "" else application.getString(R.string.passwordError)
}
