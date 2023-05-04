package com.example.realestateapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.home.navigation.homeScreen
import com.example.realestateapp.ui.launcher.navigation.launcherGraph
import com.example.realestateapp.ui.notification.navigation.notificationScreen
import com.example.realestateapp.ui.post.navigation.postScreen
import com.example.realestateapp.ui.setting.navigation.settingScreen

/**
 * Created by tuyen.dang on 5/1/2023.
 */

@Composable
fun RealEstateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        launcherGraph()
        homeScreen()
        postScreen()
        notificationScreen()
        settingScreen()
    }
}
