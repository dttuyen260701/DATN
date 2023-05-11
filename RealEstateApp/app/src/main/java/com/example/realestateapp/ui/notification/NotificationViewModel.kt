package com.example.realestateapp.ui.notification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class NotificationUiState : UiState() {
    object InitView : NotificationUiState()
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<NotificationUiState>() {

    override var uiState: MutableState<UiState> = mutableStateOf(NotificationUiState.InitView)
}
