package com.example.realestateapp.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.realestateapp.R

/**
 * Created by tuyen.dang on 5/4/2023.
 */

object RealStateIcon {
    const val TabHome = R.drawable.ic_home
    const val TabPost = R.drawable.ic_post
    const val TabNotification = R.drawable.ic_notification
    const val TabSetting = R.drawable.ic_setting
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
