package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.CLICK_BUTTON_TIME
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_ICON
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_TEXT
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_CIRCLE
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.SELECT_BOX_HEIGHT

/**
 * Created by tuyen.dang on 5/6/2023.
 */

@Composable
internal fun SettingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String,
    leadingIcon: AppIcon,
    trailingIcon: AppIcon? = AppIcon.DrawableResourceIcon(RealEstateIcon.NextArrow),
    backgroundIcon: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = RealEstateAppTheme.colors.bgSettingButton
        ),
        shape = RoundedCornerShape(ROUND_CIRCLE.dp),
        contentPadding = PaddingValues(PADDING_VIEW.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = RealEstateAppTheme.colors.bgSettingButton),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseIcon(
                icon = leadingIcon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(color = backgroundIcon, shape = CircleShape)
                    .padding(PADDING_TEXT.dp),
                tint = Color.White
            )
            Text(
                text = title,
                style = RealEstateTypography.button.copy(
                    fontSize = 16.sp,
                    color = RealEstateAppTheme.colors.textSettingButton,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(horizontal = PADDING_TEXT.dp)
            )
            trailingIcon?.run {
                BaseIcon(
                    icon = this,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .padding(PADDING_ICON.dp),
                    tint = RealEstateAppTheme.colors.textSettingButton
                )
            }
        }
    }
}

@Composable
internal fun ButtonRadius(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    radius: Int = ROUND_CIRCLE,
    title: String,
    textSize: Int = 14,
    bgColor: Color,
    textColor: Color = Color.White,
    bgDisableColor: Color = RealEstateAppTheme.colors.bgBtnDisable
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier
            .then(modifier),
        shape = RoundedCornerShape(radius.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = bgColor,
            disabledBackgroundColor = bgDisableColor
        ),
        elevation = null
    ) {
        Text(
            text = title,
            style = RealEstateTypography.button.copy(
                color = textColor,
                fontSize = textSize.sp
            )
        )
    }
}

@Composable
internal fun ButtonRadiusGradient(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    radius: Int = ROUND_CIRCLE,
    title: String,
    textSize: Int = 14,
    bgColor: List<Color>,
    textColor: Color = Color.White,
    bgDisableColor: Color = RealEstateAppTheme.colors.bgBtnDisable
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = bgDisableColor
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(radius.dp))
            .then(modifier)
            .background(
                Brush.horizontalGradient(
                    bgColor
                ),
            ),
        elevation = null
    ) {
        Text(
            text = title,
            style = RealEstateTypography.button.copy(
                color = textColor,
                fontSize = textSize.sp
            )
        )
    }
}

@Composable
internal fun ButtonUnRepeating(
    onClick: () -> Unit,
    timeBlock: Long = CLICK_BUTTON_TIME,
    content: @Composable (() -> Unit) -> Unit
) {
    var latest by remember {
        mutableLongStateOf(0L)
    }
    content {
        val now = System.currentTimeMillis()
        if (now - latest >= timeBlock) {
            latest = now
            onClick()
        }
    }
}

@Composable
internal fun SwitchButton(
    modifier: Modifier = Modifier,
    data: MutableList<ItemChoose>,
    onItemClick: (ItemChoose) -> Unit,
    bgColor: Color = RealEstateAppTheme.colors.bgTextField,
    bgColorSelected: Color = RealEstateAppTheme.colors.primary,
    cbbHeight: Int = SELECT_BOX_HEIGHT,
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(ROUND_DIALOG.dp),
            )
            .padding(
                horizontal = PADDING_HORIZONTAL_SCREEN.dp,
                vertical = MARGIN_VIEW.dp
            )
            .height(cbbHeight.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in data.indices) {
            TextButton(
                onClick = {
                    val oldIndex = data.indexOfFirst { it.isSelected }
                    if (oldIndex != i) {
                        if (oldIndex != -1)
                            data[oldIndex] =
                                data[oldIndex].copy(isSelected = false)
                        data[i] = data[i].copy(isSelected = true)
                    }
                    onItemClick(data[i])
                },
                modifier = Modifier
                    .background(
                        color = if (data[i].isSelected) bgColorSelected else bgColor,
                        shape = RoundedCornerShape(
                            topStart = (if (i == 0) ROUND_DIALOG else 0).dp,
                            bottomStart = (if (i == 0) ROUND_DIALOG else 0).dp,
                            topEnd = (if (i == data.lastIndex) ROUND_DIALOG else 0).dp,
                            bottomEnd = (if (i == data.lastIndex) ROUND_DIALOG else 0).dp,
                        )
                    )
                    .border(
                        BorderStroke(
                            width = (0.5f).dp,
                            color = bgColorSelected
                        ), shape = RoundedCornerShape(
                            topStart = (if (i == 0) ROUND_DIALOG else 0).dp,
                            bottomStart = (if (i == 0) ROUND_DIALOG else 0).dp,
                            topEnd = (if (i == data.lastIndex) ROUND_DIALOG else 0).dp,
                            bottomEnd = (if (i == data.lastIndex) ROUND_DIALOG else 0).dp,
                        )

                    )
                    .height(cbbHeight.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent
                ),
                elevation = null
            ) {
                Text(
                    text = data[i].name,
                    style = RealEstateTypography.body1.copy(
                        color = if (data[i].isSelected) bgColor else bgColorSelected
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_VIEW.dp)
                )
            }
        }
    }
}

@Preview("default", "rectangle")
@Composable
private fun SettingButtonPreview() {
    SettingButton(
        onClick = {},
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        title = "User",
        leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Lock),
        backgroundIcon = RealEstateAppTheme.colors.primary
    )
}

@Preview("buttonRadius", "rectangle")
@Composable
private fun PreviewButtonRadius() {
    ButtonRadius(
        onClick = {},
        modifier = Modifier
            .height(56.dp)
            .width(30.dp),
        radius = ROUND_RECTANGLE,
        title = "Cafi ddajwt",
        bgColor = RealEstateAppTheme.colors.primary,
        textColor = RealEstateAppTheme.colors.textSettingButton
    )
}
