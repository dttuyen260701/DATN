package com.example.realestateapp.ui.notification

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemNotification
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(NotificationUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var isMessengerScreen = mutableStateOf(true)
    internal var itemChatGuests = mutableStateListOf<ItemChatGuest>()
    internal var itemNotifications = mutableStateListOf<ItemNotification>()
}
