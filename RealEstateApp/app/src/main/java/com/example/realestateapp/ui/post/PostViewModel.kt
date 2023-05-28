package com.example.realestateapp.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

sealed class PostUiState : UiState() {
    object InitView : PostUiState()

    object Loading : PostUiState()

    object Error : PostUiState()

    object Done : PostUiState()

    data class GetSearchDataSuccess(val data: MutableList<RealEstateList>) : PostUiState()
}

@HiltViewModel
class PostViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<PostUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PostUiState.InitView)
    internal var filter = mutableStateOf("")
    internal var isNavigateAnotherScr = mutableStateOf(true)
    internal var searchResult = mutableStateListOf<RealEstateList>()

    internal fun getPosts(
        isMyRecords: Boolean,
        filter: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = PostUiState.Loading
            callAPIOnThread(
                funCallApis = mutableListOf(
                    appRepository.getPostsWOptions(
                        pageIndex = 1,
                        pageSize = 20,
                        isMostView = true,
                        typePropertyIds = mutableListOf(),
                        isHighestPrice = false,
                        isLowestPrice = false,
                        isLatest = false,
                        userId = getUser().value?.id ?: 0,
                        showLoading = false
                    )
                ), apiSuccess = {
                    uiState.value =
                        PostUiState.GetSearchDataSuccess(it.body.items ?: mutableListOf())
                }, apiError = {
                    uiState.value = PostUiState.Error
                }
            )
        }

    }
}
