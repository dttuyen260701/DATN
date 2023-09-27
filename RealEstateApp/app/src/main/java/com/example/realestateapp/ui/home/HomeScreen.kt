package com.example.realestateapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.realestateapp.ui.base.UiEffect
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: (SearchOption) -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    navigateProfile: () -> Unit
) {
    viewModel.run {
        val user by remember { getUser() }
        val types = remember { typesData }
        val realEstatesLatest = remember { realEstatesLatest }
        val realEstatesMostView = remember { realEstatesMostView }
        val realEstatesHighestPrice = remember { realEstatesHighestPrice }
        val realEstatesLowestPrice = remember { realEstatesLowestPrice }
        val uiState by uiEffect.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is HomeUiEffect.InitView -> {
                    backgroundSignIn()
                }
                is HomeUiEffect.DoneSignInBackground -> {
                    getTypes()
                }
                is HomeUiEffect.GetTypesSuccess -> {
                    types.run {
                        clear()
                        addAll((uiState as HomeUiEffect.GetTypesSuccess).data)
                    }
                    getPostsWOptions(isLatest = true)
                }
                is HomeUiEffect.GetLatestSuccess -> {
                    realEstatesLatest.run {
                        clear()
                        addAll((uiState as HomeUiEffect.GetLatestSuccess).data)
                    }
                    getPostsWOptions(isMostView = true)
                }
                is HomeUiEffect.GetMostViewSuccess -> {
                    realEstatesMostView.run {
                        clear()
                        addAll((uiState as HomeUiEffect.GetMostViewSuccess).data)
                    }
                    getPostsWOptions(isHighestPrice = true)
                }
                is HomeUiEffect.GetHighestPriceSuccess -> {
                    realEstatesHighestPrice.run {
                        clear()
                        addAll((uiState as HomeUiEffect.GetHighestPriceSuccess).data)
                    }
                    getPostsWOptions(isLowestPrice = true)
                }
                is HomeUiEffect.GetLowestPriceSuccess -> {
                    realEstatesLowestPrice.run {
                        clear()
                        addAll((uiState as HomeUiEffect.GetLowestPriceSuccess).data)
                    }
                }
                else -> {}
            }
        }

        HomeScreen(
            modifier = modifier,
            uiEffect = uiState,
            user = user,
            types = types,
            onItemTypeClick = remember {
                {
                    getPostsWOptions(isLatest = true)
                }
            },
            realEstatesLatest = realEstatesLatest,
            realEstatesMostView = realEstatesMostView,
            realEstatesHighestPrice = realEstatesHighestPrice,
            realEstatesLowestPrice = realEstatesLowestPrice,
            onRealEstateItemClick = remember { onRealEstateItemClick },
            navigateToSearch = remember { navigateToSearch },
            navigateProfile = navigateProfile
        )
    }
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiEffect: UiEffect,
    user: User?,
    types: MutableList<ItemChoose>,
    onItemTypeClick: () -> Unit,
    realEstatesLatest: MutableList<RealEstateList>,
    realEstatesMostView: MutableList<RealEstateList>,
    realEstatesHighestPrice: MutableList<RealEstateList>,
    realEstatesLowestPrice: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    navigateToSearch: (SearchOption) -> Unit,
    navigateProfile: () -> Unit
) {

    BaseScreen(
        modifier = modifier,
        paddingHorizontal = 0,
        verticalArrangement = Arrangement.Top,
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
                            }
                            .clickable { navigateProfile() }
                    )
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
                    ListTypes(
                        types = types,
                        onItemClick = onItemTypeClick
                    )
                }
            }
            Spacing(MARGIN_VIEW)
            BorderLine()
        }
    ) {
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
                    onItemClick = { id -> onRealEstateItemClick(id) }
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
                    onItemClick = { id -> onRealEstateItemClick(id) }
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
                    onItemClick = { id -> onRealEstateItemClick(id) }
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
                    onItemClick = { id -> onRealEstateItemClick(id) }
                )
            }
        }
        Spacing(MARGIN_DIFFERENT_VIEW)
        if (uiEffect is HomeUiEffect.Loading) {
            CircularProgressIndicator(
                color = RealEstateAppTheme.colors.progressBar
            )
        }
    }
}
