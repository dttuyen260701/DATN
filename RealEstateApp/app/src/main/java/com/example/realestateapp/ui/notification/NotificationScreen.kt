package com.example.realestateapp.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.designsystem.components.ItemChatGuestView
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun NotificationRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
    navigateChatScreen: (String) -> Unit
) {
    viewModel.run {
        val uiState by remember { uiState }
        var isMessengerScreen by remember { isMessengerScreen }
        val itemChatGuests = remember { itemChatGuests }
        val isLoading by remember {
            derivedStateOf {
                uiState is NotificationUiState.Loading
            }
        }

        NotificationScreen(
            modifier = modifier,
            isLoading = isLoading,
            isMessengerScreen = isMessengerScreen,
            setIsMessengerScreen = remember {
                {
                    if (isMessengerScreen != it) {
                        isMessengerScreen = it
                    }
                }
            },
            itemChatGuests = itemChatGuests,
            navigateChatScreen = remember { navigateChatScreen }
        )
    }
}

@Composable
internal fun NotificationScreen(
    modifier: Modifier,
    isLoading: Boolean,
    isMessengerScreen: Boolean,
    setIsMessengerScreen: (Boolean) -> Unit,
    itemChatGuests: MutableList<ItemChatGuest>,
    navigateChatScreen: (String) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOOLBAR_HEIGHT.dp)
            ) {
                TextButton(
                    onClick = {
                        setIsMessengerScreen(true)
                    },
                    modifier = Modifier
                        .background(
                            color = if (!isMessengerScreen) RealEstateAppTheme.colors.primary
                            else RealEstateAppTheme.colors.bgScrPrimaryLight,
                        )
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                    elevation = null
                ) {
                    Text(
                        text = stringResource(id = R.string.messengerTitle),
                        style = RealEstateTypography.body1.copy(
                            color = if (!isMessengerScreen) RealEstateAppTheme.colors.bgScrPrimaryLight
                            else RealEstateAppTheme.colors.primary
                        ),
                        modifier = Modifier
                            .padding(horizontal = PADDING_VIEW.dp)
                    )
                }
                TextButton(
                    onClick = {
                        setIsMessengerScreen(false)
                    },
                    modifier = Modifier
                        .background(
                            color = if (isMessengerScreen) RealEstateAppTheme.colors.primary
                            else RealEstateAppTheme.colors.bgScrPrimaryLight,
                        )
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                    elevation = null
                ) {
                    Text(
                        text = stringResource(id = R.string.notificationTitle),
                        style = RealEstateTypography.body1.copy(
                            color = if (isMessengerScreen) RealEstateAppTheme.colors.bgScrPrimaryLight
                            else RealEstateAppTheme.colors.primary
                        ),
                        modifier = Modifier
                            .padding(horizontal = PADDING_VIEW.dp)
                    )
                }
            }
        },
        contentNonScroll = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RealEstateAppTheme.colors.bgScrPrimaryLight),
                contentAlignment = Alignment.Center
            ) {
                if (
                    (itemChatGuests.isNotEmpty()
                            && isMessengerScreen)
                ) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .matchParentSize()
                            .background(Color.Transparent),
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(PADDING_HORIZONTAL_SCREEN.dp),
                    ) {
                        items(
                            items = itemChatGuests,
                            key = { item ->
                                item.toString()
                            },
                        ) { item ->
                            ItemChatGuestView(
                                item = item,
                                onItemClick = navigateChatScreen
                            )
                        }
                    }
                    Spacing(Constants.DefaultValue.MARGIN_VIEW)
                } else {
                    Spacing(Constants.DefaultValue.MARGIN_DIFFERENT_VIEW)
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
            }
        }
    ) { }
}
