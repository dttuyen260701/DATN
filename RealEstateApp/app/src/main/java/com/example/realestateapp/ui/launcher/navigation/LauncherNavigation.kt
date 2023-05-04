package com.example.realestateapp.ui.launcher.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.realestateapp.ui.launcher.SignInRoute
import com.example.realestateapp.ui.launcher.SignUpRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val launcherNavigationRoute = "launcher_route"
const val signInNavigationRoute = "sign_in_route"
const val signUpNavigationRoute = "sign_up_route"

internal fun NavController.navigateToLauncher(navOptions: NavOptions? = null) {
    this.navigate(launcherNavigationRoute, navOptions)
}

internal fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInNavigationRoute, navOptions)
}

internal fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpNavigationRoute, navOptions)
}

internal fun NavGraphBuilder.launcherGraph() {
    navigation(
        route = launcherNavigationRoute,
        startDestination = signInNavigationRoute
    ) {
        singInScreen()
        singUpScreen()
    }
}

internal fun NavGraphBuilder.singInScreen() {
    composable(signInNavigationRoute) {
        SignInRoute()
    }
}

internal fun NavGraphBuilder.singUpScreen() {
    composable(signUpNavigationRoute) {
        SignUpRoute()
    }
}
