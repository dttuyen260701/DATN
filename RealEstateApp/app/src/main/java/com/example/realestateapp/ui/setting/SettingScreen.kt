package com.example.realestateapp.ui.setting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

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
    Text(text = "Setting")
}
