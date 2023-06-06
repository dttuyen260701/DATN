package com.example.realestateapp.ui.notification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemChatGuest
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
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
) : BaseViewModel<NotificationUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(NotificationUiState.InitView)
    internal var isMessengerScreen = mutableStateOf(true)
    internal var itemChatGuests = mutableStateListOf<ItemChatGuest>(
        ItemChatGuest(
            idChanel = "2",
            idGuest = "123",
            nameGuest = "Loi Le",
            imageGuest = "https://znews-photo.zingcdn.me/w660/Uploaded/pgi_ubnatyvau/2019_06_21/fernandotorres2106jpg.jpg",
            lastMessage = "Xin Chào",
            isGuestSend = true
        ),
        ItemChatGuest(
            idChanel = "3",
            idGuest = "1234",
            nameGuest = "Loi Le 2",
            imageGuest = "https://cdnimg.vietnamplus.vn/uploaded/qfsqy/2019_06_21/2106_fernando_torres.jpg",
            lastMessage = "Xin Đất",
            isGuestSend = false
        ),
        ItemChatGuest(
            idChanel = "1",
            idGuest = "12453",
            nameGuest = "Loi Le 2",
            imageGuest = "https://images2.thanhnien.vn/uploaded/nguyennguyen/2016_05_17/088_UDZJ.jpg",
            lastMessage = "Xin Tiền1231233ẵcẻtwẻ,m,sdjldjsfjwelrjklẹklsdjfkljljweiỏụiơẻuiơẻuiơeuiỏưeiỏuiơẻuơiẻuoqưeqưeqưeqưeqưeqưẻqửqử",
            isGuestSend = false
        )
    )
}
