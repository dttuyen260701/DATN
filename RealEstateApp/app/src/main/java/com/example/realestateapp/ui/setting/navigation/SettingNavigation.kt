package com.example.realestateapp.ui.setting.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.setting.SettingRoute
import com.example.realestateapp.ui.setting.launcher.SignInRoute
import com.example.realestateapp.ui.setting.launcher.SignUpRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val settingNavigationRouteGraph = "setting_route_graph"
const val settingNavigationRoute = "setting_route"
const val signInNavigationRoute = "sign_in_route"
const val signUpNavigationRoute = "sign_up_route"

internal fun NavController.navigateToSettingGraph(navOptions: NavOptions? = null) {
    this.navigate(settingNavigationRouteGraph, navOptions)
}

internal fun NavHostController.navigateToSignIn() {
    this.navigateSingleTopTo(signInNavigationRoute)
}

internal fun NavHostController.navigateToSignUp() {
    this.navigateSingleTopTo(signUpNavigationRoute)
}

internal fun NavGraphBuilder.settingGraph(
    onEditClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onPostSavedClick: () -> Unit,
    onLogoutSuccessListener: () -> Unit,
) {
    navigation(
        route = settingNavigationRouteGraph,
        startDestination = settingNavigationRoute
    ) {
        settingScreen(
            onEditClick = onEditClick,
            onSignInClick = onSignInClick,
            onSignUpClick = onSignUpClick,
            onPolicyClick = onPolicyClick,
            onAboutUsClick = onAboutUsClick,
            onChangePassClick = onChangePassClick,
            onPostSavedClick = onPostSavedClick,
            onLogoutSuccessListener = onLogoutSuccessListener
        )
        singInScreen(
            onSignUpClick = onSignUpClick
        )
        singUpScreen()
    }
}

internal fun NavGraphBuilder.settingScreen(
    onEditClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onPostSavedClick: () -> Unit,
    onLogoutSuccessListener: () -> Unit
) {
    composable(settingNavigationRoute) {
        SettingRoute(
            onEditClick = onEditClick,
            onSignInClick = onSignInClick,
            onSignUpClick = onSignUpClick,
            onPolicyClick = onPolicyClick,
            onAboutUsClick = onAboutUsClick,
            onChangePassClick = onChangePassClick,
            onPostSavedClick = onPostSavedClick,
            onLogoutSuccessListener = onLogoutSuccessListener
        )
    }
}

internal fun NavGraphBuilder.singInScreen(
    onSignUpClick: () -> Unit
) {
    composable(signInNavigationRoute) {
        SignInRoute(
            onSignUpClick = onSignUpClick
        )
    }
}

internal fun NavGraphBuilder.singUpScreen() {
    composable(signUpNavigationRoute) {
        SignUpRoute()
    }
}

