package com.example.realestateapp.designsystem.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by tuyen.dang on 5/14/2023.
 */

@Composable
internal fun ListItemHome(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = Color.Black,
    btnTitle: String? = null,
    btnClick: () -> Unit = {},
    btnColor: Color = RealEstateAppTheme.colors.primary,
    listRealEstate: MutableList<RealEstateList>,
    onItemClick: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val (tvTitle, btnSeeAll, listData) = createRefs()

        Text(
            text = title,
            style = RealEstateTypography.body1.copy(
                color = titleColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(tvTitle) {
                    top.linkTo(parent.top)
                    linkTo(
                        start = parent.start,
                        startMargin = PADDING_HORIZONTAL_SCREEN.dp,
                        end = btnSeeAll.start,
                        endMargin = MARGIN_VIEW.dp,
                        bias = 0f
                    )
                }
        )
        btnTitle?.let {
            TextButton(
                onClick = btnClick,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(btnSeeAll) {
                        linkTo(top = tvTitle.top, bottom = tvTitle.bottom)
                        end.linkTo(parent.end, PADDING_HORIZONTAL_SCREEN.dp)
                    },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    text = it,
                    style = RealEstateTypography.button.copy(
                        color = btnColor
                    )
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(listData) {
                    top.linkTo(tvTitle.bottom, MARGIN_VIEW.dp)
                },
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(MARGIN_VIEW.dp),
            contentPadding = PaddingValues(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
        ) {
            items(
                items = listRealEstate,
                key = { realEstate ->
                    realEstate.run {
                        "$id - $isSaved"
                    }
                },
            ) { realEstate ->
                ItemRealEstate(
                    item = realEstate,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ListTypes(
    modifier: Modifier = Modifier,
    types: MutableList<ItemChoose>,
    onItemClick: () -> Unit,
    paddingHorizontal: Int = PADDING_HORIZONTAL_SCREEN
) {
    val listTypeState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        state = listTypeState,
        horizontalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
        contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
    ) {
        items(
            items = types,
            key = { typeKey ->
                typeKey.toString()
            },
        ) { type ->
            ItemType(
                item = type,
                onItemClick =
                {
                    if (types.indexOf(it) != -1) {
                        val newValue = it.copy(isSelected = !it.isSelected)
                        types[types.indexOf(it)] = newValue
                        types.run {
                            sortBy { item ->
                                item.id
                            }
                            sortByDescending { item ->
                                item.isSelected
                            }
                            coroutineScope.launch {
                                delay(TWEEN_ANIMATION_TIME.toLong())
                                listTypeState.animateScrollToItem(0)
                                onItemClick()
                            }
                        }
                    }
                },
                modifier = Modifier.animateItemPlacement(
                    tween(durationMillis = TWEEN_ANIMATION_TIME)
                )
            )
        }
    }
}
 