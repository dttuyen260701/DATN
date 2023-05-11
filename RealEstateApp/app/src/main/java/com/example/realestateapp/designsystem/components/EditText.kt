package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_HINT_COLOR
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_PADDING
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.WARNING_TEXT_SIZE

/**
 * Created by tuyen.dang on 5/8/2023.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EditTextRadius(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String = "",
    onTextChange: (String) -> Unit,
    typeInput: KeyboardType = KeyboardType.Text,
    hint: String = "",
    errorText: String = "",
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    isLastEditText: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isShowPassword by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent),
            textStyle = RealStateTypography.body1,
            label = if (label != null) {
                { Text(text = label) }
            } else null,
            onValueChange = { onTextChange(it) },
            value = text,
            placeholder = {
                Text(
                    text = hint,
                    color = textColor.copy(alpha = ALPHA_HINT_COLOR)
                )
            },
            trailingIcon = if (typeInput == KeyboardType.Password) {
                {
                    IconButton(
                        onClick = {
                            isShowPassword = !isShowPassword
                        },
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(if (isShowPassword) RealStateIcon.VisibilityOff else RealStateIcon.Visibility),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(TRAILING_ICON_PADDING.dp)
                                .size(TRAILING_ICON_SIZE.dp)
                        )
                    }
                }
            } else null,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                backgroundColor = backgroundColor,
                cursorColor = textColor,
                trailingIconColor = textColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = textColor,
                unfocusedLabelColor = Color.Gray,
                errorCursorColor = Color.Red,
                placeholderColor = textColor
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = if (isLastEditText) ImeAction.Done else ImeAction.Next,
                keyboardType = typeInput,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
            shape = RoundedCornerShape(ROUND_RECTANGLE.dp),
            visualTransformation =
            if (typeInput != KeyboardType.Password || isShowPassword) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        Text(
            text = errorText,
            style = RealStateTypography.caption.copy(
                color = Color.Red,
                fontSize = WARNING_TEXT_SIZE.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = MARGIN_VIEW.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EditTextTrailingIconCustom(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String = "",
    onTextChange: (String) -> Unit,
    typeInput: KeyboardType = KeyboardType.Text,
    hint: String = "",
    errorText: String = "",
    trailingIcon: AppIcon,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    isLastEditText: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isShowPassword by remember {
        mutableStateOf(false)
    }

    ConstraintLayout(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentHeight()
            .fillMaxWidth(),
    ) {
        val (
            icTrailing,
            edt,
            tvError
        ) = createRefs()

        TextField(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.Transparent)
                .constrainAs(edt) {
                    top.linkTo(parent.top)
                    start.linkTo(icTrailing.end, MARGIN_VIEW.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            textStyle = RealStateTypography.body1,
            label = if (label != null) {
                { Text(text = label) }
            } else null,
            onValueChange = { onTextChange(it) },
            value = text,
            placeholder = {
                Text(
                    text = hint,
                    color = textColor.copy(alpha = ALPHA_HINT_COLOR)
                )
            },
            trailingIcon = if (typeInput == KeyboardType.Password) {
                {
                    IconButton(
                        onClick = {
                            isShowPassword = !isShowPassword
                        },
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(if (isShowPassword) RealStateIcon.VisibilityOff else RealStateIcon.Visibility),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(TRAILING_ICON_PADDING.dp)
                                .size(TRAILING_ICON_SIZE.dp)
                        )
                    }
                }
            } else null,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                backgroundColor = backgroundColor,
                cursorColor = textColor,
                trailingIconColor = textColor,
                unfocusedIndicatorColor = Color.Gray,
                focusedIndicatorColor = textColor,
                focusedLabelColor = textColor,
                unfocusedLabelColor = Color.Gray,
                errorCursorColor = Color.Red,
                placeholderColor = textColor
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = if (isLastEditText) ImeAction.Done else ImeAction.Next,
                keyboardType = typeInput,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
            ),
            shape = RectangleShape,
            visualTransformation =
            if (typeInput != KeyboardType.Password || isShowPassword) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        BaseIcon(
            icon = trailingIcon,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .background(Color.Transparent)
                .constrainAs(icTrailing) {
                    start.linkTo(parent.start)
                    top.linkTo(edt.top)
                    bottom.linkTo(edt.bottom)
                },
            tint = textColor
        )
        Text(
            text = errorText,
            style = RealStateTypography.caption.copy(
                color = Color.Red,
                fontSize = WARNING_TEXT_SIZE.sp
            ),
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(tvError) {
                    start.linkTo(edt.start)
                    end.linkTo(edt.end)
                    top.linkTo(edt.bottom)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EditTextFullIconBorderRadius(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = RealStateAppTheme.colors.primary,
    onTextChange: (String) -> Unit,
    typeInput: KeyboardType = KeyboardType.Text,
    hint: String = "",
    borderColor: Color = RealStateAppTheme.colors.bgBtnDisable,
    leadingIcon: AppIcon? = null,
    leadingIconColor: Color,
    onLeadingIconClick: () -> Unit,
    trailingIcon: AppIcon? = null,
    trailingIconColor: Color,
    onTrailingIconClick: () -> Unit,
    onDoneAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                BorderStroke(width = 1.dp, color = borderColor),
                shape = RoundedCornerShape(ROUND_DIALOG.dp)
            )
            .background(Color.Transparent)
            .then(modifier),
        textStyle = RealStateTypography.body1,
        onValueChange = { onTextChange(it) },
        value = text,
        placeholder = {
            Text(
                text = hint,
                color = textColor.copy(alpha = ALPHA_HINT_COLOR)
            )
        },
        leadingIcon = if (leadingIcon != null) {
            {
                IconButton(
                    onClick = onLeadingIconClick,
                ) {
                    BaseIcon(
                        icon = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            {
                IconButton(
                    onClick = onTrailingIconClick,
                ) {
                    BaseIcon(
                        icon = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        } else null,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            backgroundColor = Color.Transparent,
            cursorColor = textColor,
            leadingIconColor = leadingIconColor,
            trailingIconColor = trailingIconColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorCursorColor = Color.Red
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = typeInput,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneAction()
                keyboardController?.hide()
            },
        )
    )
}

@Composable
@Preview("default")
private fun PreviewEditText() {
    EditTextRadius(
        onTextChange = {},
        text = "TAs",
        errorText = "error1231231231231232313123123123123123123123123123213123",
        textColor = Color(5, 84, 89),
        backgroundColor = Color(240, 247, 218),
        typeInput = KeyboardType.Password
    )
}

@Composable
@Preview("EditTextTrailingIconCustom", showSystemUi = false)
private fun PreviewEditTextWithTrailingIcon() {
    EditTextTrailingIconCustom(
        onTextChange = {},
        text = "TAs",
        errorText = "error1231231231231232313123123123123123123123123123213123",
        textColor = Color(5, 84, 89),
        backgroundColor = Color(240, 247, 218),
        typeInput = KeyboardType.Password,
        trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.Visibility)
    )
}
