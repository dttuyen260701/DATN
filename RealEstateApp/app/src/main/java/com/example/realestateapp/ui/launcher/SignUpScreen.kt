package com.example.realestateapp.ui.launcher

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SignUpRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    SignUpScreen()
}
 
@Composable
internal fun SignUpScreen() {

}
