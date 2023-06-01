package com.example.realestateapp.ui.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.data.enums.Direction
import com.example.realestateapp.data.enums.Juridical
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.ui.home.HomeUiState
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
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
    internal var juridicalChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var juridicalOptions = mutableStateListOf<ItemChoose>()
    internal var directionChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var directionOptions = mutableStateListOf<ItemChoose>()
    internal var square = mutableStateOf("")
    internal var price = mutableStateOf("")
    internal var floor = mutableStateOf("")
    internal var bedroom = mutableStateOf("")
    internal var streetInFront = mutableStateOf("")
    internal var width = mutableStateOf("")
    internal var length = mutableStateOf("")
    internal var isHaveCarParking = mutableStateOf(false)
    internal var isHaveRooftop = mutableStateOf(false)
    internal var isHaveKitchenRoom = mutableStateOf(false)
    internal var isHaveDiningRoom = mutableStateOf(false)
    internal var title = mutableStateOf("")
    internal var description = mutableStateOf("")
    internal var isUpLoading = mutableStateOf(false)
    internal var images = mutableStateListOf<Image>(
        Image(
            id = 2,
            url = "https://media.tinthethao.com.vn/files/news/2013/03/29/9515556ee200bc.jpg"
        ),
        Image(
            id = 3,
            url = "https://image2.tin247.news/pictures/2022/10/01/vwf1664618226.jpg"
        ),
        Image(
            id = 4,
            url = "https://static.bongda24h.vn/medias/original/2021/3/22/imgpsh_fullsize_anim.jpg"
        ),
        Image(
            id = 5,
            url = "https://static.bongda24h.vn/medias/original/2020/3/20/torres.jpg"
        ),
        Image(
            id = 6,
            url = "https://static.bongda24h.vn/medias/standard/2019/6/21/2.43654436.jpg"
        )
    )

    internal fun resetData() {

    }

    internal fun getTypes(onDone: () -> Unit) {
        uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                funCallApis = mutableListOf(
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
                    if (isMyRecords) {
                        appRepository.getPostCreatedByUser(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter,
                            showLoading = false
                        )
                    } else {
                        appRepository.getPostSaved(
                            idUser = getUser().value?.id ?: 0,
                            pageIndex = 1,
                            pageSize = 200,
                            filter = filter,
                            showLoading = false
                        )
                    }
                ), apiSuccess = {
                    uiState.value =
                        PostUiState.GetSearchDataSuccess(it.body.items ?: mutableListOf())
                }, apiError = {
                    uiState.value = PostUiState.Error
                }
            )
        }

    }

    internal fun getDataChoice(key: String, onDone: () -> Unit) {
        when (key) {
            FIELD_JURIDICAL -> {
                juridicalOptions.run {
                    clear()
                    addAll(
                        Juridical.values().map { juridical ->
                            juridical.value.isSelected = (juridical.value == juridicalChosen.value)
                            juridical.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_DIRECTION -> {
                directionOptions.run {
                    clear()
                    addAll(
                        Direction.values().map { direction ->
                            direction.value.isSelected = (direction.value == directionChosen.value)
                            direction.value
                        }.toMutableList()
                    )
                }
            }
        }
        onDone()
    }
}
