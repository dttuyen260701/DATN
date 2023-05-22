package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants

/**
 * Created by tuyen.dang on 5/22/2023.
 */

@Composable
internal fun ComboBox(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    value: String = "",
    textColor: Color = RealEstateAppTheme.colors.primary,
    hint: String = "",
    borderColor: Color = RealEstateAppTheme.colors.primary,
    isAllowClearData: Boolean = true,
    onClearData: () -> Unit = {},
    leadingIcon: AppIcon? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
            .border(
                BorderStroke(width = 1.dp, color = borderColor),
                shape = RoundedCornerShape(Constants.DefaultValue.ROUND_DIALOG.dp)
            )
            .background(Color.Transparent)
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            IconButton(
                onClick = onItemClick,
            ) {
                BaseIcon(
                    icon = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    tint = textColor
                )
            }
        }
        Text(
            text = if (value.trim().isNotEmpty()) value else hint,
            style = RealEstateTypography.body1.copy(
                color = textColor.copy(if (value.trim().isNotEmpty()) 1f else 0.8f)
            ),
            modifier = Modifier
                .weight(1f)
        )
        IconButton(
            onClick = if (isAllowClearData && value.trim().isNotEmpty())
                onClearData
            else onItemClick,
        ) {
            BaseIcon(
                icon =
                if (isAllowClearData && value.trim().isNotEmpty())
                    AppIcon.DrawableResourceIcon(RealEstateIcon.Clear)
                else
                    AppIcon.DrawableResourceIcon(RealEstateIcon.DropDown),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = textColor
            )
        }
    }
}
