package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.realestateapp.data.models.view.RealEstateProperty
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME

/**
 * Created by tuyen.dang on 5/16/2023.
 */

@Composable
internal fun RealEstateDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    realEstateId: Int,
) {
    RealEstateDetailScreen(
        modifier = modifier,
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
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RealEstateDetailScreen(
    modifier: Modifier = Modifier,
    itemProperties: MutableList<RealEstateProperty>
) {
    val scrollState = rememberScrollState()
    val transition = updateTransition(scrollState.value, label = "")
    val threadAnimation = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
            .toInt() * 3 / 4 - TOOLBAR_HEIGHT.dp.toPx()
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
        paddingHorizontal = 0
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
        ) {
            val (tvName, tvViews, tvAddress, tvCreatedDate,
                tvPrice, tvPostId, borderTopGrid, gridLayout,
                borderBottomGrid, tvDescriptionTitle,
                tvDescription) = createRefs()
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
                    fontSize = 12.sp,
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
                    fontSize = 12.sp,
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
                    fontSize = 12.sp,
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
                        top.linkTo(borderBottomGrid.top, MARGIN_DIFFERENT_VIEW.dp)
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
                    fontSize = 12.sp,
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

//            Box(
//                modifier = Modifier
//                    .height(1000.dp)
//                    .fillMaxWidth()
//                    .background(Color.Green)
//            )
        }

    }
    ToolbarWAnimation(
        modifier = Modifier
            .padding(horizontal = paddingToolbar.dp),
        toolbarHeight = toolbarHeight,
        title = "",
        bgColor = bgToolbar,
        leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
        onLeadingIconClick = {

        },
        trainingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.PostSavedOutline),
        onTrainingIconClick = {

        },
        bgIcon = bgIcon
    )
}
