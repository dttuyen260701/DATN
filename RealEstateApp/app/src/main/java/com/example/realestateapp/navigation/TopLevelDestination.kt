package com.example.realestateapp.navigation

import androidx.annotation.StringRes
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.icon.Icon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.notification.navigation.notificationNavigationRoute
import com.example.realestateapp.ui.post.navigation.postNavigationRoute
import com.example.realestateapp.ui.setting.navigation.settingNavigationRoute

/**
 * Created by tuyen.dang on 5/1/2023.
 */

enum class TopLevelDestination(
    @StringRes val title: Int,
    val icon: Icon,
    val route: String
) {
    HOME(
        title = R.string.homeTitle,
        icon = Icon.DrawableResourceIcon(RealStateIcon.TabHome),
        route = homeNavigationRoute
    ),
    POST(
        title = R.string.postTitle,
        icon = Icon.DrawableResourceIcon(RealStateIcon.TabPost),
        route = postNavigationRoute
    ),
    NOTIFICATION(
        title = R.string.notificationTitle,
        icon = Icon.DrawableResourceIcon(RealStateIcon.TabNotification),
        route = notificationNavigationRoute
    ),
    SETTING(
        title = R.string.settingTitle,
        icon = Icon.DrawableResourceIcon(RealStateIcon.TabSetting),
        route = settingNavigationRoute
    ),
}