package com.example.realestateapp.ui.setting.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.BaseScreen
import com.example.realestateapp.designsystem.components.EditTextRadius
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_TEXT_FIELD
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/8/2023.
 */

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    val email = remember {
        viewModel.email
    }
    val password = remember {
        viewModel.password
    }
    val emailError = remember {
        derivedStateOf {
            viewModel.validEmail(email.value)
        }
    }
    val passwordError = remember {
        derivedStateOf {
            viewModel.validPassWord(password.value)
        }
    }
    SignInScreen(
        modifier = modifier,
        email = email.value,
        onEmailChange = {
            email.value = it
        },
        emailError = emailError.value,
        password = password.value,
        onPassChange = {
            password.value = it
        },
        passwordError = passwordError.value
    )
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String,
    password: String,
    onPassChange: (String) -> Unit,
    passwordError: String,
) {
    BaseScreen(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.storyset_login),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = stringResource(id = R.string.settingSignInTitle),
            style = RealStateTypography.h1.copy(
                color = RealStateAppTheme.colors.primary
            )
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
        EditTextRadius(
            onTextChange = onEmailChange,
            text = email,
            label = stringResource(id = R.string.emailTitle),
            errorText = emailError,
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = RealStateAppTheme.colors.bgTextField,
            modifier = Modifier.height(TOOLBAR_HEIGHT.dp)
        )
        Spacing(MARGIN_TEXT_FIELD)
        EditTextRadius(
            onTextChange = onPassChange,
            text = password,
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            errorText = passwordError,
            textColor = RealStateAppTheme.colors.primary,
            backgroundColor = RealStateAppTheme.colors.bgTextField,
            modifier = Modifier.height(TOOLBAR_HEIGHT.dp),
            isLastEditText = true
        )
    }

}
