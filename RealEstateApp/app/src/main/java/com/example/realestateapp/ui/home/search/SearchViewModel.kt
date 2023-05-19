package com.example.realestateapp.ui.home.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/19/2023.
 */

sealed class SearchUiState : UiState() {
    object InitView : SearchUiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<SearchUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(SearchUiState.InitView)
    internal var filter = mutableStateListOf("")

}
