package com.example.realestateapp.ui.home.search

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.*
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.DefaultField.FIELD_BED_ROOM
import com.example.realestateapp.util.Constants.DefaultField.FIELD_CAR_PARKING
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DINING_ROOM
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_FLOOR
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
import com.example.realestateapp.util.Constants.DefaultField.FIELD_KITCHEN_ROOM
import com.example.realestateapp.util.Constants.DefaultField.FIELD_LENGTH
import com.example.realestateapp.util.Constants.DefaultField.FIELD_PRICE
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ROOFTOP
import com.example.realestateapp.util.Constants.DefaultField.FIELD_SQUARE
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET_OF_FRONT
import com.example.realestateapp.util.Constants.DefaultField.FIELD_WIDTH
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/19/2023.
 */

sealed class SearchUiState : UiState() {
    object InitView : SearchUiState()

    object Loading : SearchUiState()

    object Error : SearchUiState()

    object Done : SearchUiState()

    data class GetTypesSuccess(val data: MutableList<ItemChoose>) : SearchUiState()

    data class GetSearchDataSuccess(val data: MutableList<RealEstateList>) : SearchUiState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application, appRepository: AppRepository
) : BaseViewModel<SearchUiState>(appRepository) {
    override var uiStateValue: MutableStateFlow<UiState> = MutableStateFlow(SearchUiState.InitView)
    override val uiState: StateFlow<UiState> = uiStateValue.asStateFlow()
    internal var searchResult = mutableStateListOf<RealEstateList>()
    internal var isShowSearchOption = mutableStateOf(true)
    internal var isShowSearchHighOption = mutableStateOf(false)
    internal var isFirstComposing = mutableStateOf(true)
    internal var isNavigateAnotherScr = mutableStateOf(false)
    internal var filter = mutableStateOf("")
    internal var detailAddress = mutableStateOf("")
    internal var typesData = mutableStateListOf<ItemChoose>()
    internal var sortOptions = mutableStateListOf(
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
    internal var priceChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var priceOptions = mutableStateListOf<ItemChoose>()
    internal var squareChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var squareOptions = mutableStateListOf<ItemChoose>()
    internal var bedroomChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var bedroomOptions = mutableStateListOf<ItemChoose>()
    internal var floorChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var floorOptions = mutableStateListOf<ItemChoose>()
    internal var juridicalChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var juridicalOptions = mutableStateListOf<ItemChoose>()
    internal var directionChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var directionOptions = mutableStateListOf<ItemChoose>()
    internal var streetInFrontChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var streetInFrontOptions = mutableStateListOf<ItemChoose>()
    internal var widthChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var widthOptions = mutableStateListOf<ItemChoose>()
    internal var lengthChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var lengthOptions = mutableStateListOf<ItemChoose>()
    internal var carParkingChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var carParkingOptions = mutableStateListOf<ItemChoose>()
    internal var rooftopChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var rooftopOptions = mutableStateListOf<ItemChoose>()
    internal var dinningRoomChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var dinningRoomOptions = mutableStateListOf<ItemChoose>()
    internal var kitchenRoomChosen = mutableStateOf(DEFAULT_ITEM_CHOSEN)
    internal var kitchenRoomOptions = mutableStateListOf<ItemChoose>()

    internal fun onChoiceSortType(idType: Int, isCallApi: Boolean) {
        val oldIndex = sortOptions.indexOfFirst { it.isSelected }
        val newIndex = sortOptions.indexOfFirst { it.id == idType }
        if (oldIndex != newIndex) {
            if (oldIndex != -1)
                sortOptions[oldIndex] =
                    sortOptions[oldIndex].copy(isSelected = false)
            sortOptions[newIndex] = sortOptions[newIndex].copy(isSelected = true)
        }
        if (isCallApi) {
            searchPostWithOptions(isResetPaging = true)
        }
    }

    internal fun getTypes() {
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.getTypes(showLoading = false),
                ), apiSuccess = {
                    uiStateValue.value = SearchUiState.GetTypesSuccess(it.body)
                }, apiError = {
                    uiStateValue.value = SearchUiState.Error
                }, showDialog = false
            )
        }
    }

    internal fun searchPostWithOptions(
        key: String = filter.value,
        isResetPaging: Boolean = false
    ) {
        if (isResetPaging) {
            resetPaging()
            searchResult.clear()
        }
        val typePropertyIds = typesData.filter { it.isSelected }.map { it.id }
        uiStateValue.value = SearchUiState.Loading
        viewModelScope.launch {
            callAPIOnThread(
                response = mutableListOf(
                    appRepository.searchPostWithOptions(
                        idUser = getUser().value?.id?.toString() ?: "",
                        minPrice = priceChosen.value.id,
                        maxPrice = priceChosen.value.score,
                        minBedRoom = bedroomChosen.value.id,
                        maxBedRoom = bedroomChosen.value.score,
                        minWidth = widthChosen.value.id,
                        maxWidth = widthChosen.value.score,
                        minSquare = squareChosen.value.id,
                        maxSquare = squareChosen.value.score,
                        minLength = lengthChosen.value.id,
                        maxLength = lengthChosen.value.score,
                        minFloor = floorChosen.value.id,
                        maxFloor = floorChosen.value.score,
                        minKitchen = kitchenRoomChosen.value.id,
                        maxKitchen = kitchenRoomChosen.value.score,
                        propertyTypeId = typePropertyIds.toMutableList(),
                        legalId = juridicalChosen.value.id,
                        carParking = (carParkingChosen.value.id == 1),
                        directionId = directionChosen.value.id,
                        rooftop = (rooftopChosen.value.id == 1),
                        districtId = PickAddressViewModel.districtChosen.value.id,
                        wardId = PickAddressViewModel.wardChosen.value.id,
                        streetId = PickAddressViewModel.streetChosen.value.id,
                        minWidthRoad = streetInFrontChosen.value.id,
                        maxWidthRoad = streetInFrontChosen.value.score,
                        pageIndex = getPagingModel().pageIndex,
                        pageSize = getPagingModel().pageSize,
                        search = key,
                        optionSort = (sortOptions.firstOrNull { it.isSelected }
                            ?: DEFAULT_ITEM_CHOSEN).id,
                        showLoading = false
                    )
                ), apiSuccess = {
                    if (key == filter.value) {
                        it.body.run {
                            uiStateValue.value =
                                SearchUiState.GetSearchDataSuccess(items ?: mutableListOf())
                            updatePagingModel(
                                totalPageNew = pageCount,
                                totalRecordsNew = totalRecords
                            )
                        }
                    }
                }, apiError = {
                    uiStateValue.value = SearchUiState.Error
                }
            )
        }
    }

    internal fun getDataChoice(key: String, onDone: () -> Unit) {
        when (key) {
            FIELD_PRICE -> {
                priceOptions.run {
                    clear()
                    addAll(
                        PriceOption.values().map { price ->
                            price.value.isSelected = (price.value == priceChosen.value)
                            price.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_SQUARE -> {
                squareOptions.run {
                    clear()
                    addAll(
                        SquareOption.values().map { square ->
                            square.value.isSelected = (square.value == squareChosen.value)
                            square.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_BED_ROOM -> {
                bedroomOptions.run {
                    clear()
                    addAll(
                        BedRoomOption.values().map { bedroom ->
                            bedroom.value.isSelected = (bedroom.value == bedroomChosen.value)
                            bedroom.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_FLOOR -> {
                floorOptions.run {
                    clear()
                    addAll(
                        FloorOption.values().map { floor ->
                            floor.value.isSelected = (floor.value == floorChosen.value)
                            floor.value
                        }.toMutableList()
                    )
                }
            }
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
            FIELD_STREET_OF_FRONT -> {
                streetInFrontOptions.run {
                    clear()
                    addAll(
                        UnitMeterOption.values().map { item ->
                            item.value.isSelected = (item.value == streetInFrontChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_WIDTH -> {
                widthOptions.run {
                    clear()
                    addAll(
                        UnitMeterOption.values().map { item ->
                            item.value.isSelected = (item.value == widthChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_LENGTH -> {
                lengthOptions.run {
                    clear()
                    addAll(
                        UnitMeterOption.values().map { item ->
                            item.value.isSelected = (item.value == lengthChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_CAR_PARKING -> {
                carParkingOptions.run {
                    clear()
                    addAll(
                        YesNoOptions.values().map { item ->
                            item.value.isSelected = (item.value == carParkingChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_ROOFTOP -> {
                rooftopOptions.run {
                    clear()
                    addAll(
                        YesNoOptions.values().map { item ->
                            item.value.isSelected = (item.value == rooftopChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_DINING_ROOM -> {
                dinningRoomOptions.run {
                    clear()
                    addAll(
                        YesNoOptions.values().map { item ->
                            item.value.isSelected = (item.value == dinningRoomChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            FIELD_KITCHEN_ROOM -> {
                kitchenRoomOptions.run {
                    clear()
                    addAll(
                        YesNoOptions.values().map { item ->
                            item.value.isSelected = (item.value == kitchenRoomChosen.value)
                            item.value
                        }.toMutableList()
                    )
                }
            }
            else -> {}
        }
        onDone()
    }
}
