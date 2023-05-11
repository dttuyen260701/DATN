package com.example.realestateapp.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class PostUiState : UiState() {
    object InitView : PostUiState()
}

@HiltViewModel
class PostViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<PostUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PostUiState.InitView)

}
