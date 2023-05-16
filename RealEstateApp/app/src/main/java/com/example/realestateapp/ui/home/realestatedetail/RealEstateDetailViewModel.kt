package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/16/2023.
 */


sealed class PostDetailUiState : UiState() {
    object InitView : PostDetailUiState()
}

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<PostDetailUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PostDetailUiState.InitView)

}
