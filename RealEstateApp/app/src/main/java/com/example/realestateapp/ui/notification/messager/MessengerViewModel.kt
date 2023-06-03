package com.example.realestateapp.ui.notification.messager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

}
