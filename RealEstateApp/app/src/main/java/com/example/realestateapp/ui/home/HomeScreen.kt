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
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.UiState
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
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
        val listType = remember {
            listData.toMutableStateList()
        }
        val uiState by remember { uiState }
//        var refreshing by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        when (uiState) {
            is HomeUiState.GetTypesSuccess -> {
                listType.clear()
                listType.addAll((uiState as HomeUiState.GetTypesSuccess).data)
            }
            else -> {}
        }
        LaunchedEffect(key1 = true) {
            getTypes()
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
) {
    BaseScreen(
        modifier = modifier,
        paddingHorizontal = 0
    ) {
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
            leadingIcon = AppIcon.ImageVectorIcon(RealStateIcon.Search),
            borderColor = Color.Gray.copy(0.3f),
            leadingIconColor = RealEstateAppTheme.colors.primary,
            onLeadingIconClick = {},
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.Config),
            trailingIconColor = RealEstateAppTheme.colors.primary,
            onTrailingIconClick = {

            },
            onDoneAction = {

            }
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = listTypeState,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(
                items = listType,
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
        Spacing(MARGIN_DIFFERENT_VIEW)
        ItemRealEstate(
            item = RealEstateList(
                id = "123",
                image = "https://cdnimg.vietnamplus.vn/uploaded/zatmzy/2020_03_30/aaa.jpg",
                title = "Nhà 2 tầng đường 7.5m kiệt lớn",
                square = 100.0f,
                price = 3_000f,
                bedRooms = 3,
                floors = 3,
                address = "Hòa Xuân, Cẩm Lệ.",
                views = 1234,
                isSaved = false
            )
        )
        if (uiState is HomeUiState.InitView)
            CircularProgressIndicator(
                color = RealEstateAppTheme.colors.progressBar
            )
    }
}
