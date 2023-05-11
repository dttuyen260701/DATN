package com.example.realestateapp.ui.setting.changepass

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/11/2023.
 */

sealed class ChangePassUiState : UiState() {
    object InitView : ChangePassUiState()
}

@HiltViewModel
class ChangePassViewModel @Inject constructor(

) : BaseViewModel<ChangePassUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(ChangePassUiState.InitView)
}