package com.example.realestateapp.ui.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.realestateapp.ui.setting.SettingScreen

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val settingNavigationRoute = "setting_route"

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(settingNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingScreen() {
    composable(settingNavigationRoute) {
        SettingScreen()
    }
}

