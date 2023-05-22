package com.example.realestateapp.ui.pickaddress.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.pickaddress.PickAddressRoute

/**
 * Created by tuyen.dang on 5/22/2023.
 */

const val pickAddressNavigationRoute = "pick_address_route"
const val searchAddressKey = "search_address"

internal fun NavHostController.navigateToPickAddress(
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = pickAddressNavigationRoute,
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
