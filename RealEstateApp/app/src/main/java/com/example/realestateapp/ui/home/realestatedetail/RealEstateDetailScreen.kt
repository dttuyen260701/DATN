package com.example.realestateapp.ui.home.realestatedetail

import android.Manifest
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.PostStatus
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.models.view.RealEstateProperty
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.*
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.BOTTOM_ICON_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME
import com.example.realestateapp.util.Constants.MessageErrorAPI.AUTHENTICATION_ERROR
import com.google.android.gms.maps.model.LatLng

/**
 * Created by tuyen.dang on 5/16/2023.
 */

@Composable
internal fun RealEstateDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: RealEstateDetailViewModel = hiltViewModel(),
    realEstateId: Int,
    onRealEstateItemClick: (Int) -> Unit,
    navigateToEditPost: (Int) -> Unit,
    navigateMessengerScreen: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    viewModel.run {
        val user by remember { getUser() }
        var realEstateItem by remember { realEstateItem }
        val realEstateProperty = remember { realEstateProperty }
        val realEstatesSamePrice = remember { realEstatesSamePrice }
        val realEstatesCluster = remember { realEstatesCluster }
        var uiState by remember { uiState }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is RealEstateDetailUiState.InitView -> {
                    getRealEstateDetail(realEstateId)
                }
                is RealEstateDetailUiState.GetRealEstateDetailSuccess -> {
                    realEstateItem =
                        (uiState as RealEstateDetailUiState.GetRealEstateDetailSuccess).data
                    getRealEstatesSamePrice(realEstateId)
                }
                is RealEstateDetailUiState.GetSamePriceSuccess -> {
                    realEstatesSamePrice.run {
                        clear()
                        addAll((uiState as RealEstateDetailUiState.GetSamePriceSuccess).data)
                    }
                    getRealEstatesCluster(realEstateId)
                }
                is RealEstateDetailUiState.GetClusterSuccess -> {
                    realEstatesCluster.run {
                        clear()
                        addAll((uiState as RealEstateDetailUiState.GetClusterSuccess).data)
                    }
                    uiState = RealEstateDetailUiState.Done
                }
                else -> {}
            }
        }
        RealEstateDetailScreen(
            modifier = modifier,
            user = user,
            item = realEstateItem,
            itemProperties = realEstateProperty,
            onImageClick = remember {
                {
                    showDialog(
                        dialog = TypeDialog.ShowImageDialog(
                            data = realEstateItem.images,
                            currentIndex = it
                        )
                    )
                }
            },
            realEstatesSamePrice = realEstatesSamePrice,
            realEstatesCluster = realEstatesCluster,
            onRealEstateItemClick = remember { onRealEstateItemClick },
            onItemSaveClick = remember {
                { idPost ->
                    updateSavedPost(idPost = idPost) { idResult ->
                        if (idResult == realEstateItem.postId) {
                            realEstateItem = realEstateItem.copy(isSaved = !realEstateItem.isSaved)
                        }
                    }
                }
            },
            navigateToEditPost = navigateToEditPost,
            onBackClick = remember { onBackClick },
            onDirectClick = remember {
                { latitude, longitude ->
                    context.openMap(
                        latitude = latitude,
                        longitude = longitude
                    )
                }
            },
            onCallClick = remember {
                {
                    if (user != null) {
                        requestPermissionListener(
                            permission = mutableListOf(Manifest.permission.CALL_PHONE)
                        ) {
                            it.entries.forEach { result ->
                                if (result.key == Manifest.permission.CALL_PHONE && result.value) {
                                    realEstateItem.ownerPhone?.let { phoneNumber ->
                                        context.callPhone(phoneNumber)
                                    }
                                }
                            }
                        }
                    } else {
                        context.makeToast(AUTHENTICATION_ERROR)
                    }
                }
            },
            onChatClick = remember { navigateMessengerScreen },
            onReportClick = remember {
                {}
            }
        )
    }
}

