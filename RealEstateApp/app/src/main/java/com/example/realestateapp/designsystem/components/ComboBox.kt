package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_HINT_COLOR
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.SELECT_BOX_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE

/**
 * Created by tuyen.dang on 5/22/2023.
 */

@Composable
internal fun ComboBox(
    modifier: Modifier = Modifier,
    cbbHeight: Int = SELECT_BOX_HEIGHT,
    onItemClick: () -> Unit,
    title: String,
    value: String = "",
    textSize: Int = 14,
    textColor: Color = RealEstateAppTheme.colors.primary,
    hint: String = "",
    borderColor: Color = RealEstateAppTheme.colors.primary,
    isAllowClearData: Boolean = true,
    onClearData: () -> Unit = {},
    leadingIcon: AppIcon? = null,
    errorText: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
    ) {
        Text(
            text = title,
            style = RealEstateTypography.body1.copy(
                fontSize = (textSize - 1).sp,
                color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                textAlign = TextAlign.Start
            ),
        )
        Spacing(PADDING_VIEW)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(cbbHeight.dp)
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
                            .size(TRAILING_ICON_SIZE.dp),
                        tint = textColor
                    )
                }
            }
            Text(
                text = if (value.trim().isNotEmpty()) value else hint,
                style = RealEstateTypography.body1.copy(
                    fontSize = textSize.sp,
                    color = textColor.copy(if (value.trim().isNotEmpty()) 1f else ALPHA_HINT_COLOR)
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
                        .size(TRAILING_ICON_SIZE.dp),
                    tint = textColor
                )
            }
        }
        if(!errorText.isNullOrEmpty()) {
            Spacing(PADDING_VIEW)
            Text(
                text = errorText,
                style = RealEstateTypography.caption.copy(
                    color = Color.Red,
                    fontSize = Constants.DefaultValue.WARNING_TEXT_SIZE.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = MARGIN_VIEW.dp)
            )
        }
    }
}
