package com.example.realestateapp.extension

import androidx.constraintlayout.compose.Visibility

/**
 * Created by tuyen.dang on 5/20/2023.
 */

internal fun setVisibility(statement: Boolean): Visibility =
    if (statement) Visibility.Visible else Visibility.Gone