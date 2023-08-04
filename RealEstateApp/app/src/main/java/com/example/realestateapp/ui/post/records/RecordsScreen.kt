package com.example.realestateapp.ui.post.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.realestateapp.R
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.post.PostUiState
import com.example.realestateapp.ui.post.PostViewModel
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.SEARCH_TIME
import kotlinx.coroutines.delay

/**
 * Created by tuyen.dang on 5/28/2023.
 */

@Composable
internal fun RecordsRoute(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    isMyRecords: Boolean,
    onBackClick: () -> Unit,
    onRealEstateItemClick: (Int) -> Unit
) {
    viewModel.run {
        val uiState by uiState.collectAsStateWithLifecycle()
        var filter by remember { filter }
        var isNavigateAnotherScr by remember { isNavigateAnotherScr }
        val searchResult = remember { searchResult }
        val isLoading by remember {
            derivedStateOf {
                uiState is PostUiState.Loading
            }
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is PostUiState.InitView -> {
                    getPosts(
                        isMyRecords = isMyRecords,
                        filter = filter
                    )
                }
                is PostUiState.GetSearchDataSuccess -> {
                    searchResult.run {
                        clear()
                        addAll((uiState as PostUiState.GetSearchDataSuccess).data)
                    }
                }
                else -> {}
            }
        }

        LaunchedEffect(key1 = filter) {
            if (isNavigateAnotherScr) {
                isNavigateAnotherScr = false
            } else {
                delay(SEARCH_TIME)
                searchResult.clear()
                getPosts(
                    isMyRecords = isMyRecords,
                    filter = filter
                )
            }
        }

        RecordsScreen(
            modifier = modifier,
            isLoading = isLoading,
            isMyRecords = isMyRecords,
            onBackClick = onBackClick,
            filter = filter,
            onFilterChange = remember {
                {
                    filter = it
                }
            },
            searchResult = searchResult,
            onRealEstateItemClick = remember {
                {
                    isNavigateAnotherScr = true
                    onRealEstateItemClick(it)
                }
            },
        )
    }
}

@Composable
internal fun RecordsScreen(
    modifier: Modifier,
    isLoading: Boolean,
    isMyRecords: Boolean,
    onBackClick: () -> Unit,
    filter: String,
    onFilterChange: (String) -> Unit,
    searchResult: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        bgToolbarColor = RealEstateAppTheme.colors.bgScreen,
        toolbar = {
            ToolbarView(
                title = stringResource(
                    id = if (isMyRecords) R.string.yourPostTitle
                    else R.string.savePostTitle
                ),
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
            EditTextFullIconBorderRadius(
                modifier = Modifier
                    .background(RealEstateAppTheme.colors.bgScrPrimaryLight)
                    .padding(PADDING_HORIZONTAL_SCREEN.dp),
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
            BorderLine()
        },
        contentNonScroll = {
            if (searchResult.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(RealEstateAppTheme.colors.bgScreen)
                        .weight(1f),
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(MARGIN_VIEW.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(PADDING_HORIZONTAL_SCREEN.dp),
                ) {
                    items(
                        items = searchResult,
                        key = { realEstate ->
                            realEstate.id
                        },
                    ) { realEstate ->
                        ItemRealEstate(
                            item = realEstate,
                            onItemClick = onRealEstateItemClick
                        )
                    }
                }
                Spacing(MARGIN_VIEW)
            } else {
                Spacing(MARGIN_DIFFERENT_VIEW)
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = RealEstateAppTheme.colors.progressBar
                        )
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.emptyTitle),
                        style = RealEstateTypography.body1.copy(
                            color = RealEstateAppTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                    )
                }
            }
        },
    ) { }
}
 