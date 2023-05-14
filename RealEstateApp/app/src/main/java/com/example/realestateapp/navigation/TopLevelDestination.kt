package com.example.realestateapp.navigation

import androidx.annotation.StringRes
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.ui.home.navigation.homeNavigationRoute
import com.example.realestateapp.ui.notification.navigation.notificationNavigationRoute
import com.example.realestateapp.ui.post.navigation.postNavigationRoute
import com.example.realestateapp.ui.setting.navigation.settingNavigationRoute

/**
 * Created by tuyen.dang on 5/1/2023.
 */

enum class TopLevelDestination(
    @StringRes val title: Int,
    val icon: AppIcon,
    val route: String
) {
    HOME(
        title = R.string.homeTitle,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.TabHome),
        route = homeNavigationRoute
    ),
    POST(
        title = R.string.postTitle,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.TabPost),
        route = postNavigationRoute
    ),
    NOTIFICATION(
        title = R.string.notificationTitle,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.TabNotification),
        route = notificationNavigationRoute
    ),
    SETTING(
        title = R.string.settingTitle,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.TabSetting),
        route = settingNavigationRoute
    ),
}