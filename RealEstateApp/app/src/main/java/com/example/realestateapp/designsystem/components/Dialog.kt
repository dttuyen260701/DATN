package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.setVisibility
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun DialogMessage(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.errorDialogTitle),
    message: String,
    btnText: String = stringResource(id = R.string.dialogBackBtn),
    onDismissDialog: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ROUND_DIALOG.dp))
            .background(Color.White),
        onDismissRequest = onDismissDialog,
        title = {
            Text(
                text = title,
                style = RealEstateTypography.button.copy(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Text(
                text = message,
                style = RealEstateTypography.button.copy(
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Light
                )
            )
        },
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MARGIN_VIEW.dp, end = MARGIN_DIFFERENT_VIEW.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = btnText,
                    style = RealEstateTypography.button.copy(
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(MARGIN_VIEW.dp)
                        .clickable {
                            onDismissDialog()
                        },
                    textAlign = TextAlign.Center
                )
            }
        },
        properties = DialogProperties(),
        backgroundColor = Color.White
    )
}

@Composable
internal fun DialogConfirm(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    negativeBtnText: String,
    onBtnNegativeClick: () -> Unit,
    positiveBtnText: String,
    onBtnPositiveClick: () -> Unit,
    onDismissDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ROUND_RECTANGLE.dp))
            .background(Color.White),
        onDismissRequest = onDismissDialog,
        title = if (title != null) {
            {
                Text(
                    text = title,
                    style = RealEstateTypography.button.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else null,
        text = {
            Text(
                text = message,
                style = RealEstateTypography.button.copy(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = PADDING_HORIZONTAL_SCREEN.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonRadius(
                    onClick = {
                        onBtnNegativeClick()
                        onDismissDialog()
                    },
                    modifier = Modifier
                        .height(42.dp)
                        .weight(0.45f),
                    radius = ROUND_DIALOG,
                    title = negativeBtnText,
                    bgColor = RealEstateAppTheme.colors.primaryVariant,
                    textSize = 13,
                    textColor = RealEstateAppTheme.colors.primary
                )
                Spacer(modifier = Modifier.weight(0.1f))
                ButtonRadius(
                    onClick = {
                        onBtnPositiveClick()
                        onDismissDialog()
                    },
                    modifier = Modifier
                        .height(42.dp)
                        .weight(0.45f),
                    radius = ROUND_DIALOG,
                    title = positiveBtnText,
                    bgColor = RealEstateAppTheme.colors.primary,
                    textSize = 13,
                    textColor = RealEstateAppTheme.colors.primaryVariant
                )
            }
        },
        properties = DialogProperties(),
        backgroundColor = Color.White
    )
}

@Composable
internal fun DialogChoiceData(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit,
    title: String,
    onItemClick: (ItemChoose) -> Unit,
    loadData: (String, () -> Unit) -> Unit,
    isEnableSearchFromApi: Boolean = false,
    data: MutableList<ItemChoose>
) {
    var filter by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var dataSearch = data.filter { it.name.contains(filter, true) }
    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.3f))
            .fillMaxSize()
            .clickable { onDismissDialog() },
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(key1 = true) {
            loadData(filter) {
                isLoading = false
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .border(
                    BorderStroke(width = 1.dp, color = RealEstateAppTheme.colors.primary),
                    shape = RoundedCornerShape(
                        topStart = ROUND_RECTANGLE.dp,
                        topEnd = ROUND_RECTANGLE.dp
                    )
                )
                .background(
                    RealEstateAppTheme.colors.bgScreen,
                    shape = RoundedCornerShape(
                        topStart = ROUND_RECTANGLE.dp,
                        topEnd = ROUND_RECTANGLE.dp
                    )
                )
                .then(modifier)
                .padding(vertical = MARGIN_VIEW.dp)
                .clickable(enabled = false) { }
        ) {
            val (tvTitle, btnClose, edtSearch, lzItems, prgBar) = createRefs()

            Text(
                text = title,
                style = RealEstateTypography.body1.copy(
                    color = RealEstateAppTheme.colors.primary,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .constrainAs(tvTitle) {
                        top.linkTo(parent.top, MARGIN_VIEW.dp)
                        linkTo(start = parent.start, end = parent.end)
                    }
            )
            ButtonUnRepeating(onDismissDialog) {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .size(ICON_ITEM_SIZE.dp)
                        .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                        .constrainAs(btnClose) {
                            linkTo(top = tvTitle.top, bottom = tvTitle.bottom)
                            end.linkTo(parent.end, MARGIN_VIEW.dp)
                        },
                ) {
                    BaseIcon(
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                        modifier = Modifier
                            .size(TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = RealEstateAppTheme.colors.primary
                    )
                }
            }
            EditTextFullIconBorderRadius(
                text = filter,
                onTextChange = { textEdt ->
                    filter = textEdt
                    if (isEnableSearchFromApi) {
                        loadData(filter) {
                            isLoading = false
                        }
                    } else {
                        dataSearch = data.filter { it.name == filter }
                    }
                },
                hint = stringResource(id = R.string.hintSearchTitle, title),
                leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
                readOnly = false,
                borderColor = RealEstateAppTheme.colors.primary,
                leadingIconColor = RealEstateAppTheme.colors.primary,
                onLeadingIconClick = {},
                trailingIconColor = RealEstateAppTheme.colors.primary,
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(edtSearch) {
                        top.linkTo(tvTitle.bottom, MARGIN_DIFFERENT_VIEW.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            if (dataSearch.isNotEmpty() || isLoading) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = (PADDING_HORIZONTAL_SCREEN * 2).dp)
                        .constrainAs(lzItems) {
                            linkTo(
                                top = edtSearch.bottom,
                                topMargin = MARGIN_VIEW.dp,
                                bottom = parent.bottom,
                                bias = 0f
                            )
                        },
                    state = rememberLazyListState(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(
                        bottom = if (dataSearch.isNotEmpty()) PADDING_HORIZONTAL_SCREEN.dp else 0.dp
                    ),
                ) {
                    items(
                        items = dataSearch,
                        key = { keyData ->
                            keyData.toString()
                        },
                    ) { itemData ->
                        ItemChoiceDialog(
                            item = itemData,
                            onItemClick = onItemClick
                        )
                    }
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
                        .constrainAs(lzItems) {
                            top.linkTo(edtSearch.bottom, MARGIN_VIEW.dp)
                            width = Dimension.matchParent
                            visibility = setVisibility(filter.trim().isNotEmpty())
                        }
                )
            }

            if (isLoading) {
                CircularProgressIndicator(
                    color = RealEstateAppTheme.colors.progressBar,
                    modifier = Modifier
                        .constrainAs(prgBar) {
                            linkTo(
                                top = lzItems.bottom,
                                bottom = parent.bottom,
                                topMargin = MARGIN_VIEW.dp,
                                bottomMargin = MARGIN_DIFFERENT_VIEW.dp
                            )
                            linkTo(start = parent.start, end = parent.end)
                        }
                )
            }
        }
    }
}

@Composable
internal fun DialogShowImage(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit,
    data: MutableList<Image>,
    currentPosition: Int
) {
    var position by remember { mutableStateOf(currentPosition) }
    Box(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.5f))
            .fillMaxSize()
            .clickable { onDismissDialog() },
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(ROUND_RECTANGLE.dp)
                )
                .then(modifier)
                .clickable(enabled = false) { }
        ) {
            val (tvTitle, btnClose, slideImage) = createRefs()
            Text(
                text = stringResource(
                    id = R.string.numberImageTitle,
                    position + 1,
                    data.size
                ),
                style = RealEstateTypography.body1.copy(
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                    .constrainAs(tvTitle) {
                        top.linkTo(parent.top, MARGIN_DIFFERENT_VIEW.dp)
                        linkTo(parent.start, parent.end)
                    },
            )
            ButtonUnRepeating(onDismissDialog) {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .constrainAs(btnClose) {
                            top.linkTo(parent.top, PADDING_VIEW.dp)
                            end.linkTo(parent.end, PADDING_VIEW.dp)
                        }
                ) {
                    BaseIcon(
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                        modifier = Modifier
                            .size(TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            SlideShowImage(
                modifier = Modifier
                    .constrainAs(slideImage) {
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom
                        )
                        width = Dimension.matchParent
                        height = Dimension.wrapContent
                    },
                photos = data,
                isShowIndicator = false,
                currentPosition = position,
                onPositionChange = remember {
                    {
                        position = it
                    }
                }
            )
        }
    }
}

@Preview(name = "MessageDialog")
@Composable
private fun PreviewMessageDialog() {
    DialogMessage(message = "Lỗi cuộc đời")
}

@Preview(name = "ConfirmDialog")
@Composable
private fun PreviewDialogConfirm() {
    DialogConfirm(
        title = "Tesst",
        message = "a123",
        negativeBtnText = "Khoong",
        onBtnNegativeClick = {},
        positiveBtnText = "Co",
        onBtnPositiveClick = {},
        onDismissDialog = {}
    )
}
