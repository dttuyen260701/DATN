package com.example.realestateapp.ui

import com.example.realestateapp.data.repository.AppRepository
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
    appRepository: AppRepository
) : BaseViewModel<MainUiState>() {
    override var uiState: UiState = MainUiState.InitView
}
