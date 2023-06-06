package com.example.realestateapp.ui.notification.messager

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemMessenger
import com.example.realestateapp.data.models.User
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 6/3/2023.
 */

@Composable
internal fun MessengerRoute(
    modifier: Modifier = Modifier,
    viewModel: MessengerViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    idGuest: Int
) {
    val context = LocalContext.current
    viewModel.run {
        val user by remember { getUser() }
        val uiState by remember { uiState }
        var message by remember { message }
        var idChannel by remember { idChannel }
        val chats = remember { chats }
        var isUpLoading by remember { isUpLoading }
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        if (Constants.getIdChannel(user?.id ?: 0, idGuest) != idChannel) {
            idChannel = Constants.getIdChannel(user?.id ?: 0, idGuest)
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is MessengerUiState.InitView -> {
                    getFBDataBase()
                }
                else -> {}
            }

        }
        MessengerScreen(
            modifier = modifier,
            user = user,
            onBackClick = onBackClick,
            imgGuest = "https://cdnimg.vietnamplus.vn/uploaded/qfsqy/2019_06_21/2106_fernando_torres.jpg",
            nameGuest = "Test",
            message = message,
            onMessageChange = remember {
                {
                    message = it
                }
            },
            onUploadPhotoClick = remember {
                {
                    if (isUpLoading) {
                        context.makeToast(context.getString(R.string.uploadWarning))
                    } else {
                        requestPermissionListener(
                            permission = mutableListOf(
                                Manifest.permission.CAMERA
                            )
                        ) { results ->
                            if (results.entries.all { it.value }) {
                                uploadImageAndGetURL(
                                    onStart = {
                                        isUpLoading = true
                                        chats.add(
                                            ItemMessenger(
                                                timeMilliseconds = 22123,
                                                idUserSend = 1,
                                                messenger = "",
                                                isPhoto = true,
                                                isSending = true
                                            )
                                        )
                                        coroutineScope.launch {
                                            lazyListState.scrollToItem(chats.lastIndex)
                                        }
                                    }
                                ) {
                                    chats.add(
                                        ItemMessenger(
                                            timeMilliseconds = 22123,
                                            idUserSend = 1,
                                            messenger = it,
                                            isPhoto = true,
                                            isSending = false
                                        )
                                    )
                                    coroutineScope.launch {
                                        lazyListState.scrollToItem(chats.lastIndex)
                                    }
                                    isUpLoading = false
                                }
                            }
                        }
                    }
                }
            },
            onSendClick = remember {
                {}
            },
            lazyListState = lazyListState,
            coroutineScope = coroutineScope,
            chats = chats,
            onItemMessengerClick = remember {
                {
                    if (it.isPhoto) {
                        showDialog(
                            dialog = TypeDialog.ShowImageDialog(
                                data = mutableListOf(
                                    Image(
                                        id = -1,
                                        url = it.messenger
                                    )
                                ),
                                currentIndex = 0
                            )
                        )
                    }
                }
            },
        )
    }
}

@Composable
internal fun MessengerScreen(
    modifier: Modifier = Modifier,
    user: User?,
    onBackClick: () -> Unit,
    imgGuest: String,
    nameGuest: String,
    message: String,
    onMessageChange: (String) -> Unit,
    onUploadPhotoClick: () -> Unit,
    onSendClick: () -> Unit,
    lazyListState: LazyListState,
    coroutineScope: CoroutineScope,
    chats: MutableList<ItemMessenger>,
    onItemMessengerClick: (ItemMessenger) -> Unit
) {
    SideEffect {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(chats.lastIndex)
        }
    }
    BaseScreen(
        modifier = modifier,
        toolbar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOOLBAR_HEIGHT.dp)
                    .background(RealEstateAppTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(PADDING_VIEW.dp))
                ButtonUnRepeating(onBackClick) {
                    IconButton(
                        onClick = it,
                        modifier = Modifier
                            .size(ICON_ITEM_SIZE.dp)
                            .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                            .background(
                                color = Color.Transparent
                            ),
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
                            modifier = Modifier
                                .size(TRAILING_ICON_SIZE.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(MARGIN_VIEW.dp))
                ImageProfile(
                    size = ICON_ITEM_SIZE,
                    model = imgGuest,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(MARGIN_VIEW.dp))
                Text(
                    text = nameGuest,
                    style = RealEstateTypography.h1.copy(
                        fontSize = 15.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        contentNonScroll = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RealEstateAppTheme.colors.bgScrPrimaryLight),
            ) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.Transparent),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(PADDING_HORIZONTAL_SCREEN.dp),
                ) {
                    items(
                        items = chats,
                        key = { item ->
                            item.toString()
                        },
                    ) { item ->
                        ItemMessengerView(
                            item = item,
                            idUser = user?.id ?: -1,
                            onItemClick = onItemMessengerClick
                        )
                    }
                }
                Spacing(MARGIN_VIEW)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RealEstateAppTheme.colors.bgScreen)
                        .padding(
                            horizontal = PADDING_HORIZONTAL_SCREEN.dp,
                            vertical = PADDING_VIEW.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EditTextRadius(
                        onTextChange = onMessageChange,
                        text = message,
                        label = null,
                        typeInput = KeyboardType.Text,
                        errorText = "",
                        hint = stringResource(id = R.string.messageHint),
                        textColor = RealEstateAppTheme.colors.primary,
                        backgroundColor = RealEstateAppTheme.colors.bgTextField,
                        singleLine = false,
                        isLastEditText = true,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(PADDING_VIEW.dp))
                    ButtonUnRepeating(onUploadPhotoClick) {
                        IconButton(
                            onClick = it,
                            modifier = Modifier
                                .size(ICON_ITEM_SIZE.dp)
                                .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                                .background(
                                    color = Color.Transparent
                                ),
                        ) {
                            BaseIcon(
                                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Camera),
                                modifier = Modifier
                                    .size(MARGIN_DIFFERENT_VIEW.dp),
                                contentDescription = null,
                                tint = RealEstateAppTheme.colors.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(PADDING_VIEW.dp))
                    ButtonUnRepeating(onSendClick) {
                        IconButton(
                            onClick = it,
                            modifier = Modifier
                                .size(ICON_ITEM_SIZE.dp)
                                .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                                .background(
                                    color = Color.Transparent
                                ),
                        ) {
                            BaseIcon(
                                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Send),
                                modifier = Modifier
                                    .size(MARGIN_DIFFERENT_VIEW.dp),
                                contentDescription = null,
                                tint = RealEstateAppTheme.colors.primary
                            )
                        }
                    }
                }
            }
        }
    ) { }
}
