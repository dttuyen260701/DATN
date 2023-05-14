package com.example.realestateapp.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
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
    bgColor: Color = RealEstateAppTheme.colors.bgScreen,
    bgToolbarColor: Color = RealEstateAppTheme.colors.bgScrPrimaryLight,
    paddingHorizontal: Int = PADDING_HORIZONTAL_SCREEN,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    toolbar: @Composable ColumnScope.() -> Unit = {},
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
                .then(modifier)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
    }
}
