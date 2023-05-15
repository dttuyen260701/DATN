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
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.run {
        val user by remember { getUser() }
        var filter by remember { filter }
        val listTypeState = rememberLazyListState()
        val listType = remember { listTypeData }
        val listRealEstateLatest = remember { listRealEstateLatest }
        val listRealEstateMostView = remember { listRealEstateMostView }
        val listRealEstateHighestPrice =
            remember { listRealEstateHighestPrice }
        val listRealEstateLowestPrice = remember { listRealEstateLowestPrice }
        var uiState by remember { uiState }
        val coroutineScope = rememberCoroutineScope()

        when (uiState) {
            is HomeUiState.InitView -> {
                backgroundSignIn()
            }
            is HomeUiState.DoneSignInBackground -> {
                getTypes()
            }
            is HomeUiState.GetTypesSuccess -> {
                listType.clear()
                listType.addAll((uiState as HomeUiState.GetTypesSuccess).data)
                getPostsWOptions(isLatest = true)
            }
            is HomeUiState.GetLatestSuccess -> {
                listRealEstateLatest.clear()
                listRealEstateLatest.addAll((uiState as HomeUiState.GetLatestSuccess).data)
                getPostsWOptions(isMostView = true)
            }
            is HomeUiState.GetMostViewSuccess -> {
                listRealEstateMostView.clear()
                listRealEstateMostView.addAll((uiState as HomeUiState.GetMostViewSuccess).data)
                getPostsWOptions(isHighestPrice = true)
            }
            is HomeUiState.GetHighestPriceSuccess -> {
                listRealEstateHighestPrice.clear()
                listRealEstateHighestPrice.addAll((uiState as HomeUiState.GetHighestPriceSuccess).data)
                getPostsWOptions(isLowestPrice = true)
            }
            is HomeUiState.GetLowestPriceSuccess -> {
                listRealEstateLowestPrice.clear()
                listRealEstateLowestPrice.addAll((uiState as HomeUiState.GetLowestPriceSuccess).data)
                uiState = HomeUiState.Success
            }
            else -> {}
        }

        HomeScreen(
            modifier = modifier,
            uiState = uiState,
            user = user,
            filter = filter,
            onFilterChange = {
                filter = it
            },
            listTypeState = listTypeState,
            listType = listType,
            onItemTypeClick = remember {
                {
                    if (listType.indexOf(it) != -1) {
                        val newValue = it.copy(isSelected = !it.isSelected)
                        listType[listType.indexOf(it)] = newValue
                        listType.run {
                            sortBy { item ->
                                item.name
                            }
                            sortByDescending { item ->
                                item.isSelected
                            }
                            coroutineScope.launch {
                                delay(TWEEN_ANIMATION_TIME.toLong())
                                listTypeState.animateScrollToItem(0)
                            }
                        }
                    }
                }
            },
            listLRealEstateLatest = listRealEstateLatest,
            listRealEstateMostView = listRealEstateMostView,
            listRealEstateHighestPrice = listRealEstateHighestPrice,
            listRealEstateLowestPrice = listRealEstateLowestPrice,
            onItemRealEstateClick = remember {
                {}
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    user: User?,
    filter: String,
    onFilterChange: (String) -> Unit,
    listTypeState: LazyListState,
    listType: MutableList<ItemChoose>,
    onItemTypeClick: (ItemChoose) -> Unit,
    listLRealEstateLatest: MutableList<RealEstateList>,
    listRealEstateMostView: MutableList<RealEstateList>,
    listRealEstateHighestPrice: MutableList<RealEstateList>,
    listRealEstateLowestPrice: MutableList<RealEstateList>,
    onItemRealEstateClick: (RealEstateList) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        paddingHorizontal = 0,
        toolbar = {
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
                    Text(
                        text = stringResource(id = R.string.welcomeTitle),
                        style = RealEstateTypography.h1.copy(
                            fontSize = 23.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .constrainAs(tvWelcome) {
                                start.linkTo(parent.start)
                                linkTo(parent.top, verticalGuideLine)
                            }
                    )
                    Text(
                        text = fullName,
                        style = RealEstateTypography.h1.copy(
                            fontSize = 25.sp,
                            color = RealEstateAppTheme.colors.primary,
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                            .constrainAs(tvName) {
                                start.linkTo(parent.start)
                                linkTo(verticalGuideLine, parent.bottom)
                            }
                    )
                    ImageProfile(
                        size = 50,
                        model = user.imgUrl ?: "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f, true)
                            .constrainAs(imgUser) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
            Spacing(MARGIN_DIFFERENT_VIEW)
            EditTextFullIconBorderRadius(
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                text = filter,
                onTextChange = onFilterChange,
                hint = stringResource(id = R.string.searchHint),
                leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
                borderColor = Color.Gray.copy(0.3f),
                leadingIconColor = RealEstateAppTheme.colors.primary,
                onLeadingIconClick = {},
                trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Config),
                trailingIconColor = RealEstateAppTheme.colors.primary,
                onTrailingIconClick = {

                },
                onDoneAction = {

                }
            )
            listType.let {
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
        }
    ) {
        listLRealEstateLatest.let {
            if (it.size > 0) {
                Spacing(MARGIN_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.latestTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {

                    },
                    listRealEstate = it,
                    onItemClick = onItemRealEstateClick
                )
            }
        }
        listRealEstateMostView.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.mostViewTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {

                    },
                    listRealEstate = it,
                    onItemClick = onItemRealEstateClick
                )
            }
        }
        listRealEstateHighestPrice.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.highestPriceTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {

                    },
                    listRealEstate = it,
                    onItemClick = onItemRealEstateClick
                )
            }
        }
        listRealEstateLowestPrice.let {
            if (it.size > 0) {
                Spacing(MARGIN_DIFFERENT_VIEW)
                ListItemHome(
                    title = stringResource(id = R.string.lowestPriceTitle),
                    btnTitle = stringResource(id = R.string.btnSeeAll),
                    btnClick = {

                    },
                    listRealEstate = it,
                    onItemClick = onItemRealEstateClick
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
