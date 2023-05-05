package com.example.realestateapp.ui.notification

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun NotificationRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    NotificationScreen()
}

@Composable
internal fun NotificationScreen() {
    Text(text = "Notification")
}
