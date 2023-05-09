package com.example.realestateapp.ui.notification

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

    override var uiState: UiState = NotificationUiState.InitView
}
