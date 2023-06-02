package com.example.realestateapp.ui

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/3/2023.
 */

sealed class MainUiState : UiState() {
    object InitView : MainUiState()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : BaseViewModel<MainUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(MainUiState.InitView)

    internal var uri: Uri? = null
}
