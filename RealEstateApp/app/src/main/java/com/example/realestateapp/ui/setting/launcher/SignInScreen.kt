package com.example.realestateapp.ui.setting.launcher

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.designsystem.components.BaseScreen

/**
 * Created by tuyen.dang on 5/8/2023.
 */

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    SignInScreen(
        modifier = modifier
    )
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(modifier = modifier) {
        
    }
}
