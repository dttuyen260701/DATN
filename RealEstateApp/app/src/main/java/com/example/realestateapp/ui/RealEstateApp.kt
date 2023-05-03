package com.example.realestateapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.realestateapp.navigation.TopLevelDestination

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
fun RealEstateApp(
    appState: RealEstateAppState = rememberRealEstateAppState()
) {

}

@Composable
fun RealEstateBottomBar(
    currentDestination: NavDestination?,
    navController: NavController,
    tabs: Array<TopLevelDestination>
) {

}