package com.example.realestateapp.ui.pickaddress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.components.ButtonRadius
import com.example.realestateapp.designsystem.components.ComboBox
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN

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
            enableBtnConfirm = true,
            onBackClick = onBackClick,
            onDistrictClick = remember {
                {
                    viewModel.showDialog(
                        dialog = TypeDialog.ChoiceDataDialog(
                            isLoading = true,
                            title = "Quận huyện",
                            filter = "",
                            onFilterChange = {

                            }
                        )
                    )
                }
            },
            onBtnConfirmClick = remember {
                {}
            },
        )
    }
}

private fun showDialog(
    idSelected: Int,
    keyType: Int
) {

}

@Composable
internal fun PickAddressScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onDistrictClick: () -> Unit,
    enableBtnConfirm: Boolean,
    onBtnConfirmClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.pickAddressTitle),
                rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.TargetLocation),
                onRightIconClick = {},
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        }
    ) {
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {},
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.City),
            title = stringResource(id = R.string.cityTitle),
            value = stringResource(id = R.string.addressHint),
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.cityTitle)
            ),
            isAllowClearData = false,
            onClearData = { }
        )
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {
                onDistrictClick()
            },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.District),
            title = stringResource(id = R.string.districtTitle),
            value = "",
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.districtTitle)
            ),
            onClearData = { }
        )
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {},
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Ward),
            title = stringResource(id = R.string.wardTitle),
            value = "",
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.wardTitle)
            ),
            onClearData = { }
        )
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {},
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.StreetInFront),
            title = stringResource(id = R.string.streetTitle),
            value = "",
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.streetTitle)
            ),
            onClearData = { }
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnConfirmClick,
            title = stringResource(id = R.string.confirmTitle),
            enabled = enableBtnConfirm,
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(Constants.DefaultValue.TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
