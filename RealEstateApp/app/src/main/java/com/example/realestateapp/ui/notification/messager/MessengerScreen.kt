package com.example.realestateapp.ui.notification.messager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 6/3/2023.
 */

@Composable
internal fun MessengerRoute(
    modifier: Modifier = Modifier,
    viewModel: MessengerViewModel = hiltViewModel(),
    idGuest: String
) {
    viewModel.run {
        val uiState by remember { uiState }

        MessengerScreen(
            modifier = modifier
        )
    }
}

@Composable
internal fun MessengerScreen(
    modifier: Modifier = Modifier
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            
        }
    ) { }
}
