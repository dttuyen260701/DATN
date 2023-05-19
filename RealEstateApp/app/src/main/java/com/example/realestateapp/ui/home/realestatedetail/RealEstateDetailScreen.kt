package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.models.view.RealEstateProperty
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.BOTTOM_ICON_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME
import com.google.android.gms.maps.model.LatLng

/**
 * Created by tuyen.dang on 5/16/2023.
 */

@Composable
internal fun RealEstateDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    realEstateId: Int,
    onRealEstateItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    viewModel.run {
        val user by remember { getUser() }
        val realEstatesSamePrice = remember { realEstatesSamePrice }
        val realEstatesCluster = remember { realEstatesCluster }
        var uiState by remember { uiState }

        when (uiState) {
            is RealEstateDetailUiState.InitView -> {
                getRealEstateDetail(realEstateId)
            }
            is RealEstateDetailUiState.GetRealEstateDetailSuccess -> {
                getRealEstatesSamePrice(12f)
            }
            is RealEstateDetailUiState.GetSamePriceSuccess -> {
                realEstatesSamePrice.clear()
                realEstatesSamePrice.addAll((uiState as RealEstateDetailUiState.GetSamePriceSuccess).data)
                getRealEstatesCluster(2)
            }
            is RealEstateDetailUiState.GetClusterSuccess -> {
                realEstatesCluster.clear()
                realEstatesCluster.addAll((uiState as RealEstateDetailUiState.GetClusterSuccess).data)
                uiState = RealEstateDetailUiState.Success
            }
            else -> {}
        }

        RealEstateDetailScreen(
            modifier = modifier,
            user = user,
            itemProperties = mutableListOf(
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 Tầng",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                ),
                RealEstateProperty(
                    title = "3 PN",
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed)
                )
            ),
            realEstatesSamePrice = realEstatesSamePrice,
            realEstatesCluster = realEstatesCluster,
            onRealEstateItemClick = remember { onRealEstateItemClick },
            onBackClick = remember { onBackClick },
            onDirectClick = remember { {} },
            onCallClick = remember { {} },
            onWarningClick = remember { {} }
        )
    }
}

