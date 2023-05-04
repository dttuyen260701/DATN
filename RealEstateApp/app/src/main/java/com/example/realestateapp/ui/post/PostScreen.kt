package com.example.realestateapp.ui.post

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun PostRoute(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    PostScreen()
}

@Composable
internal fun PostScreen() {

}
