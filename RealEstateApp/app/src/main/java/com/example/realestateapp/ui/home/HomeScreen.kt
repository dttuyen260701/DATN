package com.example.realestateapp.ui.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.SearchOption
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: (SearchOption) -> Unit,
    onRealEstateItemClick: (Int) -> Unit
) {
    viewModel.run {
        val user by remember { getUser() }
        val listTypeState = rememberLazyListState()
        val types = remember { typesData }
        val realEstatesLatest = remember { realEstatesLatest }
        val realEstatesMostView = remember { realEstatesMostView }
        val realEstatesHighestPrice = remember { realEstatesHighestPrice }
        val realEstatesLowestPrice = remember { realEstatesLowestPrice }
        val uiState by remember { uiState }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is HomeUiState.InitView -> {
                    backgroundSignIn()
                }
                is HomeUiState.DoneSignInBackground -> {
                    getTypes()
                }
                is HomeUiState.GetTypesSuccess -> {
                    types.clear()
                    types.addAll((uiState as HomeUiState.GetTypesSuccess).data)
                    getPostsWOptions(isLatest = true)
                }
                is HomeUiState.GetLatestSuccess -> {
                    realEstatesLatest.clear()
                    realEstatesLatest.addAll((uiState as HomeUiState.GetLatestSuccess).data)
                    getPostsWOptions(isMostView = true)
                }
                is HomeUiState.GetMostViewSuccess -> {
                    realEstatesMostView.clear()
                    realEstatesMostView.addAll((uiState as HomeUiState.GetMostViewSuccess).data)
                    getPostsWOptions(isHighestPrice = true)
                }
                is HomeUiState.GetHighestPriceSuccess -> {
                    realEstatesHighestPrice.clear()
                    realEstatesHighestPrice.addAll((uiState as HomeUiState.GetHighestPriceSuccess).data)
                    getPostsWOptions(isLowestPrice = true)
                }
                is HomeUiState.GetLowestPriceSuccess -> {
                    realEstatesLowestPrice.clear()
                    realEstatesLowestPrice.addAll((uiState as HomeUiState.GetLowestPriceSuccess).data)
                }
            }
        }

        HomeScreen(modifier = modifier,
            uiState = uiState,
            user = user,
            listTypeState = listTypeState,
            types = types,
            onItemTypeClick = remember {
                {
                    if (types.indexOf(it) != -1) {
                        val newValue = it.copy(isSelected = !it.isSelected)
                        types[types.indexOf(it)] = newValue
                        types.run {
                            sortBy { item ->
                                item.id
                            }
                            sortByDescending { item ->
                                item.isSelected
                            }
                            coroutineScope.launch {
                                delay(TWEEN_ANIMATION_TIME.toLong())
                                listTypeState.animateScrollToItem(0)
                                getPostsWOptions(isLatest = true)
                            }
                        }
                    }
                }
            },
            realEstatesLatest = realEstatesLatest,
            realEstatesMostView = realEstatesMostView,
            realEstatesHighestPrice = realEstatesHighestPrice,
            realEstatesLowestPrice = realEstatesLowestPrice,
            onRealEstateItemClick = remember { onRealEstateItemClick },
            onItemSaveClick = remember { {} },
            navigateToSearch = remember { navigateToSearch }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    user: User?,
    listTypeState: LazyListState,
    types: MutableList<ItemChoose>,
    onItemTypeClick: (ItemChoose) -> Unit,
    realEstatesLatest: MutableList<RealEstateList>,
    realEstatesMostView: MutableList<RealEstateList>,
    realEstatesHighestPrice: MutableList<RealEstateList>,
    realEstatesLowestPrice: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    onItemSaveClick: (Int) -> Unit,
    navigateToSearch: (SearchOption) -> Unit
) {
    BaseScreen(modifier = modifier, paddingHorizontal = 0, toolbar = {
        user?.run {
            Spacing(MARGIN_DIFFERENT_VIEW)
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .background(Color.Transparent)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                val (imgUser, tvWelcome, tvName) = createRefs()
                val verticalGuideLine = createGuidelineFromTop(0.5f)
                Text(text = stringResource(id = R.string.welcomeTitle),
                    style = RealEstateTypography.h1.copy(
                        fontSize = 23.sp, color = Color.Black
                    ),
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .constrainAs(tvWelcome) {
                            start.linkTo(parent.start)
                            linkTo(parent.top, verticalGuideLine)
                        })
                Text(text = fullName, style = RealEstateTypography.h1.copy(
                    fontSize = 25.sp,
                    color = RealEstateAppTheme.colors.primary,
                    fontStyle = FontStyle.Italic
                ), modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(tvName) {
                        start.linkTo(parent.start)
                        linkTo(verticalGuideLine, parent.bottom)
                    })
                ImageProfile(size = 50,
                    model = user.imgUrl ?: "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .constrainAs(imgUser) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        })
            }
        }
        Spacing(MARGIN_DIFFERENT_VIEW)
        EditTextFullIconBorderRadius(
            modifier = Modifier.padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
            onTextChange = {},
            hint = stringResource(id = R.string.searchHint),
            leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
            borderColor = RealEstateAppTheme.colors.primary,
            readOnly = true,
            leadingIconColor = RealEstateAppTheme.colors.primary,
            onLeadingIconClick = {
                navigateToSearch(SearchOption.LATEST)
            },
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Config),
            trailingIconColor = RealEstateAppTheme.colors.primary,
            onTrailingIconClick = {
                navigateToSearch(SearchOption.LATEST)
            },
            onItemClick = {
                navigateToSearch(SearchOption.LATEST)
            }
        )
        types.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    state = listTypeState,
                    horizontalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
                    contentPadding = PaddingValues(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                ) {
                    items(
                        items = it,
                        key = { typeKey ->
                            typeKey.toString()
                        },
                    ) { type ->
                        ItemType(
                            item = type,
                            onItemClick = onItemTypeClick,
                            modifier = Modifier.animateItemPlacement(
                                tween(durationMillis = TWEEN_ANIMATION_TIME)
                            )
                        )
                    }
                }
            }
        }
        Spacing(MARGIN_VIEW)
        BorderLine()
    }) {
        realEstatesLatest.let {
            if (it.size > 0) {
                Spacing(MARGIN_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.latestTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {
                        navigateToSearch(SearchOption.LATEST)
                    },
                    listRealEstate = it,
                    onItemClick = { id -> onRealEstateItemClick(id) },
                    onItemSaveClick = { id -> onItemSaveClick(id) }
                )
            }
        }
        realEstatesMostView.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.mostViewTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {
                        navigateToSearch(SearchOption.MOST_VIEW)
                    },
                    listRealEstate = it,
                    onItemClick = { id -> onRealEstateItemClick(id) },
                    onItemSaveClick = { id -> onItemSaveClick(id) }
                )
            }
        }
        realEstatesHighestPrice.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.highestPriceTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {
                        navigateToSearch(SearchOption.HIGHEST_PRICE)
                    },
                    listRealEstate = it,
                    onItemClick = { id -> onRealEstateItemClick(id) },
                    onItemSaveClick = { id -> onItemSaveClick(id) }
                )
            }
        }
        realEstatesLowestPrice.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.lowestPriceTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {
                        navigateToSearch(SearchOption.LOWEST_PRICE)
                    },
                    listRealEstate = it,
                    onItemClick = { id -> onRealEstateItemClick(id) },
                    onItemSaveClick = { id -> onItemSaveClick(id) }
                )
            }
        }
        Spacing(MARGIN_DIFFERENT_VIEW)
        if (uiState is HomeUiState.Loading) {
            CircularProgressIndicator(
                color = RealEstateAppTheme.colors.progressBar
            )
        }
    }
}
