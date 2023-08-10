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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.realestateapp.R
import com.example.realestateapp.ui.MainActivity.Companion.getDataChild
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemNotification
import com.example.realestateapp.designsystem.components.BorderLine
import com.example.realestateapp.designsystem.components.ItemChatGuestView
import com.example.realestateapp.designsystem.components.ItemNotification
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.RequireLoginScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.FireBaseRef.CHANNEL_GUEST
import com.example.realestateapp.util.Constants.FireBaseRef.CHANNEL_POST
import com.example.realestateapp.util.Constants.FireBaseRef.PROPERTY_READ
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun NotificationRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
    navigateSignIn: () -> Unit,
    navigateChatScreen: (String) -> Unit,
    navigateToRealEstateDetail: (Int) -> Unit
) {
    viewModel.run {
        val user by remember { getUser() }
        val uiState by uiState.collectAsStateWithLifecycle()
        var isMessengerScreen by remember { isMessengerScreen }
        val itemChatGuests = remember { itemChatGuests }
        val itemNotifications = remember { itemNotifications }
        val isLoading by remember {
            derivedStateOf {
                uiState is NotificationUiState.Loading
            }
        }

        val childChannelListEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(ItemChatGuest::class.java)?.let {
                    itemChatGuests.add(0, it)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(ItemChatGuest::class.java)?.let { item ->
                    val index = itemChatGuests.indexOfFirst { it.idGuest == item.idGuest }
                    itemChatGuests.removeAt(index)
                    itemChatGuests.add(0, item)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        val childPostEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(ItemNotification::class.java)?.let {
                    itemNotifications.add(0, it)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(ItemNotification::class.java)?.let { item ->
                    val index =
                        itemNotifications.indexOfFirst { it.idPost == item.idPost }
                    itemNotifications.removeAt(index)
                    itemNotifications.add(0, item)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is NotificationUiState.InitView -> {
                    getUser().value?.run {
                        if (isMessengerScreen) {
                            getDataChild(CHANNEL_GUEST).child(id.toString())
                                .addChildEventListener(childChannelListEventListener)
                            getDataChild(CHANNEL_GUEST).child(id.toString())
                                .get()
                                .addOnSuccessListener { result ->
                                    itemChatGuests.run {
                                        clear()
                                        for (item in result.children) {
                                            item.getValue(ItemChatGuest::class.java)?.let {
                                                add(it)
                                            }
                                        }
                                        reverse()
                                    }
                                }.addOnFailureListener {
                                    updateUiStateDone()
                                }
                        } else {
                            getDataChild(CHANNEL_POST).child(id.toString())
                                .addChildEventListener(childPostEventListener)
                            getDataChild(CHANNEL_POST).child(id.toString())
                                .get()
                                .addOnSuccessListener { result ->
                                    itemNotifications.run {
                                        clear()
                                        for (item in result.children) {
                                            item.getValue(ItemNotification::class.java)?.let {
                                                add(it)
                                            }
                                        }
                                        reverse()
                                    }
                                }.addOnFailureListener {
                                    updateUiStateDone()
                                }
                        }
                    }
                }
                else -> {}
            }
        }

        if (user == null) {
            RequireLoginScreen(
                message = stringResource(id = R.string.messageRequireLogin),
                navigateSignIn = navigateSignIn
            )
        } else {
            NotificationScreen(
                modifier = modifier,
                isLoading = isLoading,
                isMessengerScreen = isMessengerScreen,
                setIsMessengerScreen = remember {
                    {
                        if (isMessengerScreen != it) {
                            isMessengerScreen = it
//                            uiState = NotificationUiState.InitView
                        }
                    }
                },
                itemChatGuests = itemChatGuests,
                itemNotifications = itemNotifications,
                onItemNotificationClick = remember {
                    {
                        getDataChild(CHANNEL_POST)
                            .child(getUser().value?.id?.toString() ?: "")
                            .child(it.idPost.toString())
                            .child(PROPERTY_READ).setValue(true)
                        navigateToRealEstateDetail(it.idPost)
                    }
                },
                navigateChatScreen = remember {
                    {
                        getDataChild(CHANNEL_GUEST)
                            .child(getUser().value?.id?.toString() ?: "")
                            .child(it).child(PROPERTY_READ).setValue(true)
                        navigateChatScreen(it)
                    }
                }
            )
        }
    }
}

@Composable
internal fun NotificationScreen(
    modifier: Modifier,
    isLoading: Boolean,
    isMessengerScreen: Boolean,
    setIsMessengerScreen: (Boolean) -> Unit,
    itemChatGuests: MutableList<ItemChatGuest>,
    itemNotifications: MutableList<ItemNotification>,
    onItemNotificationClick: (ItemNotification) -> Unit,
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
                            else RealEstateAppTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
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
                            else RealEstateAppTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = PADDING_VIEW.dp)
                    )
                }
            }
            BorderLine()
        },
        contentNonScroll = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RealEstateAppTheme.colors.bgScrPrimaryLight),
                contentAlignment = Alignment.Center
            ) {
                when {
                    itemChatGuests.isNotEmpty() && isMessengerScreen -> {
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
                        Spacing(MARGIN_VIEW)
                    }
                    itemNotifications.isNotEmpty() && !isMessengerScreen -> {
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
                                items = itemNotifications,
                                key = { item ->
                                    item.toString()
                                },
                            ) { item ->
                                ItemNotification(
                                    item = item,
                                    onItemClick = onItemNotificationClick
                                )
                            }
                        }
                        Spacing(MARGIN_VIEW)
                    }
                    else -> {
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
                }
            }
        }
    ) { }
}
