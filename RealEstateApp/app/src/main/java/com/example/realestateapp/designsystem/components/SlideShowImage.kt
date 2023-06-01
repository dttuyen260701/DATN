package com.example.realestateapp.designsystem.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW

/**
 * Created by tuyen.dang on 5/17/2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SlideShowImage(
    modifier: Modifier = Modifier,
    photos: MutableList<Image>,
    isShowIndicator: Boolean = true,
    currentPosition: Int = 0,
    onPositionChange: (Int) -> Unit = {},
    onItemClick: (Int) -> Unit = { _ -> }
) {
    val pageState = rememberPagerState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
            .then(modifier),
    ) {
        val (viewPager, indicator) = createRefs()
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(viewPager) {
                    top.linkTo(parent.top)
                },
            pageCount = photos.size,
            state = pageState
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onItemClick(page)
                    },
                model = photos[page].url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_user)
            )
        }
        if (isShowIndicator) {
            DotsIndicator(
                modifier = Modifier
                    .constrainAs(indicator) {
                        bottom.linkTo(viewPager.bottom, MARGIN_VIEW.dp)
                        linkTo(start = viewPager.start, end = viewPager.end)
                    },
                totalDots = photos.size,
                selectedIndex = pageState.currentPage
            )
        }
        LaunchedEffect(key1 = true) {
            pageState.scrollToPage(currentPosition)
        }
        LaunchedEffect(key1 = pageState.currentPage) {
            onPositionChange(pageState.currentPage)
        }
    }
}

@Composable
internal fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        horizontalArrangement = Arrangement.Center
    ) {
        items(totalDots) { index ->
            val transition = updateTransition(index == selectedIndex, label = "")
            val bgIndicator by transition.animateColor(
                transitionSpec = {
                    spring(
                        stiffness = Constants.DefaultValue.TWEEN_ANIMATION_TIME.toFloat()
                    )
                },
                label = ""
            ) {
                if (it) RealEstateAppTheme.colors.primary
                else RealEstateAppTheme.colors.primaryVariant
            }
            val indicatorWidth by transition.animateInt(
                transitionSpec = {
                    spring(
                        stiffness = 100f,
                        dampingRatio = 1f
                    )
                },
                label = ""
            ) { if (it) 10 else 5 }

            Spacer(
                modifier = Modifier
                    .height(5.dp)
                    .width(indicatorWidth.dp)
                    .clip(CircleShape)
                    .background(color = bgIndicator)
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
 