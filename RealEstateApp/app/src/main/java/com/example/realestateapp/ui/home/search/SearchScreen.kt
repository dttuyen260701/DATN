package com.example.realestateapp.ui.home.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.data.enums.SearchOption
import com.example.realestateapp.ui.base.BaseScreen

/**
 * Created by tuyen.dang on 5/19/2023.
 */

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    searchOption: Int
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
            modifier = modifier
        )
    }
}

@Composable
internal fun SearchScreen(
    modifier: Modifier
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (btnBack, edtSearch, searchOptionGroup) = createRefs()
            }
        }
    ) {

    }
}
