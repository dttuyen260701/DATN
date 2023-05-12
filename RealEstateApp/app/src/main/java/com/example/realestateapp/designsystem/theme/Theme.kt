package com.example.realestateapp.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Singleton

private val DarkColorRealState = RealEstateColors(
    primary = MidnightGreen,
    primaryVariant = AntiFlashWhite,
    secondary = GhostWhite,
    selectedBottomNavigate = Malachite,
    textSettingButton = SonicSilver,
    bgSettingButton = Color.White,
    bgScreen = AthensGrayApprox,
    progressBar = KellyGreen,
    bgTextField = Beige,
    bgBtnDisable = SonicSilver,
    bgScrPrimaryLight = Snow
)

private val LightColorRealState = RealEstateColors(
    primary = MidnightGreen,
    primaryVariant = AntiFlashWhite,
    secondary = GhostWhite,
    selectedBottomNavigate = Malachite,
    textSettingButton = SonicSilver,
    bgSettingButton = Color.White,
    bgScreen = AthensGrayApprox,
    progressBar = KellyGreen,
    bgTextField = Beige,
    bgBtnDisable = SonicSilver,
    bgScrPrimaryLight = Snow

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Singleton
object RealEstateAppTheme {
    val colors: RealEstateColors
        @Composable
        get() = LocalRealEstateColors.current
}

@Composable
fun RealEstateAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorRealState
    } else {
        LightColorRealState
    }
    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = colors.primary
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = colors.primary
        )
    }
    ProvideRealEstateColors(colors = colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = RealEstateTypography,
            content = content
        )
    }
}


@Stable
class RealEstateColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    selectedBottomNavigate: Color,
    textSettingButton: Color,
    bgSettingButton: Color,
    bgScreen: Color,
    progressBar: Color,
    bgTextField: Color,
    bgBtnDisable: Color,
    bgScrPrimaryLight: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var selectedBottomNavigate by mutableStateOf(selectedBottomNavigate)
        private set
    var textSettingButton by mutableStateOf(textSettingButton)
        private set
    var bgSettingButton by mutableStateOf(bgSettingButton)
        private set
    var bgScreen by mutableStateOf(bgScreen)
        private set
    var progressBar by mutableStateOf(progressBar)
        private set
    var bgTextField by mutableStateOf(bgTextField)
        private set
    var bgBtnDisable by mutableStateOf(bgBtnDisable)
        private set
    var bgScrPrimaryLight by mutableStateOf(bgScrPrimaryLight)
        private set

    fun update(other: RealEstateColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        selectedBottomNavigate = other.selectedBottomNavigate
        textSettingButton = other.textSettingButton
        bgSettingButton = other.bgSettingButton
        bgScreen = other.bgScreen
        progressBar = other.progressBar
        bgTextField = other.bgTextField
        bgBtnDisable = other.bgBtnDisable
        bgScrPrimaryLight = other.bgScrPrimaryLight
    }

    fun copy(): RealEstateColors = RealEstateColors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        selectedBottomNavigate = selectedBottomNavigate,
        textSettingButton = textSettingButton,
        bgSettingButton = bgSettingButton,
        bgScreen = bgScreen,
        progressBar = progressBar,
        bgTextField = bgTextField,
        bgBtnDisable = bgBtnDisable,
        bgScrPrimaryLight = bgScrPrimaryLight
    )
}

@Composable
fun ProvideRealEstateColors(
    colors: RealEstateColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalRealEstateColors provides colorPalette, content = content)
}

private val LocalRealEstateColors = staticCompositionLocalOf<RealEstateColors> {
    error("No LocalPostColors provided")
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)