package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.PostStatus
import com.example.realestateapp.data.models.ItemChatGuest
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.ItemMessenger
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.formatToMoney
import com.example.realestateapp.extension.formatToUnit
import com.example.realestateapp.extension.setVisibility
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/12/2023.
 */

@Composable
internal fun ItemMessengerView(
    modifier: Modifier = Modifier,
    item: ItemMessenger,
    idUser: Int,
    onItemClick: (ItemMessenger) -> Unit
) {
    item.run {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding((if (isPhoto) 0 else PADDING_VIEW).dp)
                .then(modifier),
            horizontalAlignment = if (idUserSend == idUser || isSending) Alignment.End
            else Alignment.Start
        ) {
            if (isSending) {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                )
            } else {
                if (!isPhoto) {
                    Text(
                        text = messenger,
                        style = RealEstateTypography.body1.copy(
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = if (idUserSend == idUser) TextAlign.End else TextAlign.Start
                        ),
                        modifier = Modifier
                            .widthIn(
                                min = 0.dp,
                                max = (LocalConfiguration.current.screenWidthDp * 0.8).dp
                            )
                            .background(
                                color = if (idUserSend == idUser) RealEstateAppTheme.colors.primary
                                else RealEstateAppTheme.colors.progressBar,
                                shape = RoundedCornerShape(ROUND_DIALOG.dp)
                            )
                            .clickable {
                                onItemClick(item)
                            }
                            .padding(
                                vertical = PADDING_VIEW.dp,
                                horizontal = MARGIN_VIEW.dp
                            )
                    )
                } else {
                    AsyncImage(
                        model = messenger,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.sale_real_estate),
                        error = painterResource(id = R.drawable.sale_real_estate),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(RoundedCornerShape(ROUND_RECTANGLE.dp))
                            .widthIn(
                                min = 0.dp,
                                max = (LocalConfiguration.current.screenWidthDp * 0.8).dp
                            )
                            .background(RealEstateAppTheme.colors.bgTextField)
                            .clickable {
                                onItemClick(item)
                            }
                    )
                }
            }
        }
    }
}

@Composable
internal fun ItemChatGuestView(
    modifier: Modifier = Modifier,
    item: ItemChatGuest,
    onItemClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
            .clickable {
                onItemClick(item.idGuest)
            }
    ) {
        val (imgGuest, tvName, tvLastMessage) = createRefs()
        val verticalGuideLine = createGuidelineFromTop(0.5f)
        ImageProfile(
            size = TOOLBAR_HEIGHT,
            model = item.imageGuest,
            modifier = Modifier
                .constrainAs(imgGuest) {
                    linkTo(parent.top, parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = item.nameGuest,
            style = RealEstateTypography.h1.copy(
                fontSize = 15.sp,
                color = RealEstateAppTheme.colors.primary,
                textAlign = TextAlign.Start
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(tvName) {
                    linkTo(
                        start = imgGuest.end,
                        startMargin = PADDING_VIEW.dp,
                        end = parent.end,
                        bias = 0f
                    )
                    linkTo(
                        top = parent.top,
                        bottom = verticalGuideLine,
                        bias = 1f
                    )
                    width = Dimension.fillToConstraints
                }
        )
        val sendTitle = if (!item.isGuestSend) "${stringResource(id = R.string.youTitle)} " else ""
        Text(
            text = sendTitle.plus(item.lastMessage),
            style = RealEstateTypography.h1.copy(
                fontSize = 13.sp,
                color = RealEstateAppTheme.colors.primary,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(tvLastMessage) {
                    linkTo(
                        start = imgGuest.end,
                        startMargin = PADDING_VIEW.dp,
                        end = parent.end,
                        bias = 0f
                    )
                    linkTo(
                        top = verticalGuideLine,
                        bottom = parent.bottom,
                        bias = 0f
                    )
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Composable
internal fun ItemType(
    modifier: Modifier = Modifier,
    item: ItemChoose,
    borderColor: Color = RealEstateAppTheme.colors.primary,
    borderColorSelected: Color = RealEstateAppTheme.colors.bgTextField,
    textColor: Color = RealEstateAppTheme.colors.primary,
    textColorSelected: Color = Color.White,
    bgColor: Color = RealEstateAppTheme.colors.bgTextField,
    bgColorSelected: Color = RealEstateAppTheme.colors.primary,
    onItemClick: (ItemChoose) -> Unit,
) {
    item.run {
        Text(text = name, style = RealEstateTypography.body1.copy(
            color = if (isSelected) textColorSelected else textColor,
            textAlign = TextAlign.Center
        ), modifier = Modifier
            .wrapContentHeight()
            .border(
                BorderStroke(
                    width = (0.5f).dp,
                    color = if (isSelected) borderColorSelected else borderColor
                ), shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onItemClick(this@run)
            }
            .background(
                color = if (isSelected) bgColorSelected else bgColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .then(modifier))
    }
}

@Composable
internal fun ItemRealEstate(
    modifier: Modifier = Modifier,
    item: RealEstateList,
    onItemClick: (Int) -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(ROUND_RECTANGLE.dp)
    val configuration = LocalConfiguration.current
    item.run {
        ConstraintLayout(
            modifier = modifier
                .wrapContentHeight()
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(roundedCornerShape)
                .background(
                    color = Color.White,
                    shape = roundedCornerShape
                )
                .border(
                    BorderStroke(
                        width = (0.5f).dp,
                        color = Color.Gray.copy(0.3f)
                    ),
                    shape = roundedCornerShape
                )
                .clickable {
                    onItemClick(item.id)
                }
        ) {
            val (img, titleStatus, tvViews, tvName, tvCreatedDate, tvAddress,
                tvSquare, tvFloors, tvBedrooms, tvPrice) = createRefs()

            AsyncImage(
                model = imageUrl ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                    },
                placeholder = painterResource(id = R.drawable.sale_real_estate)
            )
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                    .background(
                        when (item.status) {
                            PostStatus.Pending.id -> RealEstateAppTheme.colors.primary
                            PostStatus.Accepted.id -> RealEstateAppTheme.colors.progressBar
                            PostStatus.Reject.id -> RealEstateAppTheme.colors.bgBtnDisable
                            else -> Color.Transparent
                        }
                    )
                    .constrainAs(titleStatus) {
                        top.linkTo(parent.top, MARGIN_VIEW.dp)
                        end.linkTo(parent.end, MARGIN_VIEW.dp)
                        visibility = setVisibility(item.status != PostStatus.Default.id)
                    }
                    .padding(
                        vertical = PADDING_VIEW.dp,
                        horizontal = MARGIN_VIEW.dp
                    ),
                text = stringResource(
                    id = when (item.status) {
                        PostStatus.Pending.id -> R.string.pendingStatus
                        PostStatus.Accepted.id -> R.string.acceptStatus
                        PostStatus.Reject.id -> R.string.rejectStatus
                        else -> R.string.allTitle
                    }
                ),
                style = RealEstateTypography.body1.copy(
                    color = Color.White,
                )
            )
            TextIcon(
                text = views.formatToUnit(),
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Visibility),
                isIconFirst = false,
                size = 14,
                modifier = Modifier
                    .constrainAs(tvViews) {
                        top.linkTo(img.bottom, MARGIN_VIEW.dp)
                        end.linkTo(parent.end, MARGIN_VIEW.dp)
                    }
            )
            Text(
                text = title ?: stringResource(id = R.string.titleDefault),
                style = RealEstateTypography.body1.copy(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvName) {
                        top.linkTo(tvViews.top)
                        linkTo(
                            start = parent.start,
                            startMargin = MARGIN_VIEW.dp,
                            end = tvViews.start,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = address,
                style = RealEstateTypography.body1.copy(
                    fontSize = 12.sp,
                    color = Color.Black.copy(ALPHA_TITLE),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(tvAddress) {
                        top.linkTo(tvName.bottom)
                        linkTo(
                            start = tvName.start,
                            end = parent.end,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = createdDate,
                style = RealEstateTypography.body1.copy(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvCreatedDate) {
                        top.linkTo(tvAddress.bottom)
                        linkTo(
                            start = parent.start,
                            startMargin = MARGIN_VIEW.dp,
                            end = tvPrice.start,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            TextIcon(
                text = stringResource(R.string.squareUnit, square.toString()),
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Square),
                size = 12,
                modifier = Modifier
                    .constrainAs(tvSquare) {
                        linkTo(
                            top = tvCreatedDate.bottom,
                            topMargin = PADDING_VIEW.dp,
                            bottom = parent.bottom,
                            bottomMargin = MARGIN_DIFFERENT_VIEW.dp,
                        )
                        start.linkTo(parent.start, MARGIN_VIEW.dp)
                    }
            )
            floors?.let {
                TextIcon(
                    text = it.toString(),
                    icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Floors),
                    size = 12,
                    modifier = Modifier
                        .constrainAs(tvFloors) {
                            linkTo(top = tvSquare.top, bottom = tvSquare.bottom)
                            start.linkTo(tvSquare.end, MARGIN_VIEW.dp)
                        }
                )
            }
            bedRooms?.let {
                TextIcon(
                    text = it.toString(),
                    icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed),
                    size = 12,
                    modifier = Modifier
                        .constrainAs(tvBedrooms) {
                            linkTo(top = tvSquare.top, bottom = tvSquare.bottom)
                            start.linkTo(
                                (if (floors != null) tvFloors else tvSquare).end,
                                MARGIN_VIEW.dp
                            )
                        }
                )
            }
            TextIcon(
                text = price.formatToMoney(),
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.VND),
                size = 23,
                isIconFirst = false,
                modifier = Modifier
                    .constrainAs(tvPrice) {
                        linkTo(top = tvCreatedDate.top, bottom = tvSquare.bottom, bias = 1f)
                        linkTo(
                            start = (if (bedRooms != null) tvBedrooms
                            else if (floors != null) tvFloors
                            else tvSquare).end,
                            startMargin = MARGIN_VIEW.dp,
                            end = parent.end,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 1f
                        )
                    }
            )
        }
    }
}

@Composable
internal fun ItemChoiceDialog(
    modifier: Modifier = Modifier,
    item: ItemChoose,
    textColor: Color = RealEstateAppTheme.colors.primary,
    onItemClick: (ItemChoose) -> Unit
) {
    item.run {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onItemClick(item) }
                .padding(vertical = MARGIN_VIEW.dp)
        ) {
            Text(
                text = name,
                style = RealEstateTypography.body1.copy(
                    color = textColor
                ),
                modifier = Modifier
                    .weight(1f)
            )
            RadioButton(
                selected = item.isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = RealEstateAppTheme.colors.primary,
                    unselectedColor = RealEstateAppTheme.colors.bgScrPrimaryLight,
                    disabledColor = Color.Gray
                ),
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = RealEstateAppTheme.colors.primary
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemRealEstate() {
    ItemRealEstate(
        item = RealEstateList(
            id = 132,
            imageUrl = "https://cdn.dribbble.com/userupload/4259155/file/original-9c5357566528015850f4375ca16fee72.jpg?compress=1&resize=1024x768",
            title = "Nhà 3 ",
            square = 100.0f,
            price = 3_000_000.0,
            bedRooms = 3,
            floors = 3,
            address = "15 Lỗ giáng 19",
            views = 1234,
            isSaved = true,
            createdDate = "",
            status = 1
        ),
        onItemClick = {}
    )
}

@Preview
@Composable
private fun PreviewItemType() {
    var item = ItemChoose(2, "Test", -1)
    ItemType(item = item, onItemClick = {
        item = it.copy(isSelected = !it.isSelected)
    })
}
