package com.example.realestateapp.ui.pickaddress

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.extension.handleAddressException
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DISTRICT
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET
import com.example.realestateapp.util.Constants.DefaultField.FIELD_WARD
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        var uiState by remember { uiState }
        val coroutineScope = rememberCoroutineScope()
        var detailStreet by remember { PickAddressViewModel.detailStreet }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is PickAddressUiState.InitView -> {
                    getDistricts("") {}
                }
                is PickAddressUiState.Loading -> {

                }
                is PickAddressUiState.GetDistrictSuccess -> {
                    districts.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetDistrictSuccess).data)
                    }
                    uiState = PickAddressUiState.Done
                }
                is PickAddressUiState.GetWardSuccess -> {
                    wards.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetWardSuccess).data)
                    }
                    uiState = PickAddressUiState.Done
                }
                is PickAddressUiState.GetStreetSuccess -> {
                    streets.run {
                        clear()
                        addAll((uiState as PickAddressUiState.GetStreetSuccess).data)
                    }
                    uiState = PickAddressUiState.Done
                }
                else -> {}
            }
        }

        PickAddressScreen(
            modifier = modifier,
            enableBtnConfirm = true,
            onBackClick = onBackClick,
            onFindLocationClick = remember {
                {
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
                                        PickAddressViewModel.let {
                                            it.longitude = longitude
                                            it.latitude = latitude
                                        }
                                        val coder = Geocoder(context, Locale.getDefault())
                                        var addressLine = ""
                                        try {
                                            val result = coder.getFromLocation(
                                                latitude, longitude, 1
                                            )
                                            if (!result.isNullOrEmpty()) {
                                                addressLine = result[0].getAddressLine(0)
                                            }
                                            updateChoiceData(
                                                nameDistrict = result?.get(0)?.subAdminArea?.handleAddressException(),
                                                nameWard = getWardFromAddressLine(
                                                    addressLine,
                                                    result?.get(0)?.subAdminArea?.handleAddressException()
                                                        ?: ""
                                                ),
                                                nameStreet = result?.get(0)?.thoroughfare?.handleAddressException()
                                            )
                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }
                                    }
                                }
                        }
                    }
                }
            },
            onComboBoxClick = remember {
                { key ->
                    var title = ""
                    var data = mutableListOf<ItemChoose>()
                    var loadData: (String, () -> Unit) -> Unit = { _, _ -> }
                    var onItemClick: (ItemChoose) -> Unit = { _ -> }
                    var isValid = true
                    when (key) {
                        FIELD_DISTRICT -> {
                            districts.clear()
                            title = context.getString(R.string.districtTitle)
                            data = districts
                            onItemClick = { itemChoose ->
                                onChoiceData(
                                    itemChoose = itemChoose,
                                    key = key
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
                                        key = key
                                    )
                                    showDialog(dialog = TypeDialog.Hide)
                                }
                                loadData = { _, onDone ->
                                    getWards(onDoneApi = onDone)
                                }
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
                                        key = key
                                    )
                                    showDialog(dialog = TypeDialog.Hide)
                                }
                                loadData = { filter, onDone ->
                                    getStreets(
                                        filter = filter,
                                        onDoneApi = onDone
                                    )
                                }
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
                            dialog = TypeDialog.ChoiceDataDialog(
                                title = title,
                                data = data,
                                loadData = loadData,
                                onItemClick = onItemClick,
                                isEnableSearchFromApi = (key == FIELD_STREET)
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
                    coroutineScope.launch(Dispatchers.IO) {
                        getIsLoading().value = true
                        val coder = Geocoder(context, Locale.getDefault())
                        val address: ArrayList<Address>?

                        try {
                            address =
                                coder.getFromLocationName(
                                    "$detailStreet $streetName$wardName${districtChosen.name}",
                                    1
                                ) as ArrayList<Address>?
                            address?.firstOrNull()?.run {
                                PickAddressViewModel.let {
                                    it.longitude = this.longitude
                                    it.latitude = this.latitude
                                }
                            }
                        } catch (ex: IOException) {
                            PickAddressViewModel.let {
                                it.longitude = 0.0
                                it.latitude = 0.0
                            }
                        }
                        getIsLoading().value = false

                        withContext(Dispatchers.Main) {
                            onPickAddressSuccess(
                                "$detailStreet $streetName$wardName${districtChosen.name}"
                            )
                        }
                    }
                }
            },
            onClearData = remember {
                {
                    when (it) {
                        FIELD_DISTRICT -> {
                            districtChosen = DEFAULT_ITEM_CHOSEN
                            wardChosen = DEFAULT_ITEM_CHOSEN
                            streetChosen = DEFAULT_ITEM_CHOSEN
                            detailStreet = ""
                        }
                        FIELD_WARD -> {
                            wardChosen = DEFAULT_ITEM_CHOSEN
                            streetChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_STREET -> {
                            streetChosen = DEFAULT_ITEM_CHOSEN
                            detailStreet = ""
                        }
                        else -> {}
                    }
                }
            },
            districtChosen = districtChosen,
            wardChosen = wardChosen,
            streetChosen = streetChosen,
            detailStreet = detailStreet,
            onDetailStreetChange = remember {
                {
                    detailStreet = it
                }
            }
        )
    }
}

private fun getWardFromAddressLine(addressLine: String, districtName: String): String {
    val temp = addressLine.split(", $districtName,")[0]
    return temp
        .split(",")[temp.split(",").lastIndex]
        .replace("Hòa", "Hoà")
        .replace("Hòa Thuận Nam", "Hòa Thuận Tây")
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
    streetChosen: ItemChoose,
    detailStreet: String,
    onDetailStreetChange: (String) -> Unit
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
        Spacing(PADDING_HORIZONTAL_SCREEN)
        EditTextTrailingIconCustom(
            onTextChange = onDetailStreetChange,
            text = detailStreet,
            label = stringResource(id = R.string.detailTitle),
            typeInput = KeyboardType.Text,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.detailTitle)
            ),
            errorText = "",
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Title),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true,
            readOnly = (districtChosen == DEFAULT_ITEM_CHOSEN)
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonRadius(
            onClick = onBtnConfirmClick,
            title = stringResource(id = R.string.confirmTitle),
            enabled = enableBtnConfirm,
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
