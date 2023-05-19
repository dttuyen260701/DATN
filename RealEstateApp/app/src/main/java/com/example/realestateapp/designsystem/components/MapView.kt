package com.example.realestateapp.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.realestateapp.R
import com.example.realestateapp.util.Constants.MapConfig.DEFAULT_ZOOM
import com.example.realestateapp.util.Constants.MapConfig.MAX_ZOOM
import com.example.realestateapp.util.Constants.MapConfig.MIN_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 5/19/2023.
 */

@Composable
internal fun MapviewShowMarker(
    modifier: Modifier = Modifier,
    location: LatLng
) {
    val coroutineScope = rememberCoroutineScope()
    val mapProperties = remember {
        MapProperties(
            maxZoomPreference = MAX_ZOOM,
            minZoomPreference = MIN_ZOOM,
            mapType = MapType.NORMAL
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, DEFAULT_ZOOM)
    }
    val mapUiSettings = remember {
        // We are providing our own zoom controls so disable the built-in ones.
        MapUiSettings(
            scrollGesturesEnabled = false,
            zoomGesturesEnabled = false,
            rotationGesturesEnabled = false
        )
    }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {

        }
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
        properties = mapProperties
    ) {
        Marker(
            state = MarkerState(position = location),
            title = stringResource(id = R.string.locationTitle),
            snippet = stringResource(id = R.string.locationDes)
        )
    }
}
