package com.example.realestateapp.ui.post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ButtonRadiusGradient
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_POST
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun PostRoute(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navigateToRecord: (Boolean) -> Unit,
    navigateToAddPost: (Int) -> Unit
) {
    viewModel.run {

        PostScreen(
            modifier = modifier,
            navigateToRecord = navigateToRecord,
            navigateToAddPost = navigateToAddPost
        )
    }
}

@Composable
internal fun PostScreen(
    modifier: Modifier,
    navigateToRecord: (Boolean) -> Unit,
    navigateToAddPost: (Int) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(title = stringResource(id = R.string.managePostTitle))
        },
    ) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        Spacing(MARGIN_VIEW)
        Image(
            painter = painterResource(id = R.drawable.post_cuate),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = RealEstateAppTheme.colors.primary
                    )
                )
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadiusGradient(
            onClick = {
                navigateToRecord(true)
            },
            title = stringResource(id = R.string.yourPostTitle),
            bgColor = RealEstateAppTheme.colors.bgButtonGradient,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(0.95f)
        )
        Spacing(MARGIN_VIEW)
        ButtonRadiusGradient(
            onClick = {
                navigateToRecord(false)
            },
            title = stringResource(id = R.string.savePostTitle),
            bgColor = RealEstateAppTheme.colors.bgButtonGradient,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(0.95f)
        )
        Spacing(MARGIN_VIEW)
        ButtonRadiusGradient(
            onClick = { navigateToAddPost(DEFAULT_ID_POST) },
            title = stringResource(id = R.string.addPostTitle),
            bgColor = RealEstateAppTheme.colors.bgButtonGradient,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth(0.95f)
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
