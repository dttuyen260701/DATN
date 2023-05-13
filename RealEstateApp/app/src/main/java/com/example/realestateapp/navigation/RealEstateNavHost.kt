package com.example.realestateapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.home.navigation.homeScreen
import com.example.realestateapp.ui.home.navigation.navigateToHome
import com.example.realestateapp.ui.notification.navigation.notificationScreen
import com.example.realestateapp.ui.post.navigation.postScreen
import com.example.realestateapp.ui.setting.navigation.*

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
                navController.navigateToProfile()
            },
            onSignInClick = {
                navController.navigateToSignIn {
                    navController.popBackStack(signInNavigationRoute, true)
                }
            },
            onSignUpClick = {
                navController.navigateToSignUp {
                    navController.popBackStack(signUpNavigationRoute, true)
                }
            },
            onPolicyClick = {

            },
            onAboutUsClick = {

            },
            onChangePassClick = {
                navController.navigateToChangePass()
            },
            onSignUpSuccess = {
                navController.navigateToSignIn {
                    navController.popBackStack()
                }
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
    for (i in 1..this.backQueue.size) {
        this.popBackStack()
    }
}

fun NavHostController.navigateSingleTopTo(
    route: String,
    beforeNavigated: () -> Unit = {}
) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
    beforeNavigated.invoke()
}