@Composable
internal fun RealEstateDetailScreen(
    modifier: Modifier = Modifier,
    user: User?,
    item: RealEstateDetail,
    itemProperties: MutableList<RealEstateProperty>,
    onImageClick: (Int) -> Unit,
    realEstatesSamePrice: MutableList<RealEstateList>,
    realEstatesCluster: MutableList<RealEstateList>,
    onRealEstateItemClick: (Int) -> Unit,
    onItemSaveClick: (Int) -> Unit,
    navigateToEditPost: (Int) -> Unit,
    onBackClick: () -> Unit,
    onDirectClick: (Double, Double) -> Unit,
    onCallClick: () -> Unit,
    onChatClick: (String) -> Unit,
    onReportClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    val transition = updateTransition(scrollState.value, label = "")
    val threadAnimation = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
            .toInt() * 3 / 4
    }
    val bgToolbar by transition.animateColor(
        transitionSpec = {
            spring(
                stiffness = 50f,
                dampingRatio = 2f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) RealEstateAppTheme.colors.primary else Color.Transparent
    }
    val bgIcon by transition.animateColor(
        transitionSpec = {
            spring(
                stiffness = TWEEN_ANIMATION_TIME.toFloat(),
                dampingRatio = 2f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) Color.Transparent else RealEstateAppTheme.colors.bgIconsBlack50
    }
    val toolbarHeight by transition.animateInt(
        transitionSpec = {
            spring(
                stiffness = 700f,
                dampingRatio = 0.7f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) TOOLBAR_HEIGHT else TOOLBAR_HEIGHT * 2
    }
    val paddingToolbar by transition.animateInt(
        transitionSpec = {
            spring(
                stiffness = 700f,
                dampingRatio = 0.7f
            )
        },
        label = ""
    ) {
        if (it > threadAnimation) PADDING_VIEW else PADDING_HORIZONTAL_SCREEN
    }

    item.run {
        BaseScreen(
            modifier = modifier,
            scrollState = scrollState,
            paddingHorizontal = 0,
            footer = {
                BorderLine()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BOTTOM_ICON_SIZE.dp)
                        .background(RealEstateAppTheme.colors.bgScrPrimaryLight),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onDirectClick(latitude, longitude)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            disabledBackgroundColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(
                            vertical = 0.dp,
                            horizontal = MARGIN_VIEW.dp
                        )
                    ) {
                        TextIcon(
                            text = stringResource(id = R.string.directTitle),
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Direct),
                            size = 14,
                            textColor = RealEstateAppTheme.colors.primary,
                            iconTint = RealEstateAppTheme.colors.primary
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(0.2f.dp)
                            .fillMaxHeight()
                            .background(RealEstateAppTheme.colors.primary)
                    )
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = onCallClick,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            disabledBackgroundColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(
                            vertical = 0.dp,
                            horizontal = MARGIN_VIEW.dp
                        )
                    ) {
                        TextIcon(
                            text = stringResource(id = R.string.callTitle),
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Phone),
                            size = 14,
                            textColor = RealEstateAppTheme.colors.primary,
                            iconTint = RealEstateAppTheme.colors.primary
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(0.2f.dp)
                            .fillMaxHeight()
                            .background(RealEstateAppTheme.colors.primary)
                    )
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onChatClick(ownerId.toString())
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            disabledBackgroundColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(
                            vertical = 0.dp,
                            horizontal = MARGIN_VIEW.dp
                        )
                    ) {
                        TextIcon(
                            text = stringResource(id = R.string.chatTitle),
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Chat),
                            size = 14,
                            textColor = RealEstateAppTheme.colors.primary,
                            iconTint = RealEstateAppTheme.colors.primary
                        )
                    }
                }
            }
        ) {
            SlideShowImage(
                photos = images,
                onItemClick = onImageClick
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = PADDING_HORIZONTAL_SCREEN.dp)
            ) {
                val (
                    tvName, tvViews, tvAddress, tvCreatedDate,
                    tvPrice, tvPostId, borderTopGrid, gridLayout,
                    borderBottomGrid, tvDescriptionTitle,
                    tvDescription, samePriceList, clusterList
                ) = createRefs()
                val (
                    tvMapTitle, mapView, tvContact, tvNameUser,
                    tvPhoneUser, tvStatus, btnReport, comboOptionView
                ) = createRefs()
                Text(
                    text = title
                        ?: stringResource(id = R.string.titleDefault),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .constrainAs(tvName) {
                            top.linkTo(parent.top, PADDING_HORIZONTAL_SCREEN.dp)
                            linkTo(
                                start = parent.start,
                                end = tvViews.start,
                                bias = 0f,
                                startMargin = PADDING_HORIZONTAL_SCREEN.dp
                            )
                            width = Dimension.fillToConstraints
                        }
                )
                TextIcon(
                    text = views.toString(),
                    icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Visibility),
                    isIconFirst = false,
                    size = 14,
                    modifier = Modifier
                        .constrainAs(tvViews) {
                            top.linkTo(tvName.top)
                            end.linkTo(parent.end, PADDING_HORIZONTAL_SCREEN.dp)
                            baseline.linkTo(tvName.baseline)
                        }
                )
                Text(
                    text = address,
                    style = RealEstateTypography.body1.copy(
                        fontSize = 13.sp,
                        color = Color.Black.copy(ALPHA_TITLE),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .constrainAs(tvAddress) {
                            top.linkTo(tvName.bottom)
                            linkTo(
                                start = tvName.start,
                                end = tvPrice.start,
                                bias = 0f
                            )
                            width = Dimension.fillToConstraints
                        }
                )
                Text(
                    text = stringResource(id = R.string.idPostTitle, postId),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .constrainAs(tvPostId) {
                            top.linkTo(tvAddress.bottom)
                            linkTo(
                                start = tvAddress.start,
                                end = tvPostId.end,
                                bias = 0f
                            )
                        }
                )
                Text(
                    text = createdDate,
                    style = RealEstateTypography.body1.copy(
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .constrainAs(tvCreatedDate) {
                            top.linkTo(tvPostId.top)
                            linkTo(
                                start = tvPostId.end,
                                startMargin = MARGIN_VIEW.dp,
                                end = tvAddress.end,
                                endMargin = MARGIN_VIEW.dp,
                                bias = 0f
                            )
                        }
                )
                TextIcon(
                    text = price.formatToMoney(),
                    icon = AppIcon.DrawableResourceIcon(RealEstateIcon.VND),
                    size = 23,
                    isIconFirst = false,
                    textColor = RealEstateAppTheme.colors.primary,
                    iconTint = RealEstateAppTheme.colors.primary,
                    modifier = Modifier
                        .constrainAs(tvPrice) {
                            linkTo(top = tvAddress.top, bottom = tvPostId.bottom, bias = 0f)
                            linkTo(
                                start = tvAddress.end,
                                startMargin = MARGIN_VIEW.dp,
                                end = parent.end,
                                endMargin = PADDING_HORIZONTAL_SCREEN.dp,
                                bias = 1f
                            )
                        }
                )
                Text(
                    text = stringResource(
                        id = when (item.status) {
                            PostStatus.Pending.id -> R.string.pendingStatus
                            PostStatus.Accepted.id -> R.string.acceptStatus
                            PostStatus.Reject.id -> R.string.rejectStatus
                            else -> R.string.allTitle
                        }
                    ),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                        .constrainAs(tvStatus) {
                            top.linkTo(tvPostId.bottom, MARGIN_DIFFERENT_VIEW.dp)
                            start.linkTo(parent.start, PADDING_HORIZONTAL_SCREEN.dp)
                            visibility = setVisibility(user?.id == ownerId)
                        }
                        .background(
                            when (item.status) {
                                PostStatus.Pending.id -> RealEstateAppTheme.colors.primary
                                PostStatus.Accepted.id -> RealEstateAppTheme.colors.progressBar
                                PostStatus.Reject.id -> RealEstateAppTheme.colors.bgBtnDisable
                                else -> Color.Transparent
                            }
                        )
                        .padding(
                            vertical = PADDING_VIEW.dp,
                            horizontal = MARGIN_VIEW.dp
                        )
                )
                BorderLine(
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(borderTopGrid) {
                            top.linkTo(
                                tvStatus.bottom,
                                MARGIN_DIFFERENT_VIEW.dp,
                                MARGIN_DIFFERENT_VIEW.dp
                            )
                        },
                    height = 0.2f,
                    bgColor = Color.Gray
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MARGIN_VIEW.dp),
                    contentPadding = PaddingValues(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(gridLayout) {
                            top.linkTo(borderTopGrid.bottom, MARGIN_VIEW.dp)
                        }
                ) {
                    items(itemProperties.size) {
                        TextIconVertical(
                            text = itemProperties[it].title,
                            icon = itemProperties[it].leadingIcon,
                            textColor = RealEstateAppTheme.colors.primary,
                            iconTint = RealEstateAppTheme.colors.primary,
                            bgIconTint = RealEstateAppTheme.colors.bgTextField,
                            size = 14
                        )
                    }
                }
                BorderLine(
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(borderBottomGrid) {
                            top.linkTo(gridLayout.bottom, MARGIN_VIEW.dp)
                        },
                    height = 0.2f,
                    bgColor = Color.Gray
                )
                Text(
                    text = stringResource(id = R.string.descriptionTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvDescriptionTitle) {
                            top.linkTo(borderBottomGrid.bottom, MARGIN_DIFFERENT_VIEW.dp)
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                bias = 0f
                            )
                            width = Dimension.fillToConstraints
                        }
                )
                Text(
                    text = description ?: "",
                    style = RealEstateTypography.body1.copy(
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvDescription) {
                            top.linkTo(tvDescriptionTitle.bottom)
                            linkTo(
                                start = tvDescriptionTitle.start,
                                end = tvDescriptionTitle.end,
                                bias = 0f
                            )
                        }
                )
                if (latitude != 0.0 && longitude != 0.0) {
                    Text(
                        text = stringResource(id = R.string.mapTitle),
                        style = RealEstateTypography.body1.copy(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                            .constrainAs(tvMapTitle) {
                                top.linkTo(tvDescription.bottom, MARGIN_DIFFERENT_VIEW.dp)
                                linkTo(
                                    start = parent.start,
                                    end = parent.end,
                                    bias = 0f
                                )
                                width = Dimension.fillToConstraints
                            }
                    )
                    MapviewShowMarker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f)
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                            .constrainAs(mapView) {
                                top.linkTo(
                                    tvMapTitle.bottom, MARGIN_VIEW.dp
                                )
                            },
                        location = LatLng(latitude, longitude)
                    )
                }
                Text(
                    text = stringResource(id = R.string.contactTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvContact) {
                            top.linkTo(
                                (if (latitude != 0.0 && longitude != 0.0) mapView else tvDescriptionTitle).bottom,
                                MARGIN_DIFFERENT_VIEW.dp
                            )
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                bias = 0f
                            )
                            width = Dimension.fillToConstraints
                        }
                )
                TextIcon(
                    text = ownerName ?: "",
                    icon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
                    textColor = Color.Black.copy(ALPHA_TITLE),
                    size = 13,
                    iconTint = Color.Black.copy(ALPHA_TITLE),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvNameUser) {
                            top.linkTo(tvContact.bottom, MARGIN_VIEW.dp)
                        }
                )
                TextIcon(
                    text = if (user != null) ownerPhone ?: "" else "********",
                    icon = AppIcon.ImageVectorIcon(RealEstateIcon.PhoneOutLine),
                    textColor = Color.Black.copy(ALPHA_TITLE),
                    size = 13,
                    iconTint = Color.Black.copy(ALPHA_TITLE),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                        .constrainAs(tvPhoneUser) {
                            top.linkTo(tvNameUser.bottom, MARGIN_VIEW.dp)
                        }
                )
                TextButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .constrainAs(btnReport) {
                            end.linkTo(parent.end, PADDING_HORIZONTAL_SCREEN.dp)
                            linkTo(tvContact.top, tvPhoneUser.bottom)
                            visibility = setVisibility(ownerId != user?.id)
                        }
                        .background(
                            color = if (ownerId != user?.id) RealEstateAppTheme.colors.bgTextField
                            else Color.Transparent,
                            shape = RoundedCornerShape(ROUND_DIALOG.dp)
                        )
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = if (ownerId != user?.id) RealEstateAppTheme.colors.primary
                                else Color.Transparent,
                            ),
                            shape = RoundedCornerShape(ROUND_DIALOG.dp)
                        ),
                    onClick = {
                        onReportClick(postId)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent
                    ),
                ) {
                    TextIcon(
                        text = stringResource(id = R.string.reportTitle),
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Warning),
                        size = 14,
                        textColor = RealEstateAppTheme.colors.primary,
                        iconTint = RealEstateAppTheme.colors.primary,
                        modifier = Modifier
                            .padding(horizontal = PADDING_VIEW.dp)
                    )
                }
                if (ownerId == user?.id) {
                    Row(
                        modifier = Modifier
                            .constrainAs(comboOptionView) {
                                top.linkTo(
                                    tvPhoneUser.bottom, MARGIN_DIFFERENT_VIEW.dp
                                )
                            }
                            .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.comboOptionsName, comboOptionName),
                            style = RealEstateTypography.body1.copy(
                                fontSize = 15.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            ),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = stringResource(id = R.string.comboOptionsDate, dueDate),
                            style = RealEstateTypography.body1.copy(
                                fontSize = 15.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            ),
                        )
                    }
                } else {
                    realEstatesSamePrice.let {
                        if (it.size > 0) {
                            ListItemHome(
                                title = stringResource(id = R.string.samePriceTitle),
                                listRealEstate = it,
                                onItemClick = { id -> onRealEstateItemClick(id) },
                                modifier = Modifier
                                    .constrainAs(samePriceList) {
                                        top.linkTo(
                                            tvPhoneUser.bottom, MARGIN_DIFFERENT_VIEW.dp
                                        )
                                    }
                            )
                        }
                    }
                    realEstatesCluster.let {
                        if (it.size > 0) {
                            ListItemHome(
                                title = stringResource(id = R.string.suggestTitle),
                                listRealEstate = it,
                                onItemClick = { id -> onRealEstateItemClick(id) },
                                modifier = Modifier
                                    .constrainAs(clusterList) {
                                        top.linkTo(
                                            (if (realEstatesSamePrice.size > 0) samePriceList else tvPhoneUser).bottom,
                                            (if (realEstatesSamePrice.size > 0) MARGIN_VIEW else MARGIN_DIFFERENT_VIEW).dp
                                        )
                                    }
                            )
                        }
                    }
                }

            }
        }

        ToolbarWAnimation(
            modifier = Modifier
                .padding(horizontal = paddingToolbar.dp),
            toolbarHeight = toolbarHeight,
            title = if (scrollState.value > threadAnimation) item.title ?: ""
            else "",
            bgColor = bgToolbar,
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
            onLeadingIconClick = onBackClick,
            trainingIcon = if (ownerId == user?.id) {
                AppIcon.ImageVectorIcon(RealEstateIcon.Edit)
            } else {
                if (isSaved) AppIcon.DrawableResourceIcon(RealEstateIcon.PostSaved) else
                    AppIcon.DrawableResourceIcon(RealEstateIcon.PostSavedOutline)
            },
            onTrainingIconClick = {
                if (ownerId == user?.id) {
                    navigateToEditPost(postId)
                } else {
                    onItemSaveClick(postId)
                }
            },
            bgIcon = bgIcon
        )
    }
}
