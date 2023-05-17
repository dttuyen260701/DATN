package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.designsystem.components.SlideShowImage
import com.example.realestateapp.designsystem.components.ToolbarWAnimation
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseScreen
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
        modifier = modifier
    )
}

@Composable
internal fun RealEstateDetailScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val transition = updateTransition(scrollState.value, label = "")
    val threadAnimation = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()*3/4 - TOOLBAR_HEIGHT.dp.toPx()
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
                "https://cdnimg.vietnamplus.vn/uploaded/mzdic/2022_05_27/realmadrid2705.jpg"
            )
        )
        Box(
            modifier = Modifier
                .height(1000.dp)
                .fillMaxWidth()
                .background(Color.Green)
        )

    }
    ToolbarWAnimation(
        modifier = Modifier
            .padding(horizontal = paddingToolbar.dp),
        toolbarHeight = toolbarHeight,
        title = "",
        bgColor = bgToolbar,
        rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.PostSavedOutline),
        onRightIconClick = {

        },
        leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
        onLeftIconClick = {

        },
        bgIcon = bgIcon
    )
}
