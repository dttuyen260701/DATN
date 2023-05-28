package com.example.realestateapp.ui.post.addpost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.post.PostViewModel

/**
 * Created by tuyen.dang on 5/28/2023.
 */

@Composable
internal fun AddPostRoute(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    viewModel.run {
        var filter by remember { filter }
        AddPostScreen(
            modifier = modifier,
        )
    }
}

@Composable
internal fun AddPostScreen(
    modifier: Modifier,
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(title = stringResource(id = R.string.addPostTitle))
        },
    ) {

    }
}
