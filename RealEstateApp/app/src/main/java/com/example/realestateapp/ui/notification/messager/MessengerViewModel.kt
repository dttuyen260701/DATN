package com.example.realestateapp.ui.notification.messager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemMessenger
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import javax.inject.Inject

/**
 * Created by tuyen.dang on 6/3/2023.
 */

sealed class MessengerUiState : UiState() {
    object InitView : MessengerUiState()

    object Loading : MessengerUiState()
}

class MessengerViewModel @Inject constructor(
) : BaseViewModel<MessengerUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(MessengerUiState.InitView)
    internal var message = mutableStateOf("")
    internal var idChannel = mutableStateOf("")
    internal var isUpLoading = mutableStateOf(false)
    internal var chats = mutableStateListOf<ItemMessenger>(
        ItemMessenger(
            timeMilliseconds = 1234667,
            idUserSend = 1,
            messenger = "Test Mest"
        ),
        ItemMessenger(
            timeMilliseconds = 11245,
            idUserSend = 3,
            messenger = "Test Mest 21312312"
        ),
        ItemMessenger(
            timeMilliseconds = 123123123,
            idUserSend = 3,
            messenger = "Test Mest 123 12312312312 3 1ádsad ád qưe qưeqư eqưeqưe qưeqư eqư eqư eqưe qưe qưe qưe qưe qưe qưe qư"
        ),
        ItemMessenger(
            timeMilliseconds = 22,
            idUserSend = 3,
            messenger = "https://icdn.dantri.com.vn/thumb_w/680/2022/12/19/gettyimages-1450107740-1671453343158.jpg",
            isPhoto = true
        ),
    )

}
