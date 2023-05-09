package com.example.realestateapp.ui.home

import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class HomeUiState : UiState() {
    object InitView : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<HomeUiState>() {
    override var uiState: UiState = HomeUiState.InitView

}