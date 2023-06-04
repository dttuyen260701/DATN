package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW

/**
 * Created by tuyen.dang on 5/31/2023.
 */

@Composable
internal fun CheckBoxWIconText(
    modifier: Modifier = Modifier,
    icon: AppIcon,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseIcon(
            icon = icon,
            tint = RealEstateAppTheme.colors.primary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(PADDING_VIEW.dp))
        Text(
            text = title,
            style = RealEstateTypography.body1,
            color = RealEstateAppTheme.colors.primary
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = RealEstateAppTheme.colors.primary,
                uncheckedColor = RealEstateAppTheme.colors.primary,
                checkmarkColor = RealEstateAppTheme.colors.primaryVariant
            ),
        )
    }
}
 