package com.example.realestateapp.ui.setting.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/8/2023.
 */

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    viewModel.run {
        val uiState by viewModel.uiEffect.collectAsStateWithLifecycle()
        var email by remember { email }
        var password by remember { password }
        var firstClick by remember { firstClick }
        val emailError by remember {
            derivedStateOf {
                validEmail(email)
            }
        }
        val passwordError by remember {
            derivedStateOf {
                ""
            }
        }
        val enableBtnSignIn = remember {
            derivedStateOf {
                emailError.isEmpty() && passwordError.isEmpty()
            }
        }

        when(uiState) {
            is LauncherUiEffect.SignInSuccess -> {
                onSignInSuccess()
            }
            else -> {
            }
        }

        SignInScreen(
            modifier = modifier,
            email = email,
            onEmailChange = {
                email = it
            },
            emailError = emailError,
            password = password,
            onPassChange = {
                password = it
            },
            enableBtnSignIn = enableBtnSignIn.value,
            onSignUpClick = onSignUpClick,
            onBtnSignInClick = remember {
                {
                    firstClick = false
                    if (enableBtnSignIn.value) signInUser()
                }
            },
            onBackClick = remember {
                onBackClick
            }
        )
    }
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String,
    password: String,
    onPassChange: (String) -> Unit,
    enableBtnSignIn: Boolean,
    onSignUpClick: () -> Unit,
    onBtnSignInClick: () -> Unit,
    onBackClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.settingSignInTitle),
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        },
    ) {
        Image(
            painter = painterResource(id = R.drawable.storyset_login),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )
        TextTitle(stringResource(id = R.string.settingSignInTitle))
        Spacing(MARGIN_DIFFERENT_VIEW)
        EditTextRadius(
            onTextChange = onEmailChange,
            text = email,
            label = stringResource(id = R.string.emailTitle),
            errorText = emailError,
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = RealEstateAppTheme.colors.bgTextField
        )
        Spacing(PADDING_VIEW)
        EditTextRadius(
            onTextChange = onPassChange,
            text = password,
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = RealEstateAppTheme.colors.bgTextField,
            isLastEditText = true
        )
        Text(
            text = stringResource(id = R.string.passwordForget),
            style = RealEstateTypography.body1.copy(
                color = RealEstateAppTheme.colors.primary,
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnSignInClick,
            title = stringResource(id = R.string.settingSignInTitle),
            enabled = enableBtnSignIn,
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(MARGIN_VIEW)
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = RealEstateAppTheme.colors.textSettingButton
                    )
                ) {
                    append(stringResource(id = R.string.doNotHaveAccount))
                }
                withStyle(
                    style = SpanStyle(
                        color = RealEstateAppTheme.colors.primary
                    )
                ) {
                    append(" ${stringResource(id = R.string.settingSignUpTitle)}")
                }
            },
            style = RealEstateTypography.body1.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onSignUpClick()
                }
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
