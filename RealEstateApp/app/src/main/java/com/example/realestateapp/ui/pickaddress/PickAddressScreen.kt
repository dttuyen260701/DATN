package com.example.realestateapp.ui.pickaddress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.components.ButtonRadius
import com.example.realestateapp.designsystem.components.ComboBox
import com.example.realestateapp.designsystem.components.Spacing
import com.example.realestateapp.designsystem.components.ToolbarView
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DISTRICT
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET
import com.example.realestateapp.util.Constants.DefaultField.FIELD_WARD
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_CHOSEN
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
    val context = LocalContext.current
    viewModel.run {
        val districts = remember { districtsData }
        val wards = remember { wardsData }
        val uiState by remember { uiState }
        val isLoadingDialog by remember {
            derivedStateOf {
                uiState is PickAddressUiState.Loading
            }
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is PickAddressUiState.InitView -> {

                }
                is PickAddressUiState.GetDistrictSuccess -> {
                    districts.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetDistrictSuccess).data)
                    }
                }
                is PickAddressUiState.GetWardSuccess -> {
                    wards.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetWardSuccess).data)
                    }
                }
                else -> {}
            }
        }

        PickAddressScreen(
            modifier = modifier,
            enableBtnConfirm = true,
            onBackClick = onBackClick,
            onComboBoxClick = remember {
                {
                    var title = ""
                    var data = mutableListOf<ItemChoose>()
                    var loadData: (String) -> Unit = { _ -> }
                    var onItemClick: (ItemChoose) -> Unit = { _ -> }
                    var isValid = true
                    when (it) {
                        FIELD_DISTRICT -> {
                            title = context.getString(R.string.districtTitle)
                            data = districtsData
                            onItemClick = { itemChoose ->
                                onChoiceData(
                                    data = districtsData,
                                    itemChoose = itemChoose,
                                    key = it
                                )
                            }
                            loadData = ::getDistrictData
                        }
                        FIELD_WARD -> {
                            if (PickAddressViewModel.districtIdChosen != DEFAULT_ID_CHOSEN) {
                                title = context.getString(R.string.wardTitle)
                                data = wardsData
                                onItemClick = { itemChoose ->
                                    onChoiceData(
                                        data = wardsData,
                                        itemChoose = itemChoose,
                                        key = it
                                    )
                                }
                                loadData = ::getWardData
                            } else {
                                context.run {
                                    makeToast(
                                        getString(
                                            R.string.pleaseChoiceHint,
                                            getString(R.string.districtTitle)
                                        )
                                    )
                                }
                                isValid = false
                            }
                        }
                        FIELD_STREET -> {

                        }
                        else -> {}
                    }
                    if (isValid) {
                        showDialog(
                            dialog = showDialogChoiceData(
                                title = title,
                                data = data,
                                loadData = loadData,
                                onItemClick = onItemClick,
                                isLoadingDialog = isLoadingDialog
                            )
                        )
                    }
                }
            },
            onBtnConfirmClick = remember {
                {}
            },
        )
    }
}

private fun showDialogChoiceData(
    title: String,
    data: MutableList<ItemChoose>,
    loadData: (String) -> Unit,
    onItemClick: (ItemChoose) -> Unit,
    isEnableSearchFromApi: Boolean = false,
    isLoadingDialog: Boolean
): TypeDialog {
    return TypeDialog.ChoiceDataDialog(
        isLoading = isLoadingDialog,
        title = title,
        loadData = loadData,
        onItemClick = onItemClick,
        isEnableSearchFromApi = isEnableSearchFromApi,
        data = data
    )
}

@Composable
internal fun PickAddressScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onComboBoxClick: (String) -> Unit,
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
                onComboBoxClick(FIELD_DISTRICT)
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
            onItemClick = {
                onComboBoxClick(FIELD_WARD)
            },
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
