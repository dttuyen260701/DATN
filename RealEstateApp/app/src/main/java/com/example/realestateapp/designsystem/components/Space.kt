package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Spacing(height: Int = 0) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(Color.Transparent)
    )
}

@Composable
fun BorderLine(
    modifier: Modifier = Modifier,
    height: Float = 0f
) {
    Spacer(
        modifier = Modifier
            .height(height.dp)
            .then(modifier)
            .fillMaxWidth()
            .background(Color.Gray)
    )
}