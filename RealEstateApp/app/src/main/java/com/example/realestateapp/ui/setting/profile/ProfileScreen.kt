package com.example.realestateapp.ui.setting.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/11/2023.
 */

@Composable
internal fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        modifier = modifier
    )
}

@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(
        modifier = modifier
    ) {

    }
}
