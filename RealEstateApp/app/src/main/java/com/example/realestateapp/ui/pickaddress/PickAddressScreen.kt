package com.example.realestateapp.ui.pickaddress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/22/2023.
 */

@Composable
internal fun PickAddressRoute(
    modifier: Modifier = Modifier,
    viewModel: PickAddressViewModel = hiltViewModel(),
    onPickAddressSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    viewModel.run {

        PickAddressScreen(
            modifier = modifier,
            onPickAddressSuccess = remember { onPickAddressSuccess },
            onBackClick = onBackClick
        )
    }
}

@Composable
internal fun PickAddressScreen(
    modifier: Modifier,
    onPickAddressSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.pickAddressTitle),
                rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.TargetLocation),
                onRightIconClick = {

                },
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        }
    ) {

    }
}
