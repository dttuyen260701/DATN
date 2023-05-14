package com.example.realestateapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RealEstateAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Transparent
                ) {

                    val customTextSelectionColors = TextSelectionColors(
                        handleColor = RealEstateAppTheme.colors.primary,
                        backgroundColor = RealEstateAppTheme.colors.primary.copy(alpha = 0.4f)
                    )

                    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                        RealEstateApp(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
