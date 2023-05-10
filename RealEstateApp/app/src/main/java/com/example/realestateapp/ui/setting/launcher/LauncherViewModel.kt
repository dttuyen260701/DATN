package com.example.realestateapp.ui.setting.launcher

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.R
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.EMAIL_ADDRESS
import com.example.realestateapp.extension.PASSWORD
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
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
    override var uiState: UiState = LauncherUiState.InitView

    internal val email = mutableStateOf("")
    internal val password = mutableStateOf("")
    internal var firstClickButton = mutableStateOf(true)

    internal fun loginUser(
        onSignInSuccess: () -> Unit
    ) {
        callAPIOnThread(
            funCallApis = mutableListOf({
                appRepository.login(
                    email = email.value,
                    password = password.value
                )
            }),
            apiSuccess = {
                if(it.isSuccess) {
                    getUser().value = it.body
                    AuthenticationObject.token = it.body?.token ?: ""
                    onSignInSuccess()
                } else {
                    showDialog(
                        dialog = TypeDialog.ErrorDialog(it.errorMessage ?: "")
                    )
                }
            }
        )
    }

    fun validEmail(mail: String): String =
        if (EMAIL_ADDRESS.matches(mail) || firstClickButton.value) "" else application.getString(R.string.emailError)

    fun validPassWord(pass: String): String =
        if (PASSWORD.matches(pass) || firstClickButton.value) "" else application.getString(R.string.passwordError)
}
