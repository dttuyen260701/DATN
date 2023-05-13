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

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<HomeUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(HomeUiState.InitView)

    internal var filter = mutableStateOf("")

    internal var listData = mutableListOf<ItemChoose>()

    internal fun getTypes() {
        callAPIOnThread(
            funCallApis = mutableListOf({
                appRepository.getTypes(showLoading = false)
            }),
            apiSuccess = {
                uiState.value = HomeUiState.GetTypesSuccess(it.body)
            },
            showDialog = false
        )
    }
}