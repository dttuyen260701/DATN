package com.example.realestateapp.ui.setting.changepass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/11/2023.
 */

@Composable
internal fun ChangePassRoute(
    modifier: Modifier = Modifier,
    viewModel: ChangePassViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    viewModel.run {
        val uiState by remember { uiState }
        var firstClick by remember { firstClick }
        var oldPass by remember { oldPass }
        val oldPassWordError by remember {
            derivedStateOf {
                validPassWord(oldPass)
            }
        }
        var newPass by remember { newPass }
        val passWordError by remember {
            derivedStateOf {
                validPassWord(newPass)
            }
        }
        var newPassRepeat by remember { newPassRepeat }
        val newPassRepeatError by remember {
            derivedStateOf {
                if (newPassRepeat != newPass && !firstClick && newPassRepeat.isNotEmpty()) context.getString(
                    R.string.rePasswordError
                )
                else ""
            }
        }
        val enableSubmit by remember {
            derivedStateOf {
                passWordError.isEmpty()
                        && newPassRepeatError.isEmpty()
                        && oldPassWordError.isEmpty()
            }
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is ChangePassUiState.ChangePassSuccess -> {
                    if ((uiState as ChangePassUiState.ChangePassSuccess).data) {
                        context.run { makeToast(getString(R.string.changePassSuccessTitle)) }
                        onBackClick()
                    } else {
                        showDialog(
                            dialog = TypeDialog.ErrorDialog(
                                message = Constants.MessageErrorAPI.INTERNAL_SERVER_ERROR
                            )
                        )
                    }
                }
                else -> {}
            }
        }

        ChangePassScreen(
            modifier = modifier,
            oldPassword = oldPass,
            onOldPassChange = remember {
                {
                    oldPass = it
                }
            },
            oldPasswordError = oldPassWordError,
            newPassword = newPass,
            onNewPassChange = remember {
                {
                    newPass = it
                }
            },
            newPasswordError = passWordError,
            newPasswordRepeat = newPassRepeat,
            onNewPassRepeatChange = remember {
                {
                    newPassRepeat = it
                }
            },
            newPasswordRepeatError = newPassRepeatError,
            enableBtnSubmit = enableSubmit,
            onBtnSubmitClick = remember {
                {
                    firstClick = false
                    if (enableSubmit) changePassword()
                }
            },
            onBackClick = onBackClick
        )
    }
}

@Composable
internal fun ChangePassScreen(
    modifier: Modifier = Modifier,
    oldPassword: String,
    onOldPassChange: (String) -> Unit,
    oldPasswordError: String,
    newPassword: String,
    onNewPassChange: (String) -> Unit,
    newPasswordError: String,
    newPasswordRepeat: String,
    onNewPassRepeatChange: (String) -> Unit,
    newPasswordRepeatError: String,
    enableBtnSubmit: Boolean,
    onBtnSubmitClick: () -> Unit,
    onBackClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.settingChangePassTitle),
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        }
    ) {

        Image(
            painter = painterResource(id = R.drawable.story_pass),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )
        TextTitle(stringResource(id = R.string.settingChangePassTitle))
        Spacing(MARGIN_DIFFERENT_VIEW)
        Spacing(PADDING_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onOldPassChange,
            text = oldPassword,
            label = stringResource(id = R.string.passwordTitle),
            typeInput = KeyboardType.Password,
            errorText = oldPasswordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Password),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White
        )
        Spacing(PADDING_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onNewPassChange,
            text = newPassword,
            label = stringResource(id = R.string.newPasswordTitle),
            typeInput = KeyboardType.Password,
            errorText = newPasswordError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Password),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White
        )
        Spacing(PADDING_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onNewPassRepeatChange,
            text = newPasswordRepeat,
            label = stringResource(id = R.string.reTypePasswordTitle),
            typeInput = KeyboardType.Password,
            errorText = newPasswordRepeatError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Password),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnSubmitClick,
            title = stringResource(id = R.string.settingChangePassTitle),
            enabled = enableBtnSubmit,
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
