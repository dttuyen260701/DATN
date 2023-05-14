package com.example.realestateapp.ui.setting.launcher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ButtonRadius
import com.example.realestateapp.designsystem.components.EditTextTrailingIconCustom
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.TextTitle
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SignUpRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    viewModel.run {
        val context = LocalContext.current
        var firstClick by remember { firstClick }
        var name by remember { mutableStateOf("") }
        val nameError by remember {
            derivedStateOf {
                if (name.isEmpty() && !firstClick) context.getString(R.string.nameError)
                else ""
            }
        }
        var phone by remember { mutableStateOf("") }
        val phoneError by remember {
            derivedStateOf {
                if (phone.length != 10 && !firstClick) context.getString(R.string.phoneError)
                else ""
            }
        }
        var email by remember { email }
        val emailError by remember {
            derivedStateOf {
                validEmail(email)
            }
        }
        var password by remember { password }
        val passwordError by remember {
            derivedStateOf {
                validPassWord(password)
            }
        }
        var rePassword by remember { mutableStateOf("") }
        val rePasswordError by remember {
            derivedStateOf {
                if (password != rePassword && !firstClick && rePassword.isNotEmpty()) context.getString(
                    R.string.rePasswordError
                )
                else ""
            }
        }
        val enableBtnSignUp by remember {
            derivedStateOf {
                emailError.isEmpty()
                        && nameError.isEmpty()
                        && phoneError.isEmpty()
                        && passwordError.isEmpty()
                        && rePasswordError.isEmpty()
            }
        }

        SignUpScreen(
            modifier = modifier,
            name = name,
            nameError = nameError,
            onNameChange = {
                name = it
            },
            phone = phone,
            phoneError = phoneError,
            onPhoneChange = {
                phone = it
            },
            email = email,
            onEmailChange = {
                email = it
            },
            emailError = emailError,
            password = password,
            onPassChange = {
                password = it
            },
            passwordError = passwordError,
            rePassword = rePassword,
            onRePassChange = {
                rePassword = it
            },
            rePasswordError = rePasswordError,
            enableBtnSignUp = enableBtnSignUp,
            onSignInClick = onSignInClick,
            onBtnSignUpClick = remember {
                {
                    firstClick = false
                    if (enableBtnSignUp) signUpUser(
                        name = name,
                        phone = phone
                    ) {
                        showDialog(
                            dialog = TypeDialog.ConfirmDialog(
                                message = context.getString(R.string.signUpSuccess),
                                negativeBtnText = context.getString(R.string.dialogBackBtn),
                                onBtnNegativeClick = {},
                                positiveBtnText = context.getString(R.string.dialogYesBtn),
                                onBtnPositiveClick = {
                                    onSignUpSuccess()
                                }
                            )
                        )
                    }
                }
            }
        )
    }
}

@Composable
internal fun SignUpScreen(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String,
    phone: String,
    onPhoneChange: (String) -> Unit,
    phoneError: String,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String,
    password: String,
    onPassChange: (String) -> Unit,
    passwordError: String,
    rePassword: String,
    onRePassChange: (String) -> Unit,
    rePasswordError: String,
    enableBtnSignUp: Boolean,
    onSignInClick: () -> Unit,
    onBtnSignUpClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier, bgColor = RealEstateAppTheme.colors.bgScrPrimaryLight
    ) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        TextTitle(stringResource(id = R.string.settingSignUpTitle))
        Spacer(modifier = Modifier.weight(1f))
        EditTextTrailingIconCustom(
            onTextChange = onNameChange,
            text = name,
            label = stringResource(id = R.string.nameTitle),
            typeInput = KeyboardType.Text,
            errorText = nameError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
        )
        EditTextTrailingIconCustom(
            onTextChange = onPhoneChange,
            text = phone,
            label = stringResource(id = R.string.phoneTitle),
            typeInput = KeyboardType.Number,
            errorText = phoneError,
            trailingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Phone),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
        )
        EditTextTrailingIconCustom(
            onTextChange = onEmailChange,
            text = email,
            label = stringResource(id = R.string.emailTitle),
            typeInput = KeyboardType.Email,
            errorText = emailError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Email),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White
        )
        EditTextTrailingIconCustom(
            onTextChange = onPassChange,
            text = password,
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            errorText = passwordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Password),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White
        )
        EditTextTrailingIconCustom(
            onTextChange = onRePassChange,
            text = rePassword,
            label = stringResource(id = R.string.reTypePasswordTitle),
            typeInput = KeyboardType.Password,
            errorText = rePasswordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Password),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnSignUpClick,
            title = stringResource(id = R.string.settingSignUpTitle),
            enabled = enableBtnSignUp,
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(Constants.DefaultValue.TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(Constants.DefaultValue.MARGIN_VIEW)
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = RealEstateAppTheme.colors.textSettingButton
                )
            ) {
                append(stringResource(id = R.string.haveAccount))
            }
            withStyle(
                style = SpanStyle(
                    color = RealEstateAppTheme.colors.primary
                )
            ) {
                append(" ${stringResource(id = R.string.settingSignInTitle)}")
            }
        }, style = RealEstateTypography.body1.copy(
            textAlign = TextAlign.Center
        ), modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onSignInClick()
            })
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
