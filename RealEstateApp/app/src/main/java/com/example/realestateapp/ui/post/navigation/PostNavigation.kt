package com.example.realestateapp.ui.post.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.realestateapp.navigation.getBackEntryData
import com.example.realestateapp.navigation.navigateSingleTopTo
import com.example.realestateapp.ui.pickaddress.navigation.searchAddressKey
import com.example.realestateapp.ui.post.PostRoute
import com.example.realestateapp.ui.post.addpost.AddPostRoute
import com.example.realestateapp.ui.post.records.RecordsRoute

/**
 * Created by tuyen.dang on 5/3/2023.
 */

const val postNavigationGraphRoute = "post_route_graph"
const val postNavigationRoute = "post_route"
const val recordsRoute = "record_route"
const val isMyRecordsKey = "is_my_record"
const val addPostRoute = "add_post"
const val idPostKey = "id_post"

internal fun NavController.navigateToPost(navOptions: NavOptions? = null) {
    this.navigate(postNavigationGraphRoute, navOptions)
}

internal fun NavHostController.navigateToRecords(
    isMyRecord: Boolean,
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = "$recordsRoute/$isMyRecord",
        beforeNavigated = beforeNavigated
    )
}

internal fun NavHostController.navigateToAddPost(
    postId: Int,
    beforeNavigated: () -> Unit = {}
) {
    this.navigateSingleTopTo(
        route = "$addPostRoute/$postId",
        beforeNavigated = beforeNavigated
    )
}

internal fun NavGraphBuilder.postGraph(
    navigateToRecord: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit,
    navigateToPickAddress: () -> Unit,
    navigateToAddPost: (Int) -> Unit,
    navigateToMyRecord: (Boolean) -> Unit,
    navigateSignIn: () -> Unit
) {
    navigation(
        route = postNavigationGraphRoute,
        startDestination = postNavigationRoute
    ) {
        postScreen(
            navigateToRecord = navigateToRecord,
            navigateToAddPost = navigateToAddPost,
            navigateSignIn = navigateSignIn
        )
        recordsScreen(
            onBackClick = onBackClick,
            onRealEstateItemClick = onRealEstateItemClick
        )
        addPostScreen(
            onBackClick = onBackClick,
            navigateToPickAddress = navigateToPickAddress,
            navigateToMyRecord = navigateToMyRecord
        )
    }
}

internal fun NavGraphBuilder.postScreen(
    navigateToRecord: (Boolean) -> Unit,
    navigateToAddPost: (Int) -> Unit,
    navigateSignIn: () -> Unit
) {
    composable(postNavigationRoute) {
        PostRoute(
            navigateToRecord = navigateToRecord,
            navigateToAddPost = navigateToAddPost,
            navigateSignIn = navigateSignIn
        )
    }
}

internal fun NavGraphBuilder.recordsScreen(
    onBackClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit
) {
    composable(
        route = "$recordsRoute/{$isMyRecordsKey}",
        arguments = listOf(navArgument(isMyRecordsKey) { type = NavType.BoolType })
    ) { backStackEntry ->
        RecordsRoute(
            isMyRecords = backStackEntry.arguments?.getBoolean(isMyRecordsKey, false) ?: false,
            onBackClick = onBackClick,
            onRealEstateItemClick = onRealEstateItemClick
        )
    }
}

internal fun NavGraphBuilder.addPostScreen(
    onBackClick: () -> Unit,
    navigateToPickAddress: () -> Unit,
    navigateToMyRecord: (Boolean) -> Unit
) {
    composable(
        route = "$addPostRoute/{$idPostKey}",
        arguments = listOf(navArgument(idPostKey) { type = NavType.IntType })
    ) { backStackEntry ->
        AddPostRoute(
            idPost = backStackEntry.arguments?.getInt(idPostKey, -1) ?: -1,
            onBackClick = onBackClick,
            navigateToPickAddress = navigateToPickAddress,
            navigateToMyRecord = navigateToMyRecord,
            addressDetails = mutableListOf(
                backStackEntry.getBackEntryData<String>(key = searchAddressKey) ?: ""
            )
        )
    }
}
