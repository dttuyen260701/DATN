package com.example.realestateapp.ui.notification.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.notification.NotificationRoute
import com.example.realestateapp.ui.notification.messager.MessengerRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val notificationNavigationGraphRoute = "notification_route_graph"
const val notificationNavigationRoute = "notification_route"
const val messengerNavigationRoute = "messenger_route"
const val idGuestKey = "id_guest"

internal fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    this.navigate(notificationNavigationGraphRoute, navOptions)
}

internal fun NavHostController.navigateToMessenger(
    idGuest: String,
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = "$messengerNavigationRoute/$idGuest",
        beforeNavigated = beforeNavigated
    )
}

internal fun NavGraphBuilder.notificationGraph(
    navigateChatScreen: (String) -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = notificationNavigationGraphRoute,
        startDestination = notificationNavigationRoute
    ) {
        notificationScreen(
            navigateChatScreen = navigateChatScreen
        )
        messengerScreen(
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.notificationScreen(
    navigateChatScreen: (String) -> Unit
) {
    composable(route = notificationNavigationRoute) {
        NotificationRoute(
            navigateChatScreen = navigateChatScreen
        )
    }
}

internal fun NavGraphBuilder.messengerScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$messengerNavigationRoute/{$idGuestKey}",
        arguments = listOf(navArgument(idGuestKey) { type = NavType.IntType })
    ) { backStackEntry ->
        MessengerRoute(
            onBackClick = onBackClick,
            idGuest = backStackEntry.arguments?.getInt(idGuestKey, -1) ?: -1,
        )
    }
}
