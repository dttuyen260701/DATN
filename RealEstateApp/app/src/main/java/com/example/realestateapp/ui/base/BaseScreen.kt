package com.example.realestateapp.ui.base

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun BaseScreen(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    bgColor: Color = RealEstateAppTheme.colors.bgScreen,
    bgToolbarColor: Color = RealEstateAppTheme.colors.bgScrPrimaryLight,
    paddingHorizontal: Int = PADDING_HORIZONTAL_SCREEN,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    toolbar: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(bgToolbarColor)
    ) {
        toolbar()
        Column(
            modifier = Modifier
                .background(bgColor)
                .padding(horizontal = paddingHorizontal.dp)
                .weight(1f)
                .fillMaxWidth()
                .then(modifier)
                .verticalScroll(scrollState),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
        footer()
    }
}
