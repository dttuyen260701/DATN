package com.example.realestateapp.ui.setting.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.setting.SettingRoute
import com.example.realestateapp.ui.setting.changepass.ChangePassRoute
import com.example.realestateapp.ui.setting.launcher.SignInRoute
import com.example.realestateapp.ui.setting.launcher.SignUpRoute
import com.example.realestateapp.ui.setting.profile.ProfileRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val settingNavigationRouteGraph = "setting_route_graph"
const val settingNavigationRoute = "setting_route"
const val signInNavigationRoute = "sign_in_route"
const val signUpNavigationRoute = "sign_up_route"
const val profileNavigationRoute = "profile_route"
const val changePassNavigationRoute = "change_pass_route"

internal fun NavController.navigateToSettingGraph(navOptions: NavOptions? = null) {
    this.navigate(settingNavigationRouteGraph, navOptions)
}

internal fun NavHostController.navigateToSignIn(beforeNavigated: () -> Unit = {}) {
    this.navigateSingleTopTo(
        route = signInNavigationRoute,
        beforeNavigated = beforeNavigated
    )
}

internal fun NavHostController.navigateToSignUp(beforeNavigated: () -> Unit = {}) {
    this.navigateSingleTopTo(
        route = signUpNavigationRoute,
        beforeNavigated = beforeNavigated
    )
}

internal fun NavHostController.navigateToProfile() {
    this.navigateSingleTopTo(
        route = profileNavigationRoute
    )
}

internal fun NavHostController.navigateToChangePass() {
    this.navigateSingleTopTo(
        route = changePassNavigationRoute
    )
}

internal fun NavGraphBuilder.settingGraph(
    onEditClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onPostSavedClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
    onSignInSuccess: () -> Unit,
    onSignOutSuccess: () -> Unit,
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
            onSignOutSuccess = onSignOutSuccess
        )
        singInScreen(
            onSignUpClick = onSignUpClick,
            onSignInSuccess = onSignInSuccess
        )
        singUpScreen(
            onSignInClick = onSignInClick,
            onSignUpSuccess = onSignUpSuccess
        )
        profileScreen()
        changePassScreen()
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
    onSignOutSuccess: () -> Unit
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
            onSignOutSuccess = onSignOutSuccess
        )
    }
}

internal fun NavGraphBuilder.singInScreen(
    onSignUpClick: () -> Unit,
    onSignInSuccess: () -> Unit
) {
    composable(signInNavigationRoute) {
        SignInRoute(
            onSignUpClick = onSignUpClick,
            onSignInSuccess = onSignInSuccess
        )
    }
}

internal fun NavGraphBuilder.singUpScreen(
    onSignInClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    composable(signUpNavigationRoute) {
        SignUpRoute(
            onSignInClick = onSignInClick,
            onSignUpSuccess = onSignUpSuccess
        )
    }
}

internal fun NavGraphBuilder.profileScreen() {
    composable(profileNavigationRoute) {
        ProfileRoute()
    }
}

internal fun NavGraphBuilder.changePassScreen() {
    composable(changePassNavigationRoute) {
        ChangePassRoute()
    }
}
