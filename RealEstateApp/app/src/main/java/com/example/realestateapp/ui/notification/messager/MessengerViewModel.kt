package com.example.realestateapp.ui.notification.messager

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.ui.MainActivity.Companion.getDataChild
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemMessenger
import com.example.realestateapp.data.models.User
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.MessageDefault.SEND_IMAGE
import com.example.realestateapp.util.Constants.MessageDefault.TYPE_MESSAGE
import com.example.realestateapp.util.Constants.MessageDefault.TYPE_PHOTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    companion object {
        internal var nameGuest = mutableStateOf("")
        internal var imageGuest = mutableStateOf("")
    }

    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(MessengerUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var message = mutableStateOf("")
    internal var idChannel = mutableStateOf("")
    internal var isUpLoading = mutableStateOf(false)
    internal val chats = mutableStateListOf<ItemMessenger>()

    internal fun sendMessage(
        user: User,
        idGuest: Int,
        nameGuest: String,
        imgGuest: String,
        message: String,
        typeMessenger: Int = TYPE_MESSAGE,
        idChannelSend: String,
        onSendSuccess: () -> Unit
    ) {
        user.run {
            val currentTime = System.currentTimeMillis()
            getDataChild(Constants.FireBaseRef.CHANNEL_GUEST).child(idGuest.toString())
                .child(id.toString()).setValue(
                    ItemChatGuest(
                        idChannel = idChannelSend,
                        idGuest = id.toString(),
                        nameGuest = fullName,
                        imageGuest = imgUrl ?: "",
                        lastMessage = if (typeMessenger == TYPE_PHOTO) SEND_IMAGE else message,
                        idUserSend = idGuest.toString()
                    )
                )
            getDataChild(Constants.FireBaseRef.CHANNEL_GUEST).child(id.toString())
                .child(idGuest.toString()).setValue(
                    ItemChatGuest(
                        idChannel = idChannelSend,
                        idGuest = idGuest.toString(),
                        nameGuest = nameGuest,
                        imageGuest = imgGuest,
                        lastMessage = if (typeMessenger == TYPE_PHOTO) SEND_IMAGE else message,
                        idUserSend = idGuest.toString(),
                        read = true
                    )
                )
            getDataChild(Constants.FireBaseRef.CHANNEL_CHAT).child(idChannelSend)
                .child(currentTime.toString())
                .setValue(
                    ItemMessenger(
                        timeMilliseconds = currentTime,
                        idUserSend = id,
                        messenger = message,
                        typeMessage = typeMessenger
                    )
                ) { _, _ ->
                    onSendSuccess()
                }
        }
    }
}
