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
    internal var itemNotifications = mutableStateListOf<ItemNotification>(
        ItemNotification(
            timeMilliseconds = 12,
            idPost = 12,
            image = "https://image-us.24h.com.vn/upload/3-2020/images/2020-09-18/faar-1600415791-488-width640height480.jpg",
            messenger = "Đã được duyệt"
        ),
        ItemNotification(
            timeMilliseconds = 13,
            idPost = 13,
            image = "https://media.bongda.com.vn/resize/800x800/files/news/2019/06/23/torres-tiet-lo-ly-do-giai-nghe-chi-ra-cau-thu-hay-nhat-tung-choi-cung-152947.jpg",
            messenger = "Đã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệtĐã được duyệt"
        ),
        ItemNotification(
            timeMilliseconds = 14,
            idPost = 14,
            image = "https://image-us.24h.com.vn/upload/3-2020/images/2020-09-18/faar-1600415791-488-width640height480.jpg",
            messenger = "Đã được duyệt",
            read = true
        ),
    )
}