@Composable
internal fun RealEstateDetailScreen(
    modifier: Modifier = Modifier,
    user: User?,
    itemProperties: MutableList<RealEstateProperty>,
    realEstatesSamePrice: MutableList<RealEstateList>,
    realEstatesCluster: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onDirectClick: () -> Unit,
    onCallClick: () -> Unit,
    onWarningClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val transition = updateTransition(scrollState.value, label = "")
    val threadAnimation = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
            .toInt() * 3 / 4
    }
    val title by remember {
        derivedStateOf {
            if (scrollState.value > threadAnimation) "Nhượng đất cho anh em thiện không lành!"
            else ""
        }
    }
    val bgToolbar by transition.animateColor(
        transitionSpec = {
            spring(
                stiffness = 50f,
                dampingRatio = 2f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) RealEstateAppTheme.colors.primary else Color.Transparent
    }
    val bgIcon by transition.animateColor(
        transitionSpec = {
            spring(
                stiffness = TWEEN_ANIMATION_TIME.toFloat(),
                dampingRatio = 2f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) Color.Transparent else RealEstateAppTheme.colors.bgIconsBlack50
    }
    val toolbarHeight by transition.animateInt(
        transitionSpec = {
            spring(
                stiffness = 700f,
                dampingRatio = 0.7f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) TOOLBAR_HEIGHT else TOOLBAR_HEIGHT * 2
    }
    val paddingToolbar by transition.animateInt(
        transitionSpec = {
            spring(
                stiffness = 700f,
                dampingRatio = 0.7f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) PADDING_VIEW else PADDING_HORIZONTAL_SCREEN
    }

    BaseScreen(
        modifier = modifier,
        scrollState = scrollState,
        paddingHorizontal = 0,
        footer = {
            BorderLine(
                height = 0.2f,
                bgColor = RealEstateAppTheme.colors.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BOTTOM_ICON_SIZE.dp)
                    .background(RealEstateAppTheme.colors.bgScrPrimaryLight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    onClick = onDirectClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(
                        vertical = 0.dp,
                        horizontal = MARGIN_VIEW.dp
                    )
                ) {
                    TextIcon(
                        text = stringResource(id = R.string.directTitle),
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Direct),
                        size = 14,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(0.2f.dp)
                        .fillMaxHeight()
                        .background(RealEstateAppTheme.colors.primary)
                )
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    onClick = onCallClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(
                        vertical = 0.dp,
                        horizontal = MARGIN_VIEW.dp
                    )
                ) {
                    TextIcon(
                        text = stringResource(id = R.string.callTitle),
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Phone),
                        size = 14,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(0.2f.dp)
                        .fillMaxHeight()
                        .background(RealEstateAppTheme.colors.primary)
                )
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    onClick = onWarningClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(
                        vertical = 0.dp,
                        horizontal = MARGIN_VIEW.dp
                    )
                ) {
                    TextIcon(
                        text = stringResource(id = R.string.reportTitle),
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Warning),
                        size = 14,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary
                    )
                }
            }
        }
    ) {
        SlideShowImage(
            photos = mutableListOf(
                "https://cdnimg.vietnamplus.vn/t870/uploaded/mzdic/2022_05_29/realvodich1.jpg",
                "https://cdnimg.vietnamplus.vn/uploaded/mtpyelagtpy/2018_05_27/realmadridtrophy.jpg",
                "https://cdnimg.vietnamplus.vn/uploaded/mzdic/2022_05_27/realmadrid2705.jpg",
                "https://vn112.com/wp-content/uploads/2018/01/pxsolidwhiteborderedsvg-15161310048lcp4.png"
            )
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = PADDING_HORIZONTAL_SCREEN.dp)
        ) {
            val (
                tvName, tvViews, tvAddress, tvCreatedDate,
                tvPrice, tvPostId, borderTopGrid, gridLayout,
                borderBottomGrid, tvDescriptionTitle,
                tvDescription, samePriceList, clusterList
            ) = createRefs()
            val (
                tvMapTitle, mapView, tvContact, tvNameUser, tvPhoneUser
            ) = createRefs()
            Text(
                text = "Nhượng đất Cho anh em thiện không lành!"
                    ?: stringResource(id = R.string.titleDefault),
                style = RealEstateTypography.body1.copy(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvName) {
                        top.linkTo(parent.top, PADDING_HORIZONTAL_SCREEN.dp)
                        linkTo(
                            start = parent.start,
                            end = tvViews.start,
                            bias = 0f,
                            startMargin = PADDING_HORIZONTAL_SCREEN.dp
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            TextIcon(
                text = "123",
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Visibility),
                isIconFirst = false,
                size = 14,
                modifier = Modifier
                    .constrainAs(tvViews) {
                        top.linkTo(tvName.top)
                        end.linkTo(parent.end, PADDING_HORIZONTAL_SCREEN.dp)
                        baseline.linkTo(tvName.baseline)
                    }
            )
            Text(
                text = "Ngô Sĩ Liên Ngô Sĩ Liên Ngô Sĩ Liên Ngô Sĩ Liên Ngô Sĩ Liên Ngô Sĩ Liên Ngô Sĩ Liên",
                style = RealEstateTypography.body1.copy(
                    fontSize = 13.sp,
                    color = Color.Black.copy(0.8f),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvAddress) {
                        top.linkTo(tvName.bottom)
                        linkTo(
                            start = tvName.start,
                            end = tvPrice.start,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = stringResource(id = R.string.idPostTitle, "1231245"),
                style = RealEstateTypography.body1.copy(
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvPostId) {
                        top.linkTo(tvAddress.bottom)
                        linkTo(
                            start = tvAddress.start,
                            end = tvPostId.end,
                            bias = 0f
                        )
                    }
            )
            Text(
                text = "16/05/2023",
                style = RealEstateTypography.body1.copy(
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvCreatedDate) {
                        top.linkTo(tvPostId.top)
                        linkTo(
                            start = tvPostId.end,
                            startMargin = MARGIN_VIEW.dp,
                            end = tvAddress.end,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 0f
                        )
                    }
            )
            TextIcon(
                text = "23,3 tỷ",
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Dollar),
                size = 23,
                textColor = RealEstateAppTheme.colors.primary,
                iconTint = RealEstateAppTheme.colors.primary,
                modifier = Modifier
                    .constrainAs(tvPrice) {
                        linkTo(top = tvAddress.top, bottom = tvPostId.bottom, bias = 0f)
                        linkTo(
                            start = tvAddress.end,
                            startMargin = MARGIN_VIEW.dp,
                            end = parent.end,
                            endMargin = PADDING_HORIZONTAL_SCREEN.dp,
                            bias = 1f
                        )
                    }
            )
            BorderLine(
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(borderTopGrid) {
                        top.linkTo(tvPostId.bottom, MARGIN_DIFFERENT_VIEW.dp)
                    },
                height = 0.2f
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MARGIN_VIEW.dp),
                contentPadding = PaddingValues(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(gridLayout) {
                        top.linkTo(borderTopGrid.bottom, MARGIN_VIEW.dp)
                    }
            ) {
                items(itemProperties.size) {
                    TextIconVertical(
                        text = itemProperties[it].title,
                        icon = itemProperties[it].leadingIcon,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary,
                        bgIconTint = RealEstateAppTheme.colors.bgTextField,
                        size = 14
                    )
                }
            }
            BorderLine(
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(borderBottomGrid) {
                        top.linkTo(gridLayout.bottom, MARGIN_VIEW.dp)
                    },
                height = 0.2f
            )
            Text(
                text = stringResource(id = R.string.descriptionTitle),
                style = RealEstateTypography.body1.copy(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvDescriptionTitle) {
                        top.linkTo(borderBottomGrid.bottom, MARGIN_DIFFERENT_VIEW.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = """
                    LazyVerticalGrid lets you specify a width for items, and then the grid will fit as many columns as possible. Any remaining width is distributed equally among the columns, after the number of columns is calculated. This adaptive way of sizing is especially useful for displaying sets of items across different screen sizes.
                    If you know the exact amount of columns to be used, you can instead provide an instance of GridCells.Fixed containing the number of required columns.
                    If your design requires only certain items to have non-standard dimensions, you can use the grid support for providing custom column spans for items. Specify the column span with the span parameter of the LazyGridScope DSL item and items methods. maxLineSpan, one of the span scope’s values, is particularly useful when you're using adaptive sizing, because the number of columns is not fixed. This example shows how to provide a full row span:
                """.trimIndent(),
                style = RealEstateTypography.body1.copy(
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvDescription) {
                        top.linkTo(tvDescriptionTitle.bottom)
                        linkTo(
                            start = tvDescriptionTitle.start,
                            end = tvDescriptionTitle.end,
                            bias = 0f
                        )
                    }
            )
            Text(
                text = stringResource(id = R.string.mapTitle),
                style = RealEstateTypography.body1.copy(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvMapTitle) {
                        top.linkTo(tvDescription.bottom, MARGIN_DIFFERENT_VIEW.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            MapviewShowMarker(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(mapView) {
                        top.linkTo(
                            tvMapTitle.bottom, MARGIN_VIEW.dp
                        )
                    },
                location = LatLng(16.084382, 108.2376697)
            )
            Text(
                text = stringResource(id = R.string.contactTitle),
                style = RealEstateTypography.body1.copy(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvContact) {
                        top.linkTo(mapView.bottom, MARGIN_DIFFERENT_VIEW.dp)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            TextIcon(
                text = "Le Minh Loi",
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
                textColor = Color.Black.copy(0.8f),
                size = 13,
                iconTint = Color.Black.copy(0.8f),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvNameUser) {
                        top.linkTo(tvContact.bottom, MARGIN_VIEW.dp)
                    }
            )
            TextIcon(
                text = if (user != null) "012389324" else "********",
                icon = AppIcon.ImageVectorIcon(RealEstateIcon.PhoneOutLine),
                textColor = Color.Black.copy(0.8f),
                size = 13,
                iconTint = Color.Black.copy(0.8f),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvPhoneUser) {
                        top.linkTo(tvNameUser.bottom, MARGIN_VIEW.dp)
                    }
            )
            realEstatesSamePrice.let {
                if (it.size > 0) {
                    ListItemHome(
                        title = stringResource(id = R.string.samePriceTitle),
                        listRealEstate = it,
                        onItemClick = { item -> onRealEstateItemClick(item.id) },
                        modifier = Modifier
                            .constrainAs(samePriceList) {
                                top.linkTo(
                                    tvPhoneUser.bottom, MARGIN_DIFFERENT_VIEW.dp
                                )
                            }
                    )
                }
            }
            realEstatesCluster.let {
                if (it.size > 0) {
                    ListItemHome(
                        title = stringResource(id = R.string.suggestTitle),
                        listRealEstate = it,
                        onItemClick = { item -> onRealEstateItemClick(item.id) },
                        modifier = Modifier
                            .constrainAs(clusterList) {
                                top.linkTo(
                                    (if (realEstatesSamePrice.size > 0) samePriceList else tvDescription).bottom,
                                    (if (realEstatesSamePrice.size > 0) MARGIN_VIEW else MARGIN_DIFFERENT_VIEW).dp
                                )
                            }
                    )
                }
            }
        }
    }

    ToolbarWAnimation(
        modifier = Modifier
            .padding(horizontal = paddingToolbar.dp),
        toolbarHeight = toolbarHeight,
        title = title,
        bgColor = bgToolbar,
        leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
        onLeadingIconClick = onBackClick,
        trainingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.PostSavedOutline),
        onTrainingIconClick = {

        },
        bgIcon = bgIcon
    )
}
