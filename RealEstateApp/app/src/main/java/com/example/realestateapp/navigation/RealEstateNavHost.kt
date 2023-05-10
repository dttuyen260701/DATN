package com.example.realestateapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.home.navigation.homeScreen
import com.example.realestateapp.ui.home.navigation.navigateToHome
import com.example.realestateapp.ui.notification.navigation.notificationScreen
import com.example.realestateapp.ui.post.navigation.postScreen
import com.example.realestateapp.ui.setting.navigation.navigateToSignIn
import com.example.realestateapp.ui.setting.navigation.navigateToSignUp
import com.example.realestateapp.ui.setting.navigation.settingGraph

/**
 * Created by tuyen.dang on 5/1/2023.
 */

@Composable
fun RealEstateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
        postScreen()
        notificationScreen()
        settingGraph(
            onEditClick = {

            },
            onSignInClick = {
                navController.navigateToSignIn()
            },
            onSignUpClick = {
                navController.navigateToSignUp()
            },
            onPolicyClick = {

            },
            onAboutUsClick = {

            },
            onChangePassClick = {

            },
            onPostSavedClick = {

            },
            onSignInSuccess = {
                navController.clearBackStack()
                navController.navigateToHome()
            },
            onSignOutSuccess = {
                navController.clearBackStack()
                navController.navigateToHome()
            }
        )
    }
}

fun NavHostController.clearBackStack() {
    for (i  in 1..this.backQueue.size){
        this.popBackStack()
    }
}

fun NavHostController.navigateSingleTopTo(
    route: String,
    beforeNavigated: () -> Unit = {}
) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    // Restore state when reselecting a previously selected item
    restoreState = true
    beforeNavigated.invoke()
}
