package com.example.realestateapp.ui.home.realestatedetail

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/16/2023.
 */

@Composable
internal fun RealEstateDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    realEstateId: Int,
) {
    RealEstateDetailScreen(
        modifier = modifier
    )
}

@Composable
internal fun RealEstateDetailScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    BaseScreen(
        modifier = modifier,
        scrollState = scrollState,
        toolbar = {

        }
    ) {

    }
}
