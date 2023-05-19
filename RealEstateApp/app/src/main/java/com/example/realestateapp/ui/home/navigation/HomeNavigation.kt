package com.example.realestateapp.ui.home.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.ui.home.HomeRoute
import com.example.realestateapp.ui.home.realestatedetail.RealEstateDetailRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val homeNavigationGraphRoute = "home_route_graph"
const val homeNavigationRoute = "home_route"
const val realEstateDetailNavigationRoute = "post_detail"
const val realEstateIdKey = "realEstateId"

internal fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

internal fun NavHostController.navigateToRealEstateDetail(
    realEstateId: Int
) {
    this.navigate(
        route = "$realEstateDetailNavigationRoute/$realEstateId"
    )
}

internal fun NavGraphBuilder.homeGraph(
    onSearchClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = homeNavigationGraphRoute,
        startDestination = homeNavigationRoute
    ) {
        homeScreen(
            onSearchClick = onSearchClick,
            onRealEstateItemClick = onRealEstateItemClick
        )
        realEstateDetailScreen(
            onRealEstateItemClick = onRealEstateItemClick,
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.homeScreen(
    onSearchClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeRoute(
            onSearchClick = onSearchClick,
            onRealEstateItemClick = onRealEstateItemClick
        )
    }
}

internal fun NavGraphBuilder.realEstateDetailScreen(
    onRealEstateItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = "$realEstateDetailNavigationRoute/{$realEstateIdKey}",
        arguments = listOf(navArgument(realEstateIdKey) { type = NavType.IntType })
    ) { backStackEntry ->
        RealEstateDetailRoute(
            realEstateId = backStackEntry.arguments?.getInt(realEstateIdKey) ?: 0,
            onRealEstateItemClick = onRealEstateItemClick,
            onBackClick = onBackClick
        )
    }
}
