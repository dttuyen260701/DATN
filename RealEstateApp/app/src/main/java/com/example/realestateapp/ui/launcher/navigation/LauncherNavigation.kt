package com.example.realestateapp.ui.launcher.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.realestateapp.ui.launcher.SignInScreen
import com.example.realestateapp.ui.launcher.SignUpScreen

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val signInNavigationRoute = "sign_in_route"
const val signUpNavigationRoute = "sign_up_route"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInNavigationRoute, navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpNavigationRoute, navOptions)
}

fun NavGraphBuilder.singInScreen() {
    composable(signInNavigationRoute) {
        SignInScreen()
    }
}

fun NavGraphBuilder.singUpScreen() {
    composable(signUpNavigationRoute) {
        SignUpScreen()
    }
}
