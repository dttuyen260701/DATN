package com.example.realestateapp.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.extension.readStoreLauncher
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.AuthenticationObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/3/2023.
 */

sealed class MainUiState : UiState() {
    object InitView : MainUiState()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val application: Application
) : BaseViewModel<MainUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(MainUiState.InitView)

    internal fun backgroundSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            application.baseContext.readStoreLauncher { email, pass ->
                callAPIOnThread(
                    funCallApis = mutableListOf({
                        appRepository.signIn(
                            email = email,
                            password = pass
                        )
                    }),
                    apiSuccess = {
                        getUser().value = it.body
                        AuthenticationObject.token = it.body?.token ?: ""
                    }
                )
            }
        }
    }
}
