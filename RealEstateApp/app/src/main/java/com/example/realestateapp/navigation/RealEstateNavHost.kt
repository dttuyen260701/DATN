package com.example.realestateapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.realestateapp.ui.home.navigation.*
import com.example.realestateapp.ui.notification.navigation.navigateToMessenger
import com.example.realestateapp.ui.notification.navigation.notificationGraph
import com.example.realestateapp.ui.pickaddress.navigation.navigateToPickAddress
import com.example.realestateapp.ui.pickaddress.navigation.pickAddressScreen
import com.example.realestateapp.ui.pickaddress.navigation.searchAddressKey
import com.example.realestateapp.ui.post.navigation.navigateToAddPost
import com.example.realestateapp.ui.post.navigation.navigateToRecords
import com.example.realestateapp.ui.post.navigation.postGraph
import com.example.realestateapp.ui.setting.navigation.*

/**
 * Created by tuyen.dang on 5/1/2023.
 */

@Composable
fun RealEstateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationGraphRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navigateToSearch = {
                navController.navigateToSearch(
                    searchOption = it
                )
            },
            onRealEstateItemClick = {
                navController.navigateToRealEstateDetail(
                    realEstateId = it
                )
            },
            navigateToEditPost = {
                navController.navigateToAddPost(
                    postId = it
                )
            },
            navigateMessengerScreen = {
                navController.navigateToMessenger(
                    idGuest = it
                )
            },
            onBackClick = {
                navController.popBackStack()
            },
            onClickProfile = {
                navController.navigateToProfile()
            },
            navigateToPickAddress = {
                navController.navigateToPickAddress()
            }
        )
        postGraph(
            navigateToRecord = {
                navController.navigateToRecords(
                    isMyRecord = it
                )
            },
            onBackClick = {
                navController.popBackStack()
            },
            onRealEstateItemClick = {
                navController.navigateToRealEstateDetail(
                    realEstateId = it
                )
            },
            navigateToPickAddress = {
                navController.navigateToPickAddress()
            },
            navigateToAddPost = {
                navController.navigateToAddPost(
                    postId = it
                )
            },
            navigateToMyRecord = {
                navController.navigateToRecords(
                    isMyRecord = it
                ) {
                    navController.popBackStack()
                }
            },
            navigateSignIn = {
                navController.navigateToSignIn {

                }
            }
        )
        notificationGraph(
            navigateChatScreen = {
                navController.navigateToMessenger(
                    idGuest = it
                )
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
        settingGraph(
            onEditClick = {
                navController.navigateToProfile()
            },
            onSignInClick = {
                navController.navigateToSignIn {
                    navController.popBackStack(signInNavigationRoute, true)
                }
            },
            onSignUpClick = {
                navController.navigateToSignUp {
                    navController.popBackStack(signUpNavigationRoute, true)
                }
            },
            onPolicyClick = {

            },
            onAboutUsClick = {

            },
            onChangePassClick = {
                navController.navigateToChangePass()
            },
            onSignUpSuccess = {
                navController.navigateToSignIn {
                    navController.popBackStack()
                }
            },
            onSignInSuccess = {
                navController.clearBackStack()
                navController.navigateToHome()
            },
            onSignOutSuccess = {
                navController.clearBackStack()
                navController.navigateToHome()
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
        pickAddressScreen(
            onPickAddressSuccess = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(searchAddressKey, it)
                navController.popBackStack()
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}

fun NavHostController.clearBackStack() {
    for (i in 1..this.backQueue.size) {
        this.popBackStack()
    }
}

fun NavHostController.navigateSingleTopTo(
    route: String,
    beforeNavigated: () -> Unit = {}
) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
    beforeNavigated.invoke()
}

@Composable
fun <T> NavBackStackEntry.getBackEntryData(key: String): T? {
    val data: State<T?> = savedStateHandle
        .getLiveData<T>(key)
        .observeAsState()
    savedStateHandle.remove<T>(key)
    return data.value
}
