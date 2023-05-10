package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_ICON
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

@Composable
fun ToolbarView(
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
                color = RealStateAppTheme.colors.primary
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
            style = RealStateTypography.h2,
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
@Preview()
fun PreviewLoginScreen() {
    ToolbarView(
        modifier = Modifier.height(56.dp),
        title = "Test",
        leftIcon = AppIcon.DrawableResourceIcon(RealStateIcon.BackArrow)
    )
}