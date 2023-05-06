package com.example.realestateapp.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    SettingScreen()
}

@Composable
internal fun SettingScreen() {
    Column(
        modifier = Modifier
            .background(RealStateAppTheme.colors.bgScreen)
            .padding(horizontal = PADDING_SCREEN.dp)
    ) {
        Spacing(TOOLBAR_HEIGHT)
        Spacing(MARGIN_VIEW)
        ConstraintLayout(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            val (
                imgUser,
                tvName,
                tvMail,
                btnEdit
            ) = createRefs()
            val verticalGuideLine = createGuidelineFromTop(0.5f)
            ImageProfile(
                size = 100,
                model = "https://media.tinthethao.com.vn/files/bongda/2019/03/20/sinh-than-torres-va-10-bi-mat-khong-phai-ai-cung-biet-170116jpg.jpg",
                modifier = Modifier
                    .constrainAs(imgUser) {
                        start.linkTo(parent.start)
                    }
                    .clickable {

                    }
            )
            Text(
                text = "Name",
                style = RealStateTypography.h1,
                modifier = Modifier
                    .constrainAs(tvName) {
                        start.linkTo(imgUser.end, margin = MARGIN_VIEW.dp)
                        linkTo(
                            top = imgUser.top,
                            bottom = verticalGuideLine,
                            bottomMargin = 2.dp,
                            bias = 1F
                        )
                    }
                    .clickable {

                    }
            )
            Text(
                text = "Mail",
                style = RealStateTypography.button.copy(
                    color = RealStateAppTheme.colors.textSettingButton,
                ),
                modifier = Modifier
                    .constrainAs(tvMail) {
                        start.linkTo(imgUser.end, margin = MARGIN_VIEW.dp)
                        linkTo(
                            top = verticalGuideLine,
                            bottom = imgUser.bottom,
                            bottomMargin = 2.dp,
                            bias = 0F
                        )
                    }
                    .clickable {

                    }
            )
            IconRealStateApp(
                icon = AppIcon.ImageVectorIcon(RealStateIcon.Edit),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
                    .padding(Constants.DefaultValue.PADDING_ICON.dp)
                    .constrainAs(btnEdit) {
                        top.linkTo(imgUser.top)
                        bottom.linkTo(imgUser.bottom)
                        end.linkTo(parent.end)
                    }
                    .clickable {

                    },
                tint = RealStateAppTheme.colors.primary
            )
        }

        Spacing(MARGIN_DIFFERENT_VIEW)
        SettingButton(
            onClick = {},
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(),
            title = stringResource(id = R.string.settingChangePassTitle),
            leadingIcon = AppIcon.ImageVectorIcon(RealStateIcon.Lock),
            backgroundIcon = RealStateAppTheme.colors.primary
        )
        Spacing(MARGIN_VIEW)
        SettingButton(
            onClick = {},
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(),
            title = stringResource(id = R.string.settingPostSavedTitle),
            leadingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.PostSaved),
            backgroundIcon = RealStateAppTheme.colors.primary
        )
        Spacing(MARGIN_VIEW)
        SettingButton(
            onClick = {},
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(),
            title = stringResource(id = R.string.settingPostSavedTitle),
            leadingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.PostSaved),
            backgroundIcon = RealStateAppTheme.colors.primary
        )
        Spacing(MARGIN_VIEW)
        SettingButton(
            onClick = {},
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(),
            title = stringResource(id = R.string.settingPostSavedTitle),
            leadingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.PostSaved),
            backgroundIcon = RealStateAppTheme.colors.primary
        )
    }
    ToolbarView(title = stringResource(id = R.string.settingTitle))
}
