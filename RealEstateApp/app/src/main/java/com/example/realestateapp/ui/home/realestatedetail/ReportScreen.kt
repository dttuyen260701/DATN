package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.WARNING_TEXT_SIZE

/**
 * Created by tuyen.dang on 6/8/2023.
 */

@Composable
internal fun ReportRoute(
    modifier: Modifier = Modifier,
    viewModel: RealEstateDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    idPost: Int
) {
    val context = LocalContext.current

    viewModel.run {
        val uiState by uiEffect.collectAsStateWithLifecycle()

        var firstClick by remember { firstClick }
        var description by remember { description }
        val descriptionError by remember {
            derivedStateOf {
                if (description.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.descriptionTitle)
                    )
                else ""
            }
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is RealEstateDetailUiEffect.Done -> {
                    context.run {
                        makeToast(getString(R.string.reportPostSuccessTitle))
                    }
                    onBackClick()
                }
                else -> {}
            }
        }

        val isEnableSubmit by remember {
            derivedStateOf {
                descriptionError.isEmpty()
            }
        }

        RouteScreen(
            modifier = modifier,
            onBackClick = remember { onBackClick },
            idPost = idPost,
            isEnableSubmit = isEnableSubmit,
            onDescriptionChange = remember {
                {
                    description = it
                }
            },
            onClearDescription = remember {
                {
                    description = ""
                }
            },
            description = description,
            descriptionError = descriptionError,
            onSubmitClick = remember {
                {
                    firstClick = false
                    if (isEnableSubmit) {
                        createReport(idPost)
                    }
                }
            }
        )
    }
}

@Composable
internal fun RouteScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    idPost: Int,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onClearDescription: () -> Unit,
    descriptionError: String,
    isEnableSubmit: Boolean,
    onSubmitClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.reportTitle),
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
                    title = stringResource(id = R.string.reportTitle),
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
        TextTitle(
            stringResource(
                id = R.string.reportPostTitle,
                idPost
            )
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
        TextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = stringResource(
                        id = R.string.mandatoryTitle,
                        stringResource(id = R.string.descriptionTitle)
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.limit300CharTitle,
                        stringResource(id = R.string.descriptionTitle)
                    ),
                    color = RealEstateAppTheme.colors.primary.copy(Constants.DefaultValue.ALPHA_HINT_COLOR),
                    style = RealEstateTypography.body1.copy(
                        textAlign = TextAlign.Start
                    ),
                )
            },
            trailingIcon = {
                if (description.isNotEmpty()) {
                    IconButton(
                        onClick = onClearDescription,
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            },
            textStyle = RealEstateTypography.body1.copy(
                textAlign = TextAlign.Start,
                color = RealEstateAppTheme.colors.primary
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .heightIn(
                    min = (LocalConfiguration.current.screenHeightDp * 0.2f).dp,
                    max = (LocalConfiguration.current.screenHeightDp * 0.5f).dp
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(Constants.DefaultValue.ROUND_RECTANGLE.dp),
                    color = RealEstateAppTheme.colors.primary
                ),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = RealEstateAppTheme.colors.primary,
                backgroundColor = RealEstateAppTheme.colors.bgTextField,
                cursorColor = RealEstateAppTheme.colors.primary,
                leadingIconColor = RealEstateAppTheme.colors.primary,
                trailingIconColor = RealEstateAppTheme.colors.primary,
                focusedLabelColor = RealEstateAppTheme.colors.primary,
                unfocusedLabelColor = RealEstateAppTheme.colors.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorCursorColor = RealEstateAppTheme.colors.primary,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
        Spacing(PADDING_VIEW)
        Text(
            text = descriptionError,
            style = RealEstateTypography.caption.copy(
                color = Color.Red,
                fontSize = WARNING_TEXT_SIZE.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = MARGIN_VIEW.dp)
        )
        Spacing(MARGIN_VIEW)
    }
}
 