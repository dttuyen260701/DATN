package com.example.realestateapp.ui.setting.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/11/2023.
 */

sealed class ProfileUiState : UiState() {
    object InitView : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : BaseViewModel<ProfileUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(ProfileUiState.InitView)


}
