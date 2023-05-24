package com.example.realestateapp.ui.pickaddress

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.util.Log
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
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

/**
 * Created by tuyen.dang on 5/22/2023.
 */

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
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
        val streets = remember { streetsData }
        var districtChosen by remember { PickAddressViewModel.districtChosen }
        var wardChosen by remember { PickAddressViewModel.wardChosen }
        var streetChosen by remember { PickAddressViewModel.streetChosen }
        val uiState by remember { uiState }
        var isLoadingDialog by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is PickAddressUiState.InitView -> {

                }
                is PickAddressUiState.Loading -> {
                    isLoadingDialog = true
                }
                is PickAddressUiState.GetDistrictSuccess -> {
                    isLoadingDialog = false
                    districts.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetDistrictSuccess).data)
                    }
                }
                is PickAddressUiState.GetWardSuccess -> {
                    isLoadingDialog = false
                    wards.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetWardSuccess).data)
                    }
                }
                is PickAddressUiState.GetStreetSuccess -> {
                    isLoadingDialog = false
                    streets.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetStreetSuccess).data)
                    }
                }
                else -> {
                    isLoadingDialog = false
                }
            }
        }

        PickAddressScreen(
            modifier = modifier,
            enableBtnConfirm = true,
            onBackClick = onBackClick,
            onFindLocationClick = remember {
                {
                    Log.e("TTT", "onFindLocationClick: ")
                    requestPermissionListener(
                        permission = mutableListOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        )
                    ) { results ->
                        if (results.entries.all { it.value }) {
                            val fusedLocationClient =
                                LocationServices.getFusedLocationProviderClient(context)
                            fusedLocationClient.lastLocation
                                .addOnSuccessListener { location: Location? ->
                                    location?.run {
                                        val coder = Geocoder(context, Locale.getDefault())
                                        var addressLine = ""
                                        try {
                                            val result = coder.getFromLocation(
                                                latitude, longitude, 1
                                            )
                                            if (!result.isNullOrEmpty()) {
                                                addressLine = result[0].getAddressLine(0)
                                            }
                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }
                                        Log.e("TTT", "PickAddressRoute: $addressLine ")
                                    }
                                }
                        }
                    }
                }
            },
            onComboBoxClick = remember {
                {
                    var title = ""
                    var data = mutableListOf<ItemChoose>()
                    var loadData: (String, () -> Unit) -> Unit = { _, _ -> }
                    var onItemClick: (ItemChoose) -> Unit = { _ -> }
                    var isValid = true
                    when (it) {
                        FIELD_DISTRICT -> {
                            districts.clear()
                            title = context.getString(R.string.districtTitle)
                            data = districts
                            onItemClick = { itemChoose ->
                                onChoiceData(
                                    itemChoose = itemChoose,
                                    key = it
                                )
                                showDialog(dialog = TypeDialog.Hide)
                            }
                            loadData = ::getDistricts
                        }
                        FIELD_WARD -> {
                            if (districtChosen != DEFAULT_ITEM_CHOSEN) {
                                wards.clear()
                                title = context.getString(R.string.wardTitle)
                                data = wards
                                onItemClick = { itemChoose ->
                                    onChoiceData(
                                        itemChoose = itemChoose,
                                        key = it
                                    )
                                    showDialog(dialog = TypeDialog.Hide)
                                }
                                loadData = ::getWards
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
                            if (districtChosen != DEFAULT_ITEM_CHOSEN) {
                                streets.clear()
                                title = context.getString(R.string.streetTitle)
                                data = streets
                                onItemClick = { itemChoose ->
                                    onChoiceData(
                                        itemChoose = itemChoose,
                                        key = it
                                    )
                                    showDialog(dialog = TypeDialog.Hide)
                                }
                                loadData = ::getStreets
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
                        else -> {}
                    }
                    if (isValid) {
                        showDialog(
                            dialog = showDialogChoiceData(
                                title = title,
                                data = data,
                                loadData = loadData,
                                onItemClick = onItemClick,
                                isEnableSearchFromApi = (it == FIELD_STREET),
                                isLoadingDialog = isLoadingDialog
                            )
                        )
                    }
                }
            },
            onBtnConfirmClick = remember {
                {
                    val streetName =
                        if (streetChosen != DEFAULT_ITEM_CHOSEN) "${streetChosen.name}, " else ""
                    val wardName =
                        if (wardChosen != DEFAULT_ITEM_CHOSEN) "${wardChosen.name}, " else ""
                    onPickAddressSuccess(
                        "$streetName$wardName${districtChosen.name}"
                    )
                }
            },
            onClearData = remember {
                {
                    when (it) {
                        FIELD_DISTRICT -> {
                            districtChosen = DEFAULT_ITEM_CHOSEN
                            wardChosen = DEFAULT_ITEM_CHOSEN
                            streetChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_WARD -> {
                            wardChosen = DEFAULT_ITEM_CHOSEN
                            streetChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_STREET -> {
                            streetChosen = DEFAULT_ITEM_CHOSEN
                        }
                        else -> {}
                    }
                }
            },
            districtChosen = districtChosen,
            wardChosen = wardChosen,
            streetChosen = streetChosen
        )
    }
}

private fun showDialogChoiceData(
    title: String,
    data: MutableList<ItemChoose>,
    loadData: (String, () -> Unit) -> Unit,
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
    onFindLocationClick: () -> Unit,
    onComboBoxClick: (String) -> Unit,
    enableBtnConfirm: Boolean,
    onBtnConfirmClick: () -> Unit,
    onClearData: (String) -> Unit,
    districtChosen: ItemChoose,
    wardChosen: ItemChoose,
    streetChosen: ItemChoose
) {
    BaseScreen(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.pickAddressTitle),
                rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.TargetLocation),
                onRightIconClick = onFindLocationClick,
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
            value = districtChosen.name,
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.districtTitle)
            ),
            onClearData = {
                onClearData(FIELD_DISTRICT)
            }
        )
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {
                onComboBoxClick(FIELD_WARD)
            },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Ward),
            title = stringResource(id = R.string.wardTitle),
            value = wardChosen.name,
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.wardTitle)
            ),
            onClearData = {
                onClearData(FIELD_WARD)
            }
        )
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier,
            onItemClick = {
                onComboBoxClick(FIELD_STREET)
            },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.StreetInFront),
            title = stringResource(id = R.string.streetTitle),
            value = streetChosen.name,
            hint = stringResource(
                id = R.string.pleaseChoiceHint,
                stringResource(id = R.string.streetTitle)
            ),
            onClearData = {
                onClearData(FIELD_STREET)
            }
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
