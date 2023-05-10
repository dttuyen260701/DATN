package com.example.realestateapp.ui.setting.launcher

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.BaseScreen
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.TextTitle
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
    BaseScreen(modifier = modifier) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        TextTitle(stringResource(id = R.string.settingSignUpTitle))
    }
}
