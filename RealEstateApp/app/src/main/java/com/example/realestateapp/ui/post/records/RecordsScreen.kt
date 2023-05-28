package com.example.realestateapp.ui.post.records

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.EditTextFullIconBorderRadius
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.post.PostViewModel

/**
 * Created by tuyen.dang on 5/28/2023.
 */

@Composable
internal fun RecordsRoute(
    modifier: Modifier = Modifier,
    isMyRecords: Boolean,
    viewModel: PostViewModel = hiltViewModel()
) {
    viewModel.run {
        var filter by remember { filter }
        RecordsScreen(
            modifier = modifier,
            filter = filter,
            onFilterChange = remember {
                {
                    filter = it
                }
            }
        )
    }
}

@Composable
internal fun RecordsScreen(
    modifier: Modifier,
    filter: String,
    onFilterChange: (String) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(title = stringResource(id = R.string.managePostTitle))
            EditTextFullIconBorderRadius(
                text = filter,
                onTextChange = onFilterChange,
                hint = stringResource(id = R.string.SearchTitle),
                leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
                readOnly = false,
                borderColor = RealEstateAppTheme.colors.primary,
                leadingIconColor = RealEstateAppTheme.colors.primary,
                onLeadingIconClick = {},
                trailingIconColor = RealEstateAppTheme.colors.primary,
                onTrailingIconClick = {},
            )
        },
    ) {

    }
}
 