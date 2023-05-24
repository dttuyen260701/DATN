package com.example.realestateapp.ui.home.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME

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
    viewModel.run {
        var filter by remember { filter }
        val sortOptions = remember { sortOptions }
        val types = remember { typesData }
        var isShowSearchOption by remember { mutableStateOf(true) }
        val uiState by remember { uiState }
        var addressDetailDisplay by remember { detailAddress }
        val addressDetailsScr = remember {
            addressDetails
        }

        if (addressDetailsScr[0].isNotEmpty()) {
            addressDetailDisplay = addressDetailsScr[0]
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is SearchUiState.InitView -> {
                    onChoiceSortType(searchOption)
                    getTypes()
                }
                is SearchUiState.GetTypesSuccess -> {
                    types.run {
                        clear()
                        addAll((uiState as SearchUiState.GetTypesSuccess).data)
                    }
                }
            }
        }

        SearchScreen(
            modifier = modifier,
            onBackClick = onBackClick,
            navigateToPickAddress = navigateToPickAddress,
            addressDetail = addressDetailDisplay,
            onClearData = remember {
                {
                    when (it) {
                        FIELD_ADDRESS -> {
                            PickAddressViewModel.clearDataChosen()
                            addressDetailsScr[0] = ""
                            addressDetailDisplay = ""
                        }
                        else -> {}
                    }
                }
            },
            onSearchClick = remember {
                {}
            },
            isShowSearchOption = isShowSearchOption,
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
            onTypeItemClick = remember {
                {}
            },
            sortOptions = sortOptions,
            onSortItemClick = remember {
                {
                    onChoiceSortType(it.id)
                }
            },
            realEstates = mutableListOf(
                RealEstateList(
                    id = 1,
                    imageUrl = "https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/yqjvzdjwp/2022_05_01/bernabeu-real-madrid-2550.jpg",
                    title = "Bán rất nhiều đất",
                    createdDate = "20/05/2023",
                    square = 123f,
                    price = 1_200_000f,
                    bedRooms = null,
                    floors = null,
                    address = "54 Nguyễn Lương Bằng",
                    views = 12,
                    isSaved = true,
                ),
                RealEstateList(
                    id = 2,
                    imageUrl = "https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/yqjvzdjwp/2022_05_01/bernabeu-real-madrid-2550.jpg",
                    title = "Bán rất nhiều đất",
                    createdDate = "20/05/2023",
                    square = 123f,
                    price = 1_200_000f,
                    bedRooms = null,
                    floors = null,
                    address = "54 Nguyễn Lương Bằng",
                    views = 12,
                    isSaved = true,
                ),
                RealEstateList(
                    id = 3,
                    imageUrl = "https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/yqjvzdjwp/2022_05_01/bernabeu-real-madrid-2550.jpg",
                    title = "Bán rất nhiều đất",
                    createdDate = "20/05/2023",
                    square = 123f,
                    price = 1_200_000f,
                    bedRooms = null,
                    floors = null,
                    address = "54 Nguyễn Lương Bằng",
                    views = 12,
                    isSaved = true,
                ),
                RealEstateList(
                    id = 4,
                    imageUrl = "https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/yqjvzdjwp/2022_05_01/bernabeu-real-madrid-2550.jpg",
                    title = "Bán rất nhiều đất",
                    createdDate = "20/05/2023",
                    square = 123f,
                    price = 1_200_000f,
                    bedRooms = null,
                    floors = null,
                    address = "54 Nguyễn Lương Bằng",
                    views = 12,
                    isSaved = true,
                )
            ),
            onRealEstateItemClick = remember { onRealEstateItemClick },
            onItemSaveClick = remember {
                {}
            }
        )
    }
}

@Composable
internal fun SearchScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    navigateToPickAddress: () -> Unit,
    addressDetail: String,
    onClearData: (String) -> Unit,
    onSearchClick: () -> Unit,
    isShowSearchOption: Boolean,
    onTrainingIconTextClick: () -> Unit,
    filter: String,
    onFilterChange: (String) -> Unit,
    types: MutableList<ItemChoose>,
    onTypeItemClick: () -> Unit,
    sortOptions: MutableList<ItemChoose>,
    onSortItemClick: (ItemChoose) -> Unit,
    realEstates: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    onItemSaveClick: (Int) -> Unit
) {
    BaseScreen(
        toolbar = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = PADDING_HORIZONTAL_SCREEN.dp)
                    .fillMaxWidth()
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
                            top.linkTo(edtSearch.bottom, MARGIN_DIFFERENT_VIEW.dp)
                            height =
                                if (isShowSearchOption) Dimension.wrapContent else Dimension.value(0.dp)
                            width = Dimension.matchParent
                        }
                        .animateContentSize(
                            spring(
                                stiffness = (TWEEN_ANIMATION_TIME / 2).toFloat(),
                                dampingRatio = 2f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.typesTitle),
                        style = RealEstateTypography.body1.copy(
                            fontSize = 13.sp,
                            color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
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
                        onItemClick = navigateToPickAddress,
                        leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Location),
                        title = stringResource(id = R.string.addressTitle),
                        value = addressDetail,
                        hint = stringResource(id = R.string.addressHint),
                        onClearData = { onClearData(FIELD_ADDRESS) }
                    )
                    Spacing(MARGIN_VIEW)

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
                            top.linkTo(
                                searchOptionGroup.bottom,
                            )
                        },
                )
                LazyRow(
                    modifier = Modifier
                        .wrapContentHeight()
                        .constrainAs(sortTypes) {
                            top.linkTo(tvSortTitle.bottom, PADDING_VIEW.dp)
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
                            linkTo(
                                top = sortTypes.bottom,
                                topMargin = MARGIN_VIEW.dp,
                                bottom = parent.bottom,
                                bias = 1f
                            )
                        }
                )
            }
        },
        contentNonScroll = {
            LazyColumn(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(RealEstateAppTheme.colors.bgScreen),
                state = rememberLazyListState(),
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
