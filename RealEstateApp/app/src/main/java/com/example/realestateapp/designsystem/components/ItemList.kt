package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography

/**
 * Created by tuyen.dang on 5/12/2023.
 */

@Composable
internal fun ItemType(
    modifier: Modifier = Modifier,
    item: ItemChoose,
    borderColor: Color = RealEstateAppTheme.colors.primary,
    textColor: Color = RealEstateAppTheme.colors.primary,
    textColorSelected: Color = Color.White,
    bgColor: Color = Color.Transparent,
    bgColorSelected: Color = RealEstateAppTheme.colors.primary,
    onItemClick: (ItemChoose) -> Unit,
) {
    Text(
        text = item.name,
        style = RealEstateTypography.body1.copy(
            color = if (item.isSelected) textColorSelected else textColor
        ),
        modifier = Modifier
            .wrapContentHeight()
            .border(
                BorderStroke(width = (0.5f).dp, color = borderColor),
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onItemClick(item)
            }
            .background(
                color = if (item.isSelected) bgColorSelected else bgColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .then(modifier)
    )
}

@Preview
@Composable
private fun PreviewItemType() {
    var item = ItemChoose("2", "Test")
    ItemType(
        item = item,
        onItemClick = {
            item = it.copy(isSelected = !it.isSelected)
        }
    )
}
