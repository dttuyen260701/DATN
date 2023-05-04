package com.example.realestateapp.ui.post.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.realestateapp.ui.post.PostRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val postNavigationRoute = "post_route"

internal fun NavController.navigateToPost(navOptions: NavOptions? = null) {
    this.navigate(postNavigationRoute, navOptions)
}

internal fun NavGraphBuilder.postScreen() {
    composable(postNavigationRoute) {
        PostRoute()
    }
}
