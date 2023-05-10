package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography

/**
 * Created by tuyen.dang on 5/10/2023.
 */
 
@Composable
internal fun TextTitle(
    title: String,
    textColor: Color = RealStateAppTheme.colors.primary
){
    Text(
        text = title,
        style = RealStateTypography.h1.copy(
            color = textColor,
            textAlign = TextAlign.Start
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}