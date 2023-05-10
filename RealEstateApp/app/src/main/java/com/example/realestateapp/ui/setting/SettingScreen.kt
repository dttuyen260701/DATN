package com.example.realestateapp.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.models.view.SettingButton
import com.example.realestateapp.data.repository.ViewDataRepository
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealStateAppTheme
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    onEditClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onChangePassClick: () -> Unit,
    onPostSavedClick: () -> Unit,
    onSignOutSuccess: () -> Unit
) {
    val user = remember {
        viewModel.getUser()
    }
    SettingScreen(
        modifier = modifier,
        listSettingButton = if (user.value == null) ViewDataRepository.getListSettingSignOut()
        else ViewDataRepository.getListSettingSignIn(),
        user = user.value,
        onEditClick = onEditClick,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        onPolicyClick = onPolicyClick,
        onAboutUsClick = onAboutUsClick,
        onChangePassClick = onChangePassClick,
        onPostSavedClick = onPostSavedClick,
        onLogoutListener = {
            viewModel.signOut()
            onSignOutSuccess()
        }
    )
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
    onPostSavedClick: () -> Unit,
    onLogoutListener: () -> Unit
) {
    BaseScreen(modifier = modifier) {
        Spacing(TOOLBAR_HEIGHT)
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
                    style = RealStateTypography.h2.copy(
                        color = RealStateAppTheme.colors.primary,
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
                    style = RealStateTypography.button.copy(
                        color = RealStateAppTheme.colors.textSettingButton,
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
                IconRealStateApp(
                    icon = AppIcon.ImageVectorIcon(RealStateIcon.Edit),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .aspectRatio(1f)
                        .padding(Constants.DefaultValue.PADDING_ICON.dp)
                        .constrainAs(btnEdit) {
                            top.linkTo(imgUser.top)
                            bottom.linkTo(imgUser.bottom)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            onEditClick()
                        },
                    tint = RealStateAppTheme.colors.primary
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
                    R.string.settingPostSavedTitle -> {
                        onPostSavedClick
                    }
                    else -> {
                        onLogoutListener
                    }
                },
                modifier = Modifier
                    .height(TOOLBAR_HEIGHT.dp)
                    .fillMaxWidth(),
                title = stringResource(id = button.title),
                leadingIcon = button.leadingIcon,
                backgroundIcon = RealStateAppTheme.colors.primary
            )
        }
    }
    ToolbarView(title = stringResource(id = R.string.settingTitle))
}
