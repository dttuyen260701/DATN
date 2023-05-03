package com.example.realestateapp.ui.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.realestateapp.ui.notification.NotificationScreen

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val notificationNavigationRoute = "notification_route"

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    this.navigate(notificationNavigationRoute, navOptions)
}

fun NavGraphBuilder.notificationScreen() {
    composable(route = notificationNavigationRoute) {
        NotificationScreen()
    }
}
