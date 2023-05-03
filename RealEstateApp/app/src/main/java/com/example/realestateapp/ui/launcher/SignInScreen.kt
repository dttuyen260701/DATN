package com.example.realestateapp.ui.launcher

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by tuyen.dang on 5/1/2023.
 */

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    SignInScreen()
}

@Composable
internal fun SignInScreen() {

}
