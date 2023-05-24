package com.example.realestateapp.ui.home.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.data.enums.SearchOption
import com.example.realestateapp.navigation.getBackEntryData
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.home.HomeRoute
import com.example.realestateapp.ui.home.realestatedetail.RealEstateDetailRoute
import com.example.realestateapp.ui.home.search.SearchRoute
import com.example.realestateapp.ui.pickaddress.navigation.searchAddressKey

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val homeNavigationGraphRoute = "home_route_graph"
const val homeNavigationRoute = "home_route"
const val realEstateDetailNavigationRoute = "post_detail"
const val realEstateIdKey = "realEstateId"
const val searchNavigationRoute = "search"
const val searchOptionKey = "option"

internal fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationGraphRoute, navOptions)
}

internal fun NavHostController.navigateToRealEstateDetail(
    realEstateId: Int
) {
    this.navigate(
        route = "$realEstateDetailNavigationRoute/$realEstateId"
    )
}

internal fun NavHostController.navigateToSearch(
    searchOption: SearchOption,
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = "$searchNavigationRoute/${searchOption.option}",
        beforeNavigated = beforeNavigated
    )
}

internal fun NavGraphBuilder.homeGraph(
    navigateToSearch: (SearchOption) -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onClickProfile: () -> Unit,
    navigateToPickAddress: () -> Unit
) {
    navigation(
        route = homeNavigationGraphRoute,
        startDestination = homeNavigationRoute
    ) {
        homeScreen(
            navigateToSearch = navigateToSearch,
            onRealEstateItemClick = onRealEstateItemClick,
            navigateProfile = onClickProfile
        )
        realEstateDetailScreen(
            onRealEstateItemClick = onRealEstateItemClick,
            onBackClick = onBackClick
        )
        searchScreen(
            onBackClick = onBackClick,
            onRealEstateItemClick = onRealEstateItemClick,
            navigateToPickAddress = navigateToPickAddress
        )
    }
}

internal fun NavGraphBuilder.homeScreen(
    navigateToSearch: (SearchOption) -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    navigateProfile: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeRoute(
            navigateToSearch = navigateToSearch,
            onRealEstateItemClick = onRealEstateItemClick,
            navigateProfile = navigateProfile
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

internal fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    navigateToPickAddress: () -> Unit
) {
    composable(
        route = "$searchNavigationRoute/{$searchOptionKey}",
        arguments = listOf(navArgument(searchOptionKey) { type = NavType.IntType })
    ) { backStackEntry ->
        SearchRoute(
            searchOption = backStackEntry.arguments?.getInt(searchOptionKey) ?: 0,
            onBackClick = onBackClick,
            onRealEstateItemClick = onRealEstateItemClick,
            navigateToPickAddress = navigateToPickAddress,
            addressDetails = mutableListOf(
                backStackEntry.getBackEntryData<String>(key = searchAddressKey) ?: ""
            )
        )
    }
}
