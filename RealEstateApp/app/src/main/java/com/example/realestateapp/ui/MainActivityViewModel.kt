package com.example.realestateapp.ui

import android.net.Uri
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    override val uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(MainUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()

    internal var uri: Uri? = null
}
