package com.example.realestateapp.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.ui.home.HomeUiState
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
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

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : PostUiState()

    data class GetSearchDataSuccess(val data: MutableList<RealEstateList>) : PostUiState()
}

@HiltViewModel
class PostViewModel @Inject constructor(

) : BaseViewModel<PostUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(PostUiState.InitView)
    internal var firstClick = mutableStateOf(true)
    internal var filter = mutableStateOf("")
    internal var isNavigateAnotherScr = mutableStateOf(true)
    internal var searchResult = mutableStateListOf<RealEstateList>()
    internal var detailAddress = mutableStateOf("")
    internal var typeChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var square = mutableStateOf("")
    internal var price = mutableStateOf("")

    internal fun resetData() {

    }

    internal fun getTypes(onDone: () -> Unit) {
        uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(funCallApis = mutableListOf(
                appRepository.getTypes(showLoading = false),
            ), apiSuccess = {
                if (it.body.indexOf(typeChosen.value) != -1) {
                    it.body[it.body.indexOf(typeChosen.value)].isSelected = true
                }
                uiState.value = PostUiState.GetTypesSuccess(it.body)
            }, apiError = {
                uiState.value = PostUiState.Error
            }, onDoneCallApi = onDone, showDialog = false
            )
        }
    }

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
