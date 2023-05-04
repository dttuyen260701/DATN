package com.example.realestateapp.ui

import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.realestateapp.navigation.TopLevelDestination
import com.example.realestateapp.navigation.TopLevelDestination.*
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.home.navigation.navigateToHome
import com.example.realestateapp.ui.notification.navigation.navigateToNotification
import com.example.realestateapp.ui.notification.navigation.notificationNavigationRoute
import com.example.realestateapp.ui.post.navigation.navigateToPost
import com.example.realestateapp.ui.post.navigation.postNavigationRoute
import com.example.realestateapp.ui.setting.navigation.navigateToSetting
import com.example.realestateapp.ui.setting.navigation.settingNavigationRoute

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
            homeNavigationRoute -> HOME
            postNavigationRoute -> POST
            notificationNavigationRoute -> NOTIFICATION
            settingNavigationRoute -> SETTING
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    var isLoading by mutableStateOf(false)
        private set

    var shouldErrorDialog by mutableStateOf(false)
        private set

    val shouldShowBottomBar: Boolean
        @Composable get() = (currentDestination?.route in topLevelDestinations.map { it.route })

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        navController.popBackStack()

        when (topLevelDestination) {
            HOME -> navController.navigateToHome(topLevelNavOptions)
            POST -> navController.navigateToPost(topLevelNavOptions)
            NOTIFICATION -> navController.navigateToNotification(topLevelNavOptions)
            SETTING -> navController.navigateToSetting(topLevelNavOptions)
        }
    }

    fun setIsLoading(loading: Boolean) {
        isLoading = loading
    }

    fun setShowErrorDialog(shouldShow: Boolean) {
        shouldErrorDialog = shouldShow
    }
}
