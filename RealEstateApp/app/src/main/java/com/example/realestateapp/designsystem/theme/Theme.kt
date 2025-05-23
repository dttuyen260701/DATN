package com.example.realestateapp.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Singleton

private val darkColorRealState = RealEstateColors(
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
    bgScrPrimaryLight = Snow,
    bgIconsBlack50 = Black50,
    bgButtonGradient = listOf(KellyGreen, MidnightGreen)
)

private val lightColorRealState = RealEstateColors(
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
    bgScrPrimaryLight = Snow,
    bgIconsBlack50 = Black50,
    bgButtonGradient = listOf(KellyGreen, MidnightGreen)

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
        get() = localRealEstateColors.current
}

@Composable
fun RealEstateAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorRealState
    } else {
        lightColorRealState
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
    bgScrPrimaryLight: Color,
    bgIconsBlack50: Color,
    bgButtonGradient: List<Color>,
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    private var secondary by mutableStateOf(secondary)
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
    var bgIconsBlack50 by mutableStateOf(bgIconsBlack50)
        private set
    var bgButtonGradient by mutableStateOf(bgButtonGradient)
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
        bgIconsBlack50 = other.bgIconsBlack50
        bgButtonGradient = other.bgButtonGradient
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
        bgScrPrimaryLight = bgScrPrimaryLight,
        bgIconsBlack50 = bgIconsBlack50,
        bgButtonGradient = bgButtonGradient
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
    CompositionLocalProvider(localRealEstateColors provides colorPalette, content = content)
}

private val localRealEstateColors = staticCompositionLocalOf {
    lightColorRealState
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