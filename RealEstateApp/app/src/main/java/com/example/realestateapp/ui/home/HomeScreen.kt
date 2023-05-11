package com.example.realestateapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        modifier = modifier,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.Transparent)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val (imgUser, tvWelcome, tvName) = createRefs()
            val verticalGuideLine = createGuidelineFromTop(0.5f)
        }
    }
}
