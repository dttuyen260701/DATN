package com.example.realestateapp.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.ItemChoose
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
    override var uiState: MutableState<UiState> = mutableStateOf(HomeUiState.InitView)

    internal val filter = mutableStateOf("")

    internal var listData = mutableListOf(
        ItemChoose("1", "Tên 1"),
        ItemChoose("2", "Tên 2"),
        ItemChoose("3", "Tên 3"),
        ItemChoose("4", "Tên 4"),
        ItemChoose("5", "Tên 5"),
        ItemChoose("6", "Tên 6"),
        ItemChoose("7", "Tên 7"),
        ItemChoose("8", "Tên 8"),
        ItemChoose("9", "Tên 9"),
        ItemChoose("10", "Tên 10"),
        ItemChoose("11", "Tên 11")
    )
}