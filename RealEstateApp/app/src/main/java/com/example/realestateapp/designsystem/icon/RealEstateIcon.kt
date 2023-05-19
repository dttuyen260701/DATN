package com.example.realestateapp.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.realestateapp.R

/**
 * Created by tuyen.dang on 5/4/2023.
 */

object RealEstateIcon {
    const val TabHome = R.drawable.ic_home
    const val TabPost = R.drawable.ic_post
    const val TabNotification = R.drawable.ic_notification
    const val TabSetting = R.drawable.ic_setting
    val Lock = Icons.Rounded.Lock
    const val NextArrow = R.drawable.ic_arrow_right
    const val BackArrow = R.drawable.ic_arrow_left
    val Edit = Icons.Rounded.Edit
    const val PostSaved = R.drawable.ic_post_saved
    const val PostSavedOutline = R.drawable.ic_post_saved_outline
    const val Policy = R.drawable.ic_policy
    const val AboutUs = R.drawable.ic_contact_us
    const val SignOut = R.drawable.ic_signout
    const val SignIn = R.drawable.ic_signin
    const val SignUp = R.drawable.ic_signup
    const val Visibility = R.drawable.ic_visibility
    const val VisibilityOff = R.drawable.ic_visibility_off
    const val User = R.drawable.ic_user_outline
    const val Email = R.drawable.ic_mail
    const val Password = R.drawable.ic_pass
    val Search = Icons.Outlined.Search
    const val Config = R.drawable.ic_config
    val PhoneOutLine = Icons.Outlined.Phone
    const val Square = R.drawable.ic_square
    const val Floors = R.drawable.ic_floors
    const val Bed = R.drawable.ic_bed
    const val Dollar = R.drawable.ic_dollar
    const val LeftArrow = R.drawable.ic_left_arrow
    const val Warning = R.drawable.ic_warning
    const val Phone = R.drawable.ic_phone
    const val Direct = R.drawable.ic_direct
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */

sealed class AppIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : AppIcon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : AppIcon()
}
