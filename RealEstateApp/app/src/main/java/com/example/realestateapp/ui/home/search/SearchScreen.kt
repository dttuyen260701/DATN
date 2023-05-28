package com.example.realestateapp.ui.home.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
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
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.SEARCH_TIME
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME
import kotlinx.coroutines.delay

/**
 * Created by tuyen.dang on 5/19/2023.
 */

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    searchOption: Int,
    onBackClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    navigateToPickAddress: () -> Unit,
    addressDetails: MutableList<String>
) {
    val context = LocalContext.current
    viewModel.run {
        var filter by remember { filter }
        val searchResultListState = rememberLazyListState()
        val searchResult = remember { searchResult }
        val sortOptions = remember { sortOptions }
        val types = remember { typesData }
        var isShowSearchOption by remember { isShowSearchOption }
        var uiState by remember { uiState }
        var addressDetailDisplay by remember { detailAddress }
        var priceChosen by remember { priceChosen }
        val priceOptions = remember { priceOptions }
        var squareChosen by remember { squareChosen }
        val squareOptions = remember { squareOptions }
        var bedroomChosen by remember { bedroomChosen }
        val bedroomOptions = remember { bedroomOptions }
        var floorChosen by remember { floorChosen }
        val floorOptions = remember { floorOptions }
        var juridicalChosen by remember { juridicalChosen }
        val juridicalOptions = remember { juridicalOptions }
        var directionChosen by remember { directionChosen }
        val directionOptions = remember { directionOptions }
        var streetInFrontChosen by remember { streetInFrontChosen }
        val streetInFrontOptions = remember { streetInFrontOptions }
        var widthChosen by remember { widthChosen }
        val widthOptions = remember { widthOptions }
        var lengthChosen by remember { lengthChosen }
        val lengthOptions = remember { lengthOptions }
        var carParkingChosen by remember { carParkingChosen }
        val carParkingOptions = remember { carParkingOptions }
        var rooftopChosen by remember { rooftopChosen }
        val rooftopOptions = remember { rooftopOptions }
        var dinningRoomChosen by remember { dinningRoomChosen }
        val dinningRoomOptions = remember { dinningRoomOptions }
        var kitchenRoomChosen by remember { kitchenRoomChosen }
        val kitchenRoomOptions = remember { kitchenRoomOptions }
        val addressDetailsScr = remember { addressDetails }
        var isFirstComposing by remember { isFirstComposing }
        val isLoading by remember {
            derivedStateOf {
                uiState is SearchUiState.Loading
            }
        }
        var isNavigateAnotherScr by remember { isNavigateAnotherScr }

        if (addressDetailsScr[0].isNotEmpty()) {
            addressDetailDisplay = addressDetailsScr[0]
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is SearchUiState.InitView -> {
                    onChoiceSortType(searchOption, false)
                    getTypes()
                }
                is SearchUiState.GetTypesSuccess -> {
                    types.run {
                        clear()
                        addAll((uiState as SearchUiState.GetTypesSuccess).data)
                    }
                }
                is SearchUiState.GetSearchDataSuccess -> {
                    searchResult.addAll((uiState as SearchUiState.GetSearchDataSuccess).data)
                    if (isFirstComposing) {
                        isFirstComposing = false
                    } else {
                        isShowSearchOption = false
                    }
                    uiState = SearchUiState.Done
                }
                else -> {}
            }
        }

        LaunchedEffect(key1 = searchResultListState.canScrollForward) {
            if (!searchResultListState.canScrollForward
                && getPagingModel().checkAvailableCallApi()
                && !isFirstComposing
                && !isNavigateAnotherScr
            ) {
                searchPostWithOptions()
            }
        }

        LaunchedEffect(key1 = filter) {
            if (isNavigateAnotherScr) {
                isNavigateAnotherScr = false
            } else {
                delay(SEARCH_TIME)
                searchPostWithOptions(filter, true)
            }
        }

        SearchScreen(
            modifier = modifier,
            isLoading = isLoading,
            onBackClick = remember { onBackClick },
            addressDetail = addressDetailDisplay,
            onComboBoxClick = remember {
                { key ->
                    var title = ""
                    var data = mutableListOf<ItemChoose>()
                    var loadData: (String, () -> Unit) -> Unit = { _, _ -> }
                    var onItemClick: (ItemChoose) -> Unit = { _ -> }
                    when (key) {
                        FIELD_ADDRESS -> {
                            if (addressDetailDisplay.isBlank() || addressDetailDisplay.isEmpty()) {
                                PickAddressViewModel.clearDataChosen()
                            }
                            navigateToPickAddress()
                            isNavigateAnotherScr = true
                        }
                        FIELD_PRICE -> {
                            priceOptions.clear()
                            title = context.getString(R.string.priceTitle)
                            data = priceOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                priceChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_SQUARE -> {
                            squareOptions.clear()
                            title = context.getString(R.string.squareTitle)
                            data = squareOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                squareChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_BED_ROOM -> {
                            bedroomOptions.clear()
                            title = context.getString(R.string.bedroomTitle)
                            data = bedroomOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                bedroomChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_FLOOR -> {
                            floorOptions.clear()
                            title = context.getString(R.string.floorTitle)
                            data = floorOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                floorChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_JURIDICAL -> {
                            juridicalOptions.clear()
                            title = context.getString(R.string.juridicalTitle)
                            data = juridicalOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                juridicalChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_DIRECTION -> {
                            directionOptions.clear()
                            title = context.getString(R.string.directionTitle)
                            data = directionOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                directionChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_STREET_OF_FRONT -> {
                            streetInFrontOptions.clear()
                            title = context.getString(R.string.streetOfFrontTitle)
                            data = streetInFrontOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                streetInFrontChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_WIDTH -> {
                            widthOptions.clear()
                            title = context.getString(R.string.widthTitle)
                            data = widthOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                widthChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_LENGTH -> {
                            lengthOptions.clear()
                            title = context.getString(R.string.lengthTitle)
                            data = lengthOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                lengthChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_CAR_PARKING -> {
                            carParkingOptions.clear()
                            title = context.getString(R.string.carParkingTitle)
                            data = carParkingOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                carParkingChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_ROOFTOP -> {
                            rooftopOptions.clear()
                            title = context.getString(R.string.rooftopTitle)
                            data = rooftopOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                rooftopChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_DINING_ROOM -> {
                            dinningRoomOptions.clear()
                            title = context.getString(R.string.diningRoomTitle)
                            data = dinningRoomOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                dinningRoomChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_KITCHEN_ROOM -> {
                            kitchenRoomOptions.clear()
                            title = context.getString(R.string.kitchenTitle)
                            data = kitchenRoomOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                kitchenRoomChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        else -> {}
                    }
                    if (key != FIELD_ADDRESS) {
                        showDialog(
                            dialog = TypeDialog.ChoiceDataDialog(
                                title = title,
                                data = data,
                                loadData = loadData,
                                onItemClick = onItemClick,
                                isEnableSearchFromApi = (key == Constants.DefaultField.FIELD_STREET)
                            )
                        )
                    }
                }
            },
            onClearData = remember {
                {
                    when (it) {
                        FIELD_ADDRESS -> {
                            PickAddressViewModel.clearDataChosen()
                            addressDetailsScr[0] = ""
                            addressDetailDisplay = ""
                        }
                        FIELD_PRICE -> {
                            priceChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_SQUARE -> {
                            squareChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_BED_ROOM -> {
                            bedroomChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_FLOOR -> {
                            floorChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_JURIDICAL -> {
                            juridicalChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_DIRECTION -> {
                            directionChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_STREET_OF_FRONT -> {
                            streetInFrontChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_WIDTH -> {
                            widthChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_LENGTH -> {
                            lengthChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_CAR_PARKING -> {
                            carParkingChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_ROOFTOP -> {
                            rooftopChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_DINING_ROOM -> {
                            dinningRoomChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_KITCHEN_ROOM -> {
                            kitchenRoomChosen = DEFAULT_ITEM_CHOSEN
                        }
                        else -> {}
                    }
                }
            },
            onSearchClick = remember {
                {
                    searchPostWithOptions(isResetPaging = true)
                }
            },
            isShowSearchOption = isShowSearchOption,
            isShowSearchHighOptionVM = isShowSearchHighOption,
            onTrainingIconTextClick = remember {
                {
                    isShowSearchOption = !isShowSearchOption
                }
            },
            filter = filter,
            onFilterChange = {
                filter = it
            },
            types = types,
            onTypeItemClick = remember { {} },
            sortOptions = sortOptions,
            onSortItemClick = remember {
                {
                    onChoiceSortType(
                        idType = it.id,
                        isCallApi = !isShowSearchHighOption.value
                    )
                }
            },
            searchResultListState = searchResultListState,
            realEstates = searchResult,
            onRealEstateItemClick = remember {
                {
                    isNavigateAnotherScr = true
                    onRealEstateItemClick(it)
                }
            },
            onItemSaveClick = remember {
                {}
            },
            priceChosen = priceChosen,
            squareChosen = squareChosen,
            bedroomChosen = bedroomChosen,
            floorChosen = floorChosen,
            juridicalChosen = juridicalChosen,
            directionChosen = directionChosen,
            streetInFrontChosen = streetInFrontChosen,
            widthChosen = widthChosen,
            lengthChosen = lengthChosen,
            carParkingChosen = carParkingChosen,
            rooftopChosen = rooftopChosen,
            dinningRoomChosen = dinningRoomChosen,
            kitchenRoomChosen = kitchenRoomChosen
        )
    }
}

@Composable
internal fun SearchScreen(
    modifier: Modifier,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    addressDetail: String,
    onComboBoxClick: (String) -> Unit,
    onClearData: (String) -> Unit,
    onSearchClick: () -> Unit,
    isShowSearchOption: Boolean,
    isShowSearchHighOptionVM: MutableState<Boolean>,
    onTrainingIconTextClick: () -> Unit,
    filter: String,
    onFilterChange: (String) -> Unit,
    types: MutableList<ItemChoose>,
    onTypeItemClick: () -> Unit,
    sortOptions: MutableList<ItemChoose>,
    onSortItemClick: (ItemChoose) -> Unit,
    searchResultListState: LazyListState,
    realEstates: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    onItemSaveClick: (Int) -> Unit,
    priceChosen: ItemChoose,
    squareChosen: ItemChoose,
    bedroomChosen: ItemChoose,
    floorChosen: ItemChoose,
    juridicalChosen: ItemChoose,
    directionChosen: ItemChoose,
    streetInFrontChosen: ItemChoose,
    widthChosen: ItemChoose,
    lengthChosen: ItemChoose,
    carParkingChosen: ItemChoose,
    rooftopChosen: ItemChoose,
    dinningRoomChosen: ItemChoose,
    kitchenRoomChosen: ItemChoose
) {
    var isShowSearchHighOption by remember { isShowSearchHighOptionVM }

    BaseScreen(
        toolbar = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = PADDING_HORIZONTAL_SCREEN.dp)
                    .fillMaxWidth()
                    .then(
                        if (isShowSearchHighOption && isShowSearchOption) Modifier.weight(1f)
                        else Modifier
                    )
            ) {
                val (btnBack, edtSearch, searchOptionGroup,
                    tvSortTitle, sortTypes, borderBottom) = createRefs()
                ButtonUnRepeating(onBackClick) {
                    IconButton(
                        onClick = it,
                        modifier = Modifier
                            .size(ICON_ITEM_SIZE.dp)
                            .background(
                                color = RealEstateAppTheme.colors.primary,
                                shape = CircleShape
                            )
                            .constrainAs(btnBack) {
                                start.linkTo(parent.start, PADDING_HORIZONTAL_SCREEN.dp)
                                linkTo(top = edtSearch.top, bottom = edtSearch.bottom)
                            }
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
                            modifier = Modifier
                                .size(Constants.DefaultValue.TRAILING_ICON_SIZE.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                EditTextFullIconBorderRadius(
                    text = filter,
                    onTextChange = onFilterChange,
                    hint = stringResource(id = R.string.searchHint),
                    leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
                    readOnly = false,
                    borderColor = RealEstateAppTheme.colors.primary,
                    leadingIconColor = RealEstateAppTheme.colors.primary,
                    onLeadingIconClick = {},
                    trailingIcon =
                    if (isShowSearchOption) AppIcon.DrawableResourceIcon(RealEstateIcon.DropDown)
                    else AppIcon.DrawableResourceIcon(RealEstateIcon.Config),
                    trailingIconColor = RealEstateAppTheme.colors.primary,
                    onTrailingIconClick = onTrainingIconTextClick,
                    modifier = Modifier
                        .constrainAs(edtSearch) {
                            top.linkTo(parent.top)
                            linkTo(
                                start = btnBack.end,
                                startMargin = MARGIN_VIEW.dp,
                                end = parent.end,
                                endMargin = PADDING_HORIZONTAL_SCREEN.dp
                            )
                            width = Dimension.fillToConstraints
                        }
                )
                Column(
                    modifier = Modifier
                        .constrainAs(searchOptionGroup) {
                            linkTo(
                                top = edtSearch.bottom,
                                topMargin = if (!isShowSearchOption) 0.dp
                                else MARGIN_DIFFERENT_VIEW.dp,
                                bottom = tvSortTitle.top,
                                bottomMargin = MARGIN_VIEW.dp,
                                bias = 0f
                            )
                            height = when {
                                isShowSearchOption && !isShowSearchHighOption -> {
                                    Dimension.wrapContent
                                }
                                isShowSearchOption && isShowSearchHighOption -> {
                                    Dimension.fillToConstraints
                                }
                                else -> {
                                    Dimension.value(0.dp)
                                }
                            }
                            verticalChainWeight = 1f
                            width = Dimension.matchParent
                        }
                        .animateContentSize(
                            spring(
                                stiffness = (TWEEN_ANIMATION_TIME / 2).toFloat(),
                                dampingRatio = 2f
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.typesTitle),
                        style = RealEstateTypography.body1.copy(
                            fontSize = 13.sp,
                            color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                    )
                    Spacing(PADDING_VIEW)
                    ListTypes(
                        types = types,
                        onItemClick = onTypeItemClick,
                        modifier = Modifier
                    )
                    Spacing(MARGIN_VIEW)
                    ComboBox(
                        modifier = Modifier
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                        onItemClick = { onComboBoxClick(FIELD_ADDRESS) },
                        leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Location),
                        title = stringResource(id = R.string.addressTitle),
                        value = addressDetail,
                        hint = stringResource(id = R.string.addressHint),
                        onClearData = { onClearData(FIELD_ADDRESS) }
                    )
                    Spacing(MARGIN_VIEW)
                    ComboBox(
                        modifier = Modifier
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                        onItemClick = { onComboBoxClick(FIELD_PRICE) },
                        leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Money),
                        title = stringResource(id = R.string.priceTitle),
                        value = priceChosen.name,
                        hint = stringResource(
                            id = R.string.chooseHint,
                            stringResource(id = R.string.priceTitle)
                        ),
                        onClearData = { onClearData(FIELD_PRICE) }
                    )
                    Spacing(MARGIN_VIEW)
                    TextIcon(
                        size = 13,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary,
                        text = stringResource(
                            if (isShowSearchHighOption) R.string.closeSearchHighTitle
                            else R.string.openSearchHighTitle
                        ),
                        icon = AppIcon.DrawableResourceIcon(
                            if (isShowSearchHighOption) RealEstateIcon.DropUpBig
                            else RealEstateIcon.DropDownBig
                        ),
                        modifier = Modifier
                            .clickable {
                                isShowSearchHighOption = !isShowSearchHighOption
                            }
                    )
                    if (isShowSearchHighOption) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                                .background(RealEstateAppTheme.colors.bgScreen)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_SQUARE) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Square),
                                title = stringResource(id = R.string.squareTitle),
                                value = squareChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.squareTitle)
                                ),
                                onClearData = { onClearData(FIELD_SQUARE) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_BED_ROOM) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed),
                                title = stringResource(id = R.string.bedroomTitle),
                                value = bedroomChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.bedroomTitle)
                                ),
                                onClearData = { onClearData(FIELD_BED_ROOM) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_FLOOR) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Floors),
                                title = stringResource(id = R.string.floorTitle),
                                value = floorChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.floorTitle)
                                ),
                                onClearData = { onClearData(FIELD_FLOOR) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_JURIDICAL) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Legal),
                                title = stringResource(id = R.string.juridicalTitle),
                                value = juridicalChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.juridicalTitle)
                                ),
                                onClearData = { onClearData(FIELD_JURIDICAL) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_DIRECTION) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Compass),
                                title = stringResource(id = R.string.directionTitle),
                                value = directionChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.directionTitle)
                                ),
                                onClearData = { onClearData(FIELD_DIRECTION) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_STREET_OF_FRONT) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.StreetInFront),
                                title = stringResource(id = R.string.streetOfFrontTitle),
                                value = streetInFrontChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.streetOfFrontTitle)
                                ),
                                onClearData = { onClearData(FIELD_STREET_OF_FRONT) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_WIDTH) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Width),
                                title = stringResource(id = R.string.widthTitle),
                                value = widthChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.widthTitle)
                                ),
                                onClearData = { onClearData(FIELD_WIDTH) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_LENGTH) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Length),
                                title = stringResource(id = R.string.lengthTitle),
                                value = lengthChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.lengthTitle)
                                ),
                                onClearData = { onClearData(FIELD_LENGTH) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_CAR_PARKING) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.CarParking),
                                title = stringResource(id = R.string.carParkingTitle),
                                value = carParkingChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.carParkingTitle)
                                ),
                                onClearData = { onClearData(FIELD_CAR_PARKING) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_ROOFTOP) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Rooftop),
                                title = stringResource(id = R.string.rooftopTitle),
                                value = rooftopChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.rooftopTitle)
                                ),
                                onClearData = { onClearData(FIELD_ROOFTOP) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_DINING_ROOM) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.DiningRoom),
                                title = stringResource(id = R.string.diningTitle),
                                value = dinningRoomChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.diningTitle)
                                ),
                                onClearData = { onClearData(FIELD_DINING_ROOM) }
                            )
                            Spacing(MARGIN_VIEW)
                            ComboBox(
                                onItemClick = { onComboBoxClick(FIELD_KITCHEN_ROOM) },
                                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Kitchen),
                                title = stringResource(id = R.string.kitchenTitle),
                                value = kitchenRoomChosen.name,
                                hint = stringResource(
                                    id = R.string.chooseHint,
                                    stringResource(id = R.string.kitchenTitle)
                                ),
                                onClearData = { onClearData(FIELD_KITCHEN_ROOM) }
                            )
                            Spacing(MARGIN_VIEW)
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.sortTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 13.sp,
                        color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvSortTitle) {
                            bottom.linkTo(sortTypes.top, PADDING_VIEW.dp)
                        },
                )
                LazyRow(
                    modifier = Modifier
                        .wrapContentHeight()
                        .constrainAs(sortTypes) {
                            bottom.linkTo(borderBottom.top, PADDING_VIEW.dp)
                            width = Dimension.matchParent
                        },
                    state = rememberLazyListState(),
                    horizontalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
                    contentPadding = PaddingValues(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                ) {
                    items(
                        items = sortOptions,
                        key = { typeKey ->
                            typeKey.toString()
                        },
                    ) { type ->
                        ItemType(
                            item = type,
                            onItemClick = onSortItemClick
                        )
                    }
                }
                BorderLine(
                    modifier = Modifier
                        .constrainAs(borderBottom) {
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        },
        contentNonScroll = {
            if (realEstates.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(RealEstateAppTheme.colors.bgScreen)
                        .animateContentSize(
                            animationSpec = spring(
                                stiffness = TWEEN_ANIMATION_TIME.toFloat(),
                                dampingRatio = 2f
                            )
                        )
                        .then(
                            if (isShowSearchHighOption && isShowSearchOption) Modifier.height(0.dp)
                            else Modifier.weight(1f)
                        ),
                    state = searchResultListState,
                    verticalArrangement = Arrangement.spacedBy(MARGIN_VIEW.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(PADDING_HORIZONTAL_SCREEN.dp),
                ) {
                    items(
                        items = realEstates,
                        key = { realEstate ->
                            realEstate.id
                        },
                    ) { realEstate ->
                        ItemRealEstate(
                            item = realEstate,
                            onItemClick = onRealEstateItemClick,
                            onSaveClick = onItemSaveClick
                        )
                    }
                }
                Spacing(MARGIN_VIEW)
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = RealEstateAppTheme.colors.progressBar
                        )
                    }
                }
            } else {
                Spacing(MARGIN_DIFFERENT_VIEW)
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .then(
                                if (isShowSearchHighOption && isShowSearchOption) Modifier.height(0.dp)
                                else Modifier.weight(1f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = RealEstateAppTheme.colors.progressBar
                        )
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.emptyTitle),
                        style = RealEstateTypography.body1.copy(
                            color = RealEstateAppTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .then(
                                if (isShowSearchHighOption && isShowSearchOption) Modifier.height(0.dp)
                                else Modifier.weight(1f)
                            )
                    )
                }
            }
        },
        footer = {
            BorderLine()
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = PADDING_HORIZONTAL_SCREEN.dp,
                        vertical = if (isShowSearchOption) PADDING_VIEW.dp else 0.dp
                    )
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = TWEEN_ANIMATION_TIME.toFloat(),
                            dampingRatio = 2f
                        )
                    )
                    .height(
                        if (isShowSearchOption) (TOOLBAR_HEIGHT + PADDING_VIEW).dp
                        else 0.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                ButtonRadius(
                    onClick = onSearchClick,
                    title = stringResource(id = R.string.searchBtnTitle),
                    bgColor = RealEstateAppTheme.colors.primary,
                    modifier = Modifier
                        .height(TOOLBAR_HEIGHT.dp)
                        .fillMaxWidth()
                )
            }
        }
    ) {}
}
