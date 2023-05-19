package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_ICON
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE

@Composable
internal fun ToolbarView(
    modifier: Modifier = Modifier,
    title: String = "",
    rightIcon: AppIcon? = null,
    onRightIconClick: () -> Unit = {},
    leftIcon: AppIcon? = null,
    onLeftIconClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(TOOLBAR_HEIGHT.dp)
            .fillMaxWidth()
            .background(
                color = RealEstateAppTheme.colors.primary
            )
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLeftIconClick,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, false)
                .padding(PADDING_VIEW.dp),
            enabled = (leftIcon != null)
        ) {
            leftIcon?.run {
                BaseIcon(
                    icon = this,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PADDING_ICON.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Text(
            text = title,
            style = RealEstateTypography.h2,
            color = Color.White
        )

        IconButton(
            onClick = onRightIconClick,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, false)
                .padding(PADDING_VIEW.dp),
            enabled = (rightIcon != null)
        ) {
            rightIcon?.run {
                BaseIcon(
                    icon = this,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PADDING_ICON.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
internal fun ToolbarWAnimation(
    modifier: Modifier = Modifier,
    toolbarHeight: Int = TOOLBAR_HEIGHT,
    bgColor: Color = Color.Transparent,
    title: String = "",
    textAlign: TextAlign = TextAlign.Start,
    titleColor: Color = Color.White,
    leadingIcon: AppIcon? = null,
    onLeadingIconClick: () -> Unit = {},
    trainingIcon: AppIcon? = null,
    onTrainingIconClick: () -> Unit = {},
    bgIcon: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .height(toolbarHeight.dp)
            .fillMaxWidth()
            .background(
                color = bgColor
            )
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonUnRepeating(onLeadingIconClick) {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .size(ICON_ITEM_SIZE.dp)
                    .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                    .background(
                        color = bgIcon
                    ),
                enabled = (leadingIcon != null)
            ) {
                leadingIcon?.run {
                    BaseIcon(
                        icon = this,
                        modifier = Modifier
                            .size(TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        Text(
            text = title,
            style = RealEstateTypography.h2.copy(
                color = titleColor,
                textAlign = textAlign
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = PADDING_VIEW.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        ButtonUnRepeating(onTrainingIconClick) {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .size(ICON_ITEM_SIZE.dp)
                    .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                    .background(
                        color = bgIcon
                    ),
                enabled = (trainingIcon != null)
            ) {
                trainingIcon?.run {
                    BaseIcon(
                        icon = this,
                        modifier = Modifier
                            .size(TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen() {
    ToolbarView(
        modifier = Modifier.height(56.dp),
        title = "Test",
        leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow)
    )
}