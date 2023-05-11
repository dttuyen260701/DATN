package com.example.realestateapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.User
import com.example.realestateapp.designsystem.components.ImageProfile
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val user = remember {
        viewModel.getUser()
    }
    HomeScreen(
        modifier = modifier,
        user = user.value
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    user: User?
) {
    BaseScreen(modifier = modifier) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        user?.run {
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Transparent)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                val (imgUser, tvWelcome, tvName) = createRefs()
                val verticalGuideLine = createGuidelineFromTop(0.5f)
                Text(
                    text = stringResource(id = R.string.welcomeTitle),
                    style = RealStateTypography.h1.copy(
                        fontSize = 23.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .constrainAs(tvWelcome) {
                            start.linkTo(parent.start)
                            linkTo(parent.top, verticalGuideLine)
                        }
                )
                Text(
                    text = fullName,
                    style = RealStateTypography.h1.copy(
                        fontSize = 25.sp,
                        color = RealStateAppTheme.colors.primary,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .constrainAs(tvName) {
                            start.linkTo(parent.start)
                            linkTo(verticalGuideLine, parent.bottom)
                        }
                )
                ImageProfile(
                    size = 50,
                    model = user.imgUrl ?: "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .constrainAs(imgUser) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
