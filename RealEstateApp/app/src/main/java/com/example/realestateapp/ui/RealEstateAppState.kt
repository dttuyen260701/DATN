package com.example.realestateapp.ui

import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.realestateapp.data.navigation.TopLevelDestination

/**
 * Created by tuyen.dang on 5/1/2023.
 */

@Composable
fun rememberRealEstateAppState(
    navController: NavHostController = rememberNavController(),
): RealEstateAppState = remember(navController) {
    RealEstateAppState(navController)
}

@Stable
class RealEstateAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {

            else -> null
        }

    var isLoading by mutableStateOf(false)
        private set

    var isOnline by mutableStateOf(false)
        private set

    var shouldErrorDialog by mutableStateOf(false)
        private set

    fun setIsLoading(loading: Boolean) {
        isLoading = loading
    }

    fun setIsOnline(online: Boolean) {
        isOnline = online
    }

    fun setShowErrorDialog(shouldShow: Boolean) {
        shouldErrorDialog = shouldShow
    }
}
