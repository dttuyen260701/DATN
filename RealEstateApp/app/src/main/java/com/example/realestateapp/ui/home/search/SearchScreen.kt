package com.example.realestateapp.ui.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.SearchOption
import com.example.realestateapp.designsystem.components.ButtonUnRepeating
import com.example.realestateapp.designsystem.components.EditTextFullIconBorderRadius
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN

/**
 * Created by tuyen.dang on 5/19/2023.
 */

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    searchOption: Int,
    onBackClick: () -> Unit
) {
    viewModel.run {
        when (searchOption) {
            SearchOption.LATEST.option -> {

            }
            SearchOption.MOST_VIEW.option -> {

            }
            SearchOption.HIGHEST_PRICE.option -> {

            }
            SearchOption.LOWEST_PRICE.option -> {

            }
        }
        SearchScreen(
            modifier = modifier,
            onBackClick = onBackClick
        )
    }
}

@Composable
internal fun SearchScreen(
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(PADDING_HORIZONTAL_SCREEN.dp)
                    .fillMaxWidth()
            ) {
                val (btnBack, edtSearch, searchOptionGroup) = createRefs()
                ButtonUnRepeating(onBackClick) {
                    IconButton(
                        onClick = it,
                        modifier = Modifier
                            .size(ICON_ITEM_SIZE.dp)
                            .background(
                                color = RealEstateAppTheme.colors.primary,
                                shape = CircleShape
                            )
                            .constrainAs(btnBack) {
                                start.linkTo(parent.start)
                                linkTo(top = edtSearch.top, bottom = edtSearch.bottom)
                            }
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.LeftArrow),
                            modifier = Modifier
                                .size(Constants.DefaultValue.TRAILING_ICON_SIZE.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                EditTextFullIconBorderRadius(
                    onTextChange = {},
                    hint = stringResource(id = R.string.searchHint),
                    leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Search),
                    borderColor = RealEstateAppTheme.colors.primary,
                    leadingIconColor = RealEstateAppTheme.colors.primary,
                    onLeadingIconClick = {

                    },
                    trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Config),
                    trailingIconColor = RealEstateAppTheme.colors.primary,
                    onTrailingIconClick = {

                    },
                    modifier = Modifier
                        .constrainAs(edtSearch) {
                            top.linkTo(parent.top)
                            linkTo(
                                start = btnBack.end,
                                startMargin = MARGIN_VIEW.dp,
                                end = parent.end
                            )
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    ) {

    }
}
