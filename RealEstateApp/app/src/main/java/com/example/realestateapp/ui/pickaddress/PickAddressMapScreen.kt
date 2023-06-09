package com.example.realestateapp.ui.pickaddress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.realestateapp.extension.getFullAddress
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 6/9/2023.
 */

@Composable
internal fun PickAddressMapRoute(
    modifier: Modifier = Modifier,
    viewModel: PickAddressViewModel = hiltViewModel(),
    onPickAddressSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    viewModel.run {
        val coroutineScope = rememberCoroutineScope()

        PickAddressMapScreen(
            modifier = modifier,
            onBackClick = remember { onBackClick },
            address = "",
            location = LatLng(
                PickAddressViewModel.latitude,
                PickAddressViewModel.longitude
            ),
            onBtnConfirmClick = remember {
                {
                    context.getFullAddress(
                        coroutineScope = coroutineScope,
                        onStart = {
                            getIsLoading().value = true
                        },
                        onSuccess = { result ->
                            getIsLoading().value = false
                            coroutineScope.launch(Dispatchers.Main) {
                                onPickAddressSuccess(result)
                            }
                        }
                    )
                }
            },
        )
    }
}

@Composable
internal fun PickAddressMapScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    address: String,
    location: LatLng,
    onBtnConfirmClick: () -> Unit
) {

    BaseScreen(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        paddingHorizontal = 0,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.pickAddressTitle),
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        }
    ) {
        Spacing(PADDING_HORIZONTAL_SCREEN)
        ComboBox(
            modifier = Modifier.padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
            onItemClick = {},
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.City),
            title = stringResource(id = R.string.addressTitle),
            value = address,
            isAllowClearData = false,
            onClearData = { }
        )
        val mapProperties = remember {
            MapProperties(
                maxZoomPreference = Constants.MapConfig.MAX_ZOOM,
                minZoomPreference = Constants.MapConfig.MIN_ZOOM,
                mapType = MapType.NORMAL
            )
        }
        val cameraPositionState = rememberCameraPositionState(location.toString())
        val mapUiSettings = remember {
            // We are providing our own zoom controls so disable the built-in ones.
            MapUiSettings(
                scrollGesturesEnabled = false,
                zoomGesturesEnabled = false,
                rotationGesturesEnabled = false
            )
        }
        Spacing(MARGIN_VIEW)
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings,
            properties = mapProperties,
            onMapLoaded = {
                cameraPositionState.move(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(
                            location,
                            Constants.MapConfig.DEFAULT_ZOOM
                        )
                    )
                )
            }
        ) {
            Marker(
                state = MarkerState(position = location),
                title = stringResource(id = R.string.locationTitle),
                snippet = stringResource(id = R.string.locationDes)
            )
        }
        Spacing(MARGIN_VIEW)
        ButtonRadius(
            onClick = onBtnConfirmClick,
            title = stringResource(id = R.string.confirmTitle),
            bgColor = RealEstateAppTheme.colors.primary,
            modifier = Modifier
                .height(TOOLBAR_HEIGHT.dp)
                .fillMaxWidth()
                .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
        )
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
