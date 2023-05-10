package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_HINT_COLOR
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
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

@Composable
internal fun EditTextTrailingIconCustom(
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
    ConstraintLayout(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentHeight(),
    ) {
        val (
            trailingIcon,
            edt,
            tvError
        ) = createRefs()

    }
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
@Preview("EditTextTrailingIconCustom")
private fun PreviewEditTextWithTrailingIcon() {
    EditTextTrailingIconCustom (
        onTextChange = {},
        text = "TAs",
        errorText = "error1231231231231232313123123123123123123123123123213123",
        textColor = Color(5, 84, 89),
        backgroundColor = Color(240, 247, 218),
        typeInput = KeyboardType.Password
    )
}
