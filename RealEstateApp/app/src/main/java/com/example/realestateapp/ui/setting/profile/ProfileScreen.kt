package com.example.realestateapp.ui.setting.profile

import android.Manifest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.User
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.setVisibility
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/11/2023.
 */

@Composable
internal fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    viewModel.run {
        val user by remember { getUser() }
        val uiState by remember { uiState }
        var firstClick by remember { firstClick }
        var imgUrl by remember { imgUrl }
        var name by remember { name }
        val nameError by remember {
            derivedStateOf {
                if (name.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.floorTitle)
                    )
                else ""
            }
        }
        var isUploading by remember { mutableStateOf(false) }

        val isEnableSubmit by remember {
            derivedStateOf {
                nameError.isEmpty()
            }
        }

        LaunchedEffect(key1 = uiState) {

        }

        user?.let { value ->
            ProfileScreen(
                modifier = modifier,
                onBackClick = remember { onBackClick },
                isUploading = isUploading,
                user = value,
                imgUrl = imgUrl,
                onImageUserClick = remember {
                    {
                        requestPermissionListener(
                            permission = mutableListOf(
                                Manifest.permission.CAMERA
                            )
                        ) { results ->
                            if (results.entries.all { it.value }) {
                                uploadImageAndGetURL(
                                    onStart = {
                                        isUploading = true
                                    }
                                ) {
                                    isUploading = false
                                    if (it.trim().isNotEmpty()) {
                                        imgUrl = it
                                    }
                                }
                            }
                        }
                    }
                },
                name = name,
                onNameChange = remember {
                    {
                        name = it
                    }
                },
                nameError = nameError,
                isEnableSubmit = isEnableSubmit,
                onSubmitClick = remember {
                    {
                        firstClick = false
                    }
                }
            )
        }
    }
}

@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    isUploading: Boolean,
    user: User,
    imgUrl: String,
    onImageUserClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String,
    isEnableSubmit: Boolean,
    onSubmitClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.settingUserTitle),
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        },
        footer = {
            BorderLine()
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = PADDING_HORIZONTAL_SCREEN.dp,
                        vertical = MARGIN_VIEW.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                ButtonRadiusGradient(
                    onClick = onSubmitClick,
                    title = stringResource(id = R.string.updateTitle),
                    enabled = isEnableSubmit,
                    bgColor = RealEstateAppTheme.colors.bgButtonGradient,
                    modifier = Modifier
                        .height(TOOLBAR_HEIGHT.dp)
                        .fillMaxWidth()
                )
            }
        },
        verticalArrangement = Arrangement.Top
    ) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (imgUser, icAdd, tvEmail, progressBar) = createRefs()
            ImageProfile(
                size = TOOLBAR_HEIGHT * 2,
                model = imgUrl,
                modifier = Modifier
                    .constrainAs(imgUser) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                        )
                        linkTo(
                            top = parent.top,
                            bottom = tvEmail.top,
                            bottomMargin = MARGIN_VIEW.dp
                        )
                    }
                    .clickable {
                        onImageUserClick()
                    }
            )
            ButtonUnRepeating(onImageUserClick) {
                IconButton(
                    onClick = if (isUploading) { -> } else it,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(2.dp)
                        .aspectRatio(1f, false)
                        .constrainAs(icAdd) {
                            end.linkTo(imgUser.end)
                            bottom.linkTo(imgUser.bottom)
                        }
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .border(
                            BorderStroke(1.dp, RealEstateAppTheme.colors.primary),
                            CircleShape
                        ),
                ) {
                    BaseIcon(
                        icon = AppIcon.ImageVectorIcon(RealEstateIcon.Add),
                        contentDescription = null,
                        tint = RealEstateAppTheme.colors.primary
                    )
                }
            }
            Text(
                text = user.email,
                style = RealEstateTypography.h2.copy(
                    color = RealEstateAppTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .constrainAs(tvEmail) {
                        linkTo(imgUser.start, imgUser.end)
                        linkTo(
                            top = imgUser.top,
                            bottom = parent.bottom,
                            bottomMargin = 2.dp,
                            bias = 1F
                        )
                    }
            )
            CircularProgressIndicator(
                color = RealEstateAppTheme.colors.progressBar,
                modifier = Modifier
                    .constrainAs(progressBar) {
                        linkTo(imgUser.top, imgUser.bottom)
                        linkTo(imgUser.start, imgUser.end)
                        visibility = setVisibility(isUploading)
                    }
            )

        }
        Spacing(PADDING_VIEW)
        BorderLine()
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onNameChange,
            text = name,
            label = stringResource(id = R.string.nameTitle),
            typeInput = KeyboardType.Text,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.nameTitle)
            ),
            errorText = nameError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isLastEditText = true,
        )
    }
}
