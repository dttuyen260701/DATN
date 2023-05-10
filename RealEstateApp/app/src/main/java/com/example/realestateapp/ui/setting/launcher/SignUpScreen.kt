package com.example.realestateapp.ui.setting.launcher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
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
    val context = LocalContext.current
    val firstClick = remember {
        viewModel.firstClickButton
    }
    val name = remember { mutableStateOf("") }
    val nameError = remember {
        derivedStateOf {
            if (name.value.isEmpty() && !firstClick.value) context.getString(R.string.nameError)
            else ""
        }
    }
    val phone = remember { mutableStateOf("") }
    val phoneError = remember {
        derivedStateOf {
            if (phone.value.length != 10 && !firstClick.value) context.getString(R.string.phoneError)
            else ""
        }
    }
    val email = remember {
        viewModel.email
    }
    val emailError = remember {
        derivedStateOf {
            viewModel.validEmail(email.value)
        }
    }
    val password = remember {
        viewModel.password
    }
    val passwordError = remember {
        derivedStateOf {
            viewModel.validPassWord(password.value)
        }
    }
    val rePassword = remember { mutableStateOf("") }
    val rePasswordError = remember {
        derivedStateOf {
            if (password.value != rePassword.value && !firstClick.value && rePassword.value.isNotEmpty()) context.getString(
                R.string.rePasswordError
            )
            else ""
        }
    }
    val enableBtnSignUp = remember {
        derivedStateOf {
            emailError.value.isEmpty()
                    && nameError.value.isEmpty()
                    && phoneError.value.isEmpty()
                    && passwordError.value.isEmpty()
                    && rePasswordError.value.isEmpty()
        }
    }
    SignUpScreen(modifier = modifier,
        name = name.value,
        nameError = nameError.value,
        onNameChange = {
            name.value = it
        },
        phone = phone.value,
        phoneError = phoneError.value,
        onPhoneChange = {
            phone.value = it
        },
        email = email.value,
        onEmailChange = {
            email.value = it
        },
        emailError = emailError.value,
        password = password.value,
        onPassChange = {
            password.value = it
        },
        passwordError = passwordError.value,
        rePassword = rePassword.value,
        onRePassChange = {
            rePassword.value = it
        },
        rePasswordError = rePasswordError.value,
        enableBtnSignUp = enableBtnSignUp.value,
        onSignInClick = onSignInClick,
        onBtnSignUpClick = {
            firstClick.value = false
            viewModel.run {
                if (enableBtnSignUp.value) signUpUser(
                    name = name.value,
                    phone = phone.value
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
        })
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
        modifier = modifier, bgColor = RealStateAppTheme.colors.bgScrPrimaryLight
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
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.User),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
        )
        EditTextTrailingIconCustom(
            onTextChange = onPhoneChange,
            text = phone,
            label = stringResource(id = R.string.phoneTitle),
            typeInput = KeyboardType.Number,
            errorText = phoneError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.User),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
        )
        EditTextTrailingIconCustom(
            onTextChange = onEmailChange,
            text = email,
            label = stringResource(id = R.string.emailTitle),
            typeInput = KeyboardType.Email,
            errorText = emailError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.Email),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        EditTextTrailingIconCustom(
            onTextChange = onPassChange,
            text = password,
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            errorText = passwordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.Password),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        EditTextTrailingIconCustom(
            onTextChange = onRePassChange,
            text = rePassword,
            label = stringResource(id = R.string.reTypePasswordTitle),
            typeInput = KeyboardType.Password,
            errorText = rePasswordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealStateIcon.Password),
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnSignUpClick,
            title = stringResource(id = R.string.settingSignUpTitle),
            enabled = enableBtnSignUp,
            bgColor = RealStateAppTheme.colors.primary,
            modifier = Modifier
                .height(Constants.DefaultValue.TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(Constants.DefaultValue.MARGIN_VIEW)
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = RealStateAppTheme.colors.textSettingButton
                )
            ) {
                append(stringResource(id = R.string.haveAccount))
            }
            withStyle(
                style = SpanStyle(
                    color = RealStateAppTheme.colors.primary
                )
            ) {
                append(" ${stringResource(id = R.string.settingSignInTitle)}")
            }
        }, style = RealStateTypography.body1.copy(
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
