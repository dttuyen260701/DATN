package com.example.realestateapp.ui.setting

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.*
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.compose.*
import com.example.realestateapp.R
import com.example.realestateapp.data.models.*
import com.example.realestateapp.data.models.view.*
import com.example.realestateapp.data.repository.*
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.*
import com.example.realestateapp.designsystem.theme.*
import com.example.realestateapp.ui.base.*
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_ICON
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onEditClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onSignOutSuccess: () -> Unit
) {
    val context = LocalContext.current
    viewModel.run {
        val user = remember {
            getUser()
        }
        val uiState by uiEffect.collectAsStateWithLifecycle()

        when (uiState) {
            is SettingUiEffect.SignOutSuccess -> {
                onSignOutSuccess()
            }
        }
        SettingScreen(
            modifier = modifier,
            listSettingButton = remember {
                if (user.value == null) {
                    ViewDataRepository.getListSettingSignOut()
                } else {
                    ViewDataRepository.getListSettingSignIn()
                }
            },
            user = user.value,
            onEditClick = onEditClick,
            onSignInClick = onSignInClick,
            onSignUpClick = onSignUpClick,
            onPolicyClick = onPolicyClick,
            onAboutUsClick = onAboutUsClick,
            onChangePassClick = onChangePassClick,
            onSignOutListener = {
                showSignOutDialog(
                    message = context.getString(R.string.confirmSignOut),
                    negativeBtnText = context.getString(R.string.dialogBackBtn),
                    positiveBtnText = context.getString(R.string.settingSignOutTitle),
                )
            }
        )
    }
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    listSettingButton: MutableList<SettingButton>,
    user: User?,
    onEditClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onSignOutListener: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        toolbar = {
            ToolbarView(title = stringResource(id = R.string.settingTitle))
        }
    ) {
        user?.run {
            Spacing(MARGIN_VIEW)
            ConstraintLayout(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
            ) {
                val (
                    imgUser,
                    tvName,
                    tvMail,
                    btnEdit
                ) = createRefs()
                val verticalGuideLine = createGuidelineFromTop(0.5f)
                ImageProfile(
                    size = 100,
                    model = imgUrl ?: "",
                    modifier = Modifier
                        .constrainAs(imgUser) {
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = fullName,
                    style = RealEstateTypography.h2.copy(
                        color = RealEstateAppTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .constrainAs(tvName) {
                            start.linkTo(imgUser.end, margin = MARGIN_VIEW.dp)
                            linkTo(
                                top = imgUser.top,
                                bottom = verticalGuideLine,
                                bottomMargin = 2.dp,
                                bias = 1F
                            )
                        }
                        .clickable {
                            onEditClick()
                        }
                )
                Text(
                    text = email,
                    style = RealEstateTypography.button.copy(
                        color = RealEstateAppTheme.colors.textSettingButton,
                    ),
                    modifier = Modifier
                        .constrainAs(tvMail) {
                            start.linkTo(imgUser.end, margin = MARGIN_VIEW.dp)
                            linkTo(
                                top = verticalGuideLine,
                                bottom = imgUser.bottom,
                                bottomMargin = 2.dp,
                                bias = 0F
                            )
                        }
                        .clickable {
                            onEditClick()
                        }
                )
                BaseIcon(
                    icon = AppIcon.ImageVectorIcon(RealEstateIcon.Edit),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .aspectRatio(1f)
                        .padding(PADDING_ICON.dp)
                        .constrainAs(btnEdit) {
                            top.linkTo(imgUser.top)
                            bottom.linkTo(imgUser.bottom)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            onEditClick()
                        },
                    tint = RealEstateAppTheme.colors.primary
                )
            }
            Spacing(MARGIN_VIEW)
        }
        listSettingButton.forEach { button ->
            Spacing(MARGIN_VIEW)
            SettingButton(
                onClick = when (button.title) {
                    R.string.settingSignInTitle -> {
                        onSignInClick
                    }

                    R.string.settingSignUpTitle -> {
                        onSignUpClick
                    }

                    R.string.settingPolicyTitle -> {
                        onPolicyClick
                    }

                    R.string.settingAboutUsTitle -> {
                        onAboutUsClick
                    }

                    R.string.settingChangePassTitle -> {
                        onChangePassClick
                    }

                    else -> {
                        onSignOutListener
                    }
                },
                modifier = Modifier
                    .height(TOOLBAR_HEIGHT.dp)
                    .fillMaxWidth(),
                title = stringResource(id = button.title),
                leadingIcon = button.leadingIcon,
                backgroundIcon = RealEstateAppTheme.colors.primary
            )
        }
    }
}
