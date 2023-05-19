package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG

/**
 * Created by tuyen.dang on 5/10/2023.
 */

@Composable
internal fun TextTitle(
    title: String,
    textColor: Color = RealEstateAppTheme.colors.primary
) {
    Text(
        text = title,
        style = RealEstateTypography.h1.copy(
            color = textColor,
            textAlign = TextAlign.Start
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
internal fun TextIcon(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    size: Int = 12,
    icon: AppIcon,
    iconTint: Color = Color.Black,
    isIconFirst: Boolean = true
) {
    LazyRow(
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        reverseLayout = !isIconFirst
    ) {
        item {
            BaseIcon(
                icon = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(size.dp),
                tint = iconTint
            )
        }
        item {
            Text(
                text = text,
                style = RealEstateTypography.body1.copy(
                    color = textColor,
                    fontSize = size.sp
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(
                        start = (if (isIconFirst) PADDING_VIEW else 0).dp,
                        end = (if (isIconFirst) 0 else PADDING_VIEW).dp
                    )
            )
        }
    }
}

@Composable
internal fun TextIconVertical(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    size: Int = 14,
    icon: AppIcon,
    iconTint: Color = Color.White,
    bgIconTint: Color = Color.Black.copy(0.5f)
) {
    Column(
        modifier = Modifier
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseIcon(
            icon = icon,
            modifier = Modifier
                .size((size + ICON_ITEM_SIZE).dp)
                .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                .background(
                    color = bgIconTint
                )
                .border(
                    border = BorderStroke(
                        width = 0.2f.dp,
                        color = textColor
                    ),
                    shape = RoundedCornerShape(ROUND_DIALOG.dp)
                )
                .padding(MARGIN_VIEW.dp),
            contentDescription = null,
            tint = iconTint
        )
        Spacer(modifier = Modifier.height(PADDING_VIEW.dp))
        Text(
            text = text,
            style = RealEstateTypography.body1.copy(
                color = textColor,
                fontSize = size.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            modifier = Modifier
                .wrapContentSize()
                .padding()
        )
    }
}

@Preview
@Composable
fun PreviewTextIcon() {
    TextIcon(
        text = "Text",
        size = 14,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.PostSaved),
    )
}

@Preview
@Composable
fun PreviewTextIconVertical() {
    TextIconVertical(
        text = "212334",
        size = 14,
        icon = AppIcon.DrawableResourceIcon(RealEstateIcon.PostSavedOutline),
        iconTint = Color.Green,
        bgIconTint = Color.White,
        textColor = Color.White
    )
}
