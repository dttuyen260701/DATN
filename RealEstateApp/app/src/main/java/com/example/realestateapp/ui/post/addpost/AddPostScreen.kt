package com.example.realestateapp.ui.post.addpost

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.ui.post.PostUiState
import com.example.realestateapp.ui.post.PostViewModel
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET
import com.example.realestateapp.util.Constants.DefaultField.FIELD_TYPE
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_POST
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN

/**
 * Created by tuyen.dang on 5/28/2023.
 */

@Composable
internal fun AddPostRoute(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    idPost: Int,
    onBackClick: () -> Unit,
    navigateToPickAddress: () -> Unit,
    addressDetails: MutableList<String>
) {
    val context = LocalContext.current

    viewModel.run {
        val uiState by remember { uiState }
        var firstClick by remember { firstClick }
        var addressDetailDisplay by remember { detailAddress }
        val addressDetailsScr = remember { addressDetails }
        val addressError by remember {
            derivedStateOf {
                if (addressDetailDisplay.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.addressTitle)
                    )
                else ""
            }
        }
        var typeChosen by remember { typeChosen }
        val types = remember { typesData }
        val typeError by remember {
            derivedStateOf {
                if (typeChosen == DEFAULT_ITEM_CHOSEN && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.typesTitle)
                    )
                } else ""
            }
        }
        var square by remember { square }
        val squareError by remember {
            derivedStateOf {
                if (square.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.squareTitle)
                    )
                else ""
            }
        }
        var price by remember { price }
        val priceError by remember {
            derivedStateOf {
                if (price.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.priceTitle)
                    )
                else ""
            }
        }
        var floor by remember { floor }
        val floorError by remember {
            derivedStateOf {
                if (floor.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.floorTitle)
                    )
                else ""
            }
        }
        var juridicalChosen by remember { juridicalChosen }

        if (addressDetailsScr[0].isNotEmpty()) {
            addressDetailDisplay = addressDetailsScr[0]
        }


        when (uiState) {
            is PostUiState.GetTypesSuccess -> {
                types.run {
                    clear()
                    addAll((uiState as PostUiState.GetTypesSuccess).data)
                }
            }
            else -> {}
        }

        if (idPost != DEFAULT_ID_POST) {
            Log.e("TTT", "AddPostRoute: $idPost")
        }

        AddPostScreen(
            modifier = modifier,
            onResetData = remember { ::resetData },
            onBackClick = onBackClick,
            addressDetail = addressDetailDisplay,
            addressError = addressError,
            onComboBoxClick = remember {
                { key ->
                    var title = ""
                    var data = mutableListOf<ItemChoose>()
                    var loadData: (String, () -> Unit) -> Unit = { _, _ -> }
                    var onItemClick: (ItemChoose) -> Unit = { _ -> }
                    when (key) {
                        FIELD_ADDRESS -> {
                            if (addressDetailDisplay.isBlank() || addressDetailDisplay.isEmpty()) {
                                PickAddressViewModel.clearDataChosen()
                            }
                            navigateToPickAddress()
                        }
                        FIELD_TYPE -> {
                            types.clear()
                            title = context.getString(R.string.typesTitle)
                            data = typesData
                            loadData = { _, onDone ->
                                getTypes(onDone = onDone)
                            }
                            onItemClick = {
                                typeChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        else -> {}
                    }
                    if (key != FIELD_ADDRESS) {
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
            onClearData = remember {
                {
                    when (it) {
                        FIELD_ADDRESS -> {
                            PickAddressViewModel.clearDataChosen()
                            addressDetailsScr[0] = ""
                            addressDetailDisplay = ""
                        }
                        FIELD_TYPE -> {
                            typeChosen = DEFAULT_ITEM_CHOSEN
                        }
                        else -> {}
                    }
                }
            },
            typeChosen = typeChosen,
            typeError = typeError,
            square = square,
            onSquareChange = remember {
                {
                    try {
                        if (it.toInt() > 0) square = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            square = it
                        }
                    }
                }
            },
            squareError = squareError,
            price = price,
            onPriceChange = remember {
                {
                    price = it
                }
            },
            priceError = priceError,
            floor = floor,
            onFloorChange = remember {
                {
                    floor = it
                }
            },
            floorError = floorError,
            juridicalChosen = juridicalChosen,
            onSubmitClick = remember {
                {
                    firstClick = false
                }
            }
        )
    }
}

@Composable
internal fun AddPostScreen(
    modifier: Modifier,
    onResetData: () -> Unit,
    onBackClick: () -> Unit,
    addressDetail: String,
    addressError: String,
    onComboBoxClick: (String) -> Unit,
    onClearData: (String) -> Unit,
    typeChosen: ItemChoose,
    typeError: String,
    square: String,
    onSquareChange: (String) -> Unit,
    squareError: String,
    price: String,
    onPriceChange: (String) -> Unit,
    priceError: String,
    floor: String,
    onFloorChange: (String) -> Unit,
    floorError: String,
    juridicalChosen: ItemChoose,
    onSubmitClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier
            .padding(vertical = PADDING_HORIZONTAL_SCREEN.dp),
        verticalArrangement = Arrangement.Top,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.addPostTitle),
                rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Reset),
                onRightIconClick = onResetData,
                leftIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.BackArrow),
                onLeftIconClick = onBackClick
            )
        },
        footer = {
            BorderLine()
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = PADDING_HORIZONTAL_SCREEN.dp,
                        vertical = MARGIN_VIEW.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                ButtonRadiusGradient(
                    onClick = onSubmitClick,
                    title = stringResource(id = R.string.searchBtnTitle),
                    bgColor = RealEstateAppTheme.colors.bgButtonGradient,
                    modifier = Modifier
                        .height(Constants.DefaultValue.TOOLBAR_HEIGHT.dp)
                        .fillMaxWidth()
                )
            }
        }
    ) {
        Text(
            text = stringResource(id = R.string.basicInfoTitle),
            style = RealEstateTypography.body1.copy(
                fontSize = 17.sp,
                color = RealEstateAppTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacing(MARGIN_VIEW)
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_TYPE) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.RealEstateType),
            title = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(
                    id = R.string.typesTitle
                )
            ),
            value = typeChosen.name,
            hint = stringResource(
                id = R.string.chooseHint,
                stringResource(id = R.string.typesTitle)
            ),
            onClearData = { onClearData(FIELD_TYPE) },
            errorText = typeError
        )
        Spacing(MARGIN_VIEW)
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_ADDRESS) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Location),
            title = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(
                    id = R.string.addressTitle
                )
            ),
            value = addressDetail,
            hint = stringResource(id = R.string.addressHint),
            onClearData = { onClearData(FIELD_ADDRESS) },
            errorText = addressError
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onPriceChange,
            text = price,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(id = R.string.priceWUnitTitle)
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.priceWUnitTitle)
            ),
            errorText = priceError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Money),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        BorderLine()
        Spacing(MARGIN_VIEW)
        Text(
            text = stringResource(id = R.string.detailInfoTitle),
            style = RealEstateTypography.body1.copy(
                fontSize = 17.sp,
                color = RealEstateAppTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onSquareChange,
            text = square,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(id = R.string.squareWUnitTitle)
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.squareWUnitTitle)
            ),
            errorText = squareError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Square),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onFloorChange,
            text = floor,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(id = R.string.floorTitle)
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.floorTitle)
            ),
            errorText = floorError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Floors),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        ComboBox(
            onItemClick = { onComboBoxClick(Constants.DefaultField.FIELD_JURIDICAL) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Legal),
            title = stringResource(id = R.string.juridicalTitle),
            value = juridicalChosen.name,
            hint = stringResource(
                id = R.string.chooseHint,
                stringResource(id = R.string.juridicalTitle)
            ),
            onClearData = { onClearData(Constants.DefaultField.FIELD_JURIDICAL) }
        )
    }
}
