package com.example.realestateapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.realestateapp.R
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.notification.navigation.notificationNavigationRoute
import com.example.realestateapp.ui.post.navigation.postNavigationRoute
import com.example.realestateapp.ui.setting.navigation.settingNavigationRoute

/**
 * Created by tuyen.dang on 5/1/2023.
 */

enum class TopLevelDestination(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(
        title = R.string.homeTitle,
        icon = R.drawable.ic_home,
        route = homeNavigationRoute
    ),
    POST(
        title = R.string.postTitle,
        icon = R.drawable.ic_post,
        route = postNavigationRoute
    ),
    NOTIFICATION(
        title = R.string.notificationTitle,
        icon = R.drawable.ic_notification,
        route = notificationNavigationRoute
    ),
    SETTING(
        title = R.string.settingTitle,
        icon = R.drawable.ic_setting,
        route = settingNavigationRoute
    ),
}