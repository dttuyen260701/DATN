package com.example.realestateapp.ui.setting.launcher

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.EditTextTrailingIconCustom
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.TextTitle
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SignUpRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    SignUpScreen(
        modifier = modifier
    )
}

@Composable
internal fun SignUpScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(
        modifier = modifier,
        bgColor = RealStateAppTheme.colors.bgScrPrimaryLight
    ) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        TextTitle(stringResource(id = R.string.settingSignUpTitle))
        Spacer(modifier = Modifier.weight(1f))
        EditTextTrailingIconCustom(
            onTextChange = {},
            text = "password",
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            errorText = "passwordError",
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.User),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
