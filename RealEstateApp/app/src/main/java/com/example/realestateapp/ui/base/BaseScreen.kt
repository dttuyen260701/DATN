package com.example.realestateapp.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.util.Constants

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun BaseScreen(
    modifier: Modifier = Modifier,
    bgColor: Color = RealStateAppTheme.colors.bgScreen,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(bgColor)
            .padding(horizontal = Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN.dp)
            .then(modifier)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}
