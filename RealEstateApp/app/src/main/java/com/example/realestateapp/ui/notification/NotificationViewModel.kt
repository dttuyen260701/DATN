package com.example.realestateapp.ui.notification

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemNotification
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class NotificationUiEffect : UiEffect() {
    object InitView : NotificationUiEffect()

    object Loading : NotificationUiEffect()

    object Done : NotificationUiEffect()
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<NotificationUiEffect>(appRepository) {
    override var uiEffectValue: MutableStateFlow<UiEffect> = MutableStateFlow(NotificationUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()
    internal var isMessengerScreen = mutableStateOf(true)
    internal var itemChatGuests = mutableStateListOf<ItemChatGuest>()
    internal var itemNotifications = mutableStateListOf<ItemNotification>()
}
