package com.example.realestateapp.data.models.view

import androidx.annotation.StringRes
import com.example.realestateapp.designsystem.icon.AppIcon

/**
 * Created by tuyen.dang on 5/6/2023.
 */

data class SettingButton(
    @StringRes val title: Int,
    val leadingIcon: AppIcon
)
