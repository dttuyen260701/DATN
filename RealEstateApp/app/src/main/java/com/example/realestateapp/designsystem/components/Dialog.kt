package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE

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
    isLoading: Boolean,
    title: String,
    filter: String,
    onFilterChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.3f))
            .fillMaxSize()
            .clickable { onDismissDialog() },
        verticalArrangement = Arrangement.Bottom
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
//                .heightIn(0.dp, LocalConfiguration.current.screenHeightDp.dp * 0.7f)
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
                .padding(top = MARGIN_VIEW.dp)
                .clickable { }
        ) {
            val (tvTitle, btnClose, edtSearch, lzItems) = createRefs()

            Text(
                text = title,
                style = RealEstateTypography.body1.copy(
                    color = RealEstateAppTheme.colors.primary
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
                        .size(Constants.DefaultValue.ICON_ITEM_SIZE.dp)
                        .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                        .constrainAs(btnClose) {
                            linkTo(top = tvTitle.top, bottom = tvTitle.bottom)
                            end.linkTo(parent.end, MARGIN_VIEW.dp)
                        },
                ) {
                    BaseIcon(
                        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                        modifier = Modifier
                            .size(Constants.DefaultValue.TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = RealEstateAppTheme.colors.primary
                    )
                }
            }
            EditTextFullIconBorderRadius(
                text = filter,
                onTextChange = onFilterChange,
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
                        top.linkTo(parent.top, MARGIN_DIFFERENT_VIEW.dp)
                        width = Dimension.fillToConstraints
                    }
            )

        }

        if (isLoading) {
            CircularProgressIndicator(
                color = RealEstateAppTheme.colors.progressBar
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
