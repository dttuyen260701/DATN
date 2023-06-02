package com.example.realestateapp.ui.base

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ButtonRadius
import com.example.realestateapp.designsystem.components.ButtonRadiusGradient
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.TextTitle
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

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
    contentNonScroll: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(bgToolbarColor)
    ) {
        toolbar()
        if (contentNonScroll != null) {
            contentNonScroll()
        } else {
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
        }
        footer()
    }
}

@Composable
internal fun RequireLoginScreen(
    message: String,
    navigateSignIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                RealEstateAppTheme.colors.bgScrPrimaryLight
            )
            .padding(PADDING_HORIZONTAL_SCREEN.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(message)
        Spacing(MARGIN_VIEW)
        Image(
            painter = painterResource(id = R.drawable.ic_require_sign_in),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadiusGradient(
            onClick = navigateSignIn,
            title = stringResource(id = R.string.settingSignInTitle),
            bgColor = RealEstateAppTheme.colors.bgButtonGradient,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
    }
}
