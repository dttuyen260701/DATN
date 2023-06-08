package com.example.realestateapp.ui.notification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemNotification
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class NotificationUiState : UiState() {
    object InitView : NotificationUiState()

    object Loading : NotificationUiState()

    object Done : NotificationUiState()

    object Error : NotificationUiState()
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
) : BaseViewModel<NotificationUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(NotificationUiState.InitView)
    internal var isMessengerScreen = mutableStateOf(true)
    internal var itemChatGuests = mutableStateListOf<ItemChatGuest>()
    internal var itemNotifications = mutableStateListOf<ItemNotification>()
}
