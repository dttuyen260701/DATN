package com.example.realestateapp.ui.pickaddress.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.pickaddress.PickAddressMapRoute
import com.example.realestateapp.ui.pickaddress.PickAddressRoute

/**
 * Created by tuyen.dang on 5/22/2023.
 */

const val pickAddressNavigationRoute = "pick_address_route"
const val pickAddressMapNavigationRoute = "pick_address_map_route"
const val searchAddressKey = "search_address"

internal fun NavHostController.navigateToPickAddress(
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = pickAddressNavigationRoute,
        beforeNavigated = beforeNavigated
    )
}

internal fun NavHostController.navigateToPickAddressMap(
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = pickAddressMapNavigationRoute,
        beforeNavigated = beforeNavigated
    )
}

internal fun NavGraphBuilder.pickAddressScreen(
    onPickAddressSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = pickAddressNavigationRoute,
    ) {
        PickAddressRoute(
            onPickAddressSuccess = onPickAddressSuccess,
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.pickAddressMapScreen(
    onPickAddressSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = pickAddressMapNavigationRoute,
    ) {
        PickAddressMapRoute(
            onPickAddressSuccess = onPickAddressSuccess,
            onBackClick = onBackClick
        )
    }
}
