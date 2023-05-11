package com.example.realestateapp.ui.setting.changepass

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/11/2023.
 */

@Composable
internal fun ChangePassRoute(
    modifier: Modifier = Modifier,
    viewModel: ChangePassViewModel = hiltViewModel()
) {
    ChangePassScreen(
        modifier = modifier
    )
}

@Composable
internal fun ChangePassScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(
        modifier = modifier
    ) {

    }
}
