package com.example.realestateapp.ui.home.search

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.SearchOption
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/19/2023.
 */

sealed class SearchUiState : UiState() {
    object InitView : SearchUiState()

    object Error : SearchUiState()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : SearchUiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRepository: AppRepository,
    application: Application
) : BaseViewModel<SearchUiState>() {
    override var uiState: MutableState<UiState> = mutableStateOf(SearchUiState.InitView)
    internal var filter = mutableStateOf("")
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var sortOptions =
        mutableStateListOf(
            ItemChoose(
                id = SearchOption.LATEST.option,
                name = application.getString(R.string.latestSortTitle),
                score = -1
            ),
            ItemChoose(
                id = SearchOption.MOST_VIEW.option,
                name = application.getString(R.string.viewSortTitle),
                score = -2
            ),
            ItemChoose(
                id = SearchOption.HIGHEST_PRICE.option,
                name = application.getString(R.string.highestPriceSortTitle),
                score = -3
            ),
            ItemChoose(
                id = SearchOption.LOWEST_PRICE.option,
                name = application.getString(R.string.lowestPriceSortTitle),
                score = -4
            )
        )

    internal fun onChoiceSortType(idType: Int) {
        val oldIndex = sortOptions.indexOfFirst { it.isSelected }
        val newIndex = sortOptions.indexOfFirst { it.id == idType }
        if (oldIndex != newIndex) {
            if (oldIndex != -1)
                sortOptions[oldIndex] =
                    sortOptions[oldIndex].copy(isSelected = false)
            sortOptions[newIndex] = sortOptions[newIndex].copy(isSelected = true)
        }
    }

    internal fun getTypes() {
        viewModelScope.launch {
            callAPIOnThread(funCallApis = mutableListOf(
                appRepository.getTypes(showLoading = false),
            ), apiSuccess = {
                uiState.value = SearchUiState.GetTypesSuccess(it.body)
            }, apiError = {
                uiState.value = SearchUiState.Error
            }, showDialog = false
            )
        }
    }
}
