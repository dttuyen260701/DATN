package com.example.realestateapp.ui.post.addpost

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.ui.post.PostUiState
import com.example.realestateapp.ui.post.PostViewModel
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET
import com.example.realestateapp.util.Constants.DefaultField.FIELD_TYPE
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_HINT_COLOR
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_POST
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MAX_IMAGE_POST
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.WARNING_TEXT_SIZE

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
        var juridicalChosen by remember { juridicalChosen }
        val juridicalError by remember {
            derivedStateOf {
                if (juridicalChosen == DEFAULT_ITEM_CHOSEN && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.juridicalTitle)
                    )
                } else ""
            }
        }
        var directionChosen by remember { directionChosen }
        val directionError by remember {
            derivedStateOf {
                if (directionChosen == DEFAULT_ITEM_CHOSEN && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.directionTitle)
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
        var bedroom by remember { bedroom }
        val bedroomError by remember {
            derivedStateOf {
                if (bedroom.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.bedroomTitle)
                    )
                else ""
            }
        }
        var streetInFront by remember { streetInFront }
        val streetInFrontError by remember {
            derivedStateOf {
                if (streetInFront.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.streetOfFrontTitle)
                    )
                else ""
            }
        }
        var width by remember { width }
        val widthError by remember {
            derivedStateOf {
                if (width.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.widthTitle)
                    )
                else ""
            }
        }
        var length by remember { length }
        val lengthError by remember {
            derivedStateOf {
                if (length.isEmpty() && !firstClick)
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.lengthTitle)
                    )
                else ""
            }
        }
        var isHaveCarParking by remember { isHaveCarParking }
        var isHaveRooftop by remember { isHaveRooftop }
        var isHaveKitchenRoom by remember { isHaveKitchenRoom }
        var isHaveDiningRoom by remember { isHaveDiningRoom }
        var titlePost by remember { title }
        val titleError by remember {
            derivedStateOf {
                if (titlePost.isEmpty() && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.title)
                    )
                } else ""
            }
        }
        var description by remember { description }
        val descriptionError by remember {
            derivedStateOf {
                if (description.isEmpty() && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.title)
                    )
                } else ""
            }
        }
        var isUpLoading by remember { isUpLoading }
        val images = remember { images }

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
                        FIELD_JURIDICAL -> {
                            juridicalOptions.clear()
                            title = context.getString(R.string.juridicalTitle)
                            data = juridicalOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                juridicalChosen = it
                                showDialog(dialog = TypeDialog.Hide)
                            }
                        }
                        FIELD_DIRECTION -> {
                            directionOptions.clear()
                            title = context.getString(R.string.directionTitle)
                            data = directionOptions
                            loadData = { _, onDone ->
                                getDataChoice(key, onDone)
                            }
                            onItemClick = {
                                directionChosen = it
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
                        FIELD_JURIDICAL -> {
                            juridicalChosen = DEFAULT_ITEM_CHOSEN
                        }
                        FIELD_DIRECTION -> {
                            directionChosen = DEFAULT_ITEM_CHOSEN
                        }
                        else -> {}
                    }
                }
            },
            typeChosen = typeChosen,
            typeError = typeError,
            juridicalChosen = juridicalChosen,
            juridicalError = juridicalError,
            directionChosen = directionChosen,
            directionError = directionError,
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
                    try {
                        if (it.toInt() > 0) price = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            price = it
                        }
                    }
                }
            },
            priceError = priceError,
            floor = floor,
            onFloorChange = remember {
                {
                    try {
                        if (it.toInt() > 0) floor = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            floor = it
                        }
                    }
                }
            },
            floorError = floorError,
            bedroom = bedroom,
            onBedroomChange = remember {
                {
                    try {
                        if (it.toInt() > 0) bedroom = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            bedroom = it
                        }
                    }
                }
            },
            bedroomError = bedroomError,
            streetInFront = streetInFront,
            onStreetInFrontChange = remember {
                {
                    try {
                        if (it.toFloat() > 0) streetInFront = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            streetInFront = it
                        }
                    }
                }
            },
            streetInFrontError = streetInFrontError,
            width = width,
            onWidthChange = remember {
                {
                    try {
                        if (it.toFloat() > 0) width = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            width = it
                        }
                    }
                }
            },
            widthError = widthError,
            length = length,
            onLengthChange = remember {
                {
                    try {
                        if (it.toFloat() > 0) length = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            length = it
                        }
                    }
                }
            },
            lengthError = lengthError,
            isHaveCarParking = isHaveCarParking,
            onHaveCarChange = remember {
                {
                    isHaveCarParking = it
                }
            },
            isHaveRooftop = isHaveRooftop,
            onHaveRooftopChange = remember {
                {
                    isHaveRooftop = it
                }
            },
            isHaveKitchenRoom = isHaveKitchenRoom,
            onHaveKitchenChange = remember {
                {
                    isHaveKitchenRoom = it
                }
            },
            isHaveDiningRoom = isHaveDiningRoom,
            onHaveDiningRoomChange = remember {
                {
                    isHaveDiningRoom = it
                }
            },
            title = titlePost,
            onTitleChange = remember {
                {
                    if (it.length <= 100)
                        titlePost = it
                }
            },
            titleError = titleError,
            onClearTitle = remember {
                {
                    titlePost = ""
                }
            },
            description = description,
            onDescriptionChange = remember {
                {
                    if (it.length <= 500)
                        description = it
                }
            },
            descriptionError = descriptionError,
            onClearDescription = remember {
                {
                    description = ""
                }
            },
            isUpLoading = isUpLoading,
            images = images,
            addImageClick = remember {
                {
                }
            },
            onImageClick = remember {
                {
                    showDialog(
                        dialog = TypeDialog.ShowImageDialog(
                            data = images,
                            currentIndex = it
                        )
                    )
                }
            },
            deleteImage = remember {
                {
                    images.remove(it)
                }
            },
            onSubmitClick = remember {
                {
                    firstClick = false
                }
            },
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
    juridicalChosen: ItemChoose,
    juridicalError: String,
    directionChosen: ItemChoose,
    directionError: String,
    square: String,
    onSquareChange: (String) -> Unit,
    squareError: String,
    price: String,
    onPriceChange: (String) -> Unit,
    priceError: String,
    floor: String,
    onFloorChange: (String) -> Unit,
    floorError: String,
    bedroom: String,
    onBedroomChange: (String) -> Unit,
    bedroomError: String,
    streetInFront: String,
    onStreetInFrontChange: (String) -> Unit,
    streetInFrontError: String,
    width: String,
    onWidthChange: (String) -> Unit,
    widthError: String,
    length: String,
    onLengthChange: (String) -> Unit,
    lengthError: String,
    isHaveCarParking: Boolean,
    onHaveCarChange: (Boolean) -> Unit,
    isHaveRooftop: Boolean,
    onHaveRooftopChange: (Boolean) -> Unit,
    isHaveKitchenRoom: Boolean,
    onHaveKitchenChange: (Boolean) -> Unit,
    isHaveDiningRoom: Boolean,
    onHaveDiningRoomChange: (Boolean) -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    titleError: String,
    onClearTitle: () -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    descriptionError: String,
    onClearDescription: () -> Unit,
    isUpLoading: Boolean,
    images: MutableList<Image>,
    addImageClick: () -> Unit,
    onImageClick: (Int) -> Unit,
    deleteImage: (Image) -> Unit,
    onSubmitClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
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
        Spacing(MARGIN_DIFFERENT_VIEW)
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
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_JURIDICAL) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Legal),
            title = stringResource(id = R.string.juridicalTitle),
            value = juridicalChosen.name,
            hint = stringResource(
                id = R.string.chooseHint,
                stringResource(id = R.string.juridicalTitle)
            ),
            onClearData = { onClearData(FIELD_JURIDICAL) },
            errorText = juridicalError
        )
        Spacing(MARGIN_VIEW)
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_DIRECTION) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Compass),
            title = stringResource(id = R.string.directionTitle),
            value = directionChosen.name,
            hint = stringResource(
                id = R.string.chooseHint,
                stringResource(id = R.string.directionTitle)
            ),
            onClearData = { onClearData(FIELD_DIRECTION) },
            errorText = directionError
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
        EditTextTrailingIconCustom(
            onTextChange = onBedroomChange,
            text = bedroom,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(id = R.string.bedroomTitle)
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(id = R.string.bedroomTitle)
            ),
            errorText = bedroomError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Bed),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onStreetInFrontChange,
            text = streetInFront,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.streetOfFrontTitle)
                )
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.streetOfFrontTitle)
                )
            ),
            errorText = streetInFrontError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.StreetInFront),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onWidthChange,
            text = width,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.widthTitle)
                )
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.widthTitle)
                )
            ),
            errorText = widthError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Width),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        EditTextTrailingIconCustom(
            onTextChange = onLengthChange,
            text = length,
            label = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.lengthTitle)
                )
            ),
            typeInput = KeyboardType.Number,
            hint = stringResource(
                id = R.string.hintTitle,
                stringResource(
                    id = R.string.unitMBracketsTitle,
                    stringResource(id = R.string.lengthTitle)
                )
            ),
            errorText = lengthError,
            trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Length),
            textColor = RealEstateAppTheme.colors.primary,
            backgroundColor = Color.White,
            isShowErrorStart = true,
            isLastEditText = true
        )
        Spacing(MARGIN_VIEW)
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.CarParking),
                title = stringResource(id = R.string.carParkingTitle),
                isChecked = isHaveCarParking,
                onCheckedChange = onHaveCarChange,
            )
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Rooftop),
                title = stringResource(id = R.string.rooftopTitle),
                isChecked = isHaveRooftop,
                onCheckedChange = onHaveRooftopChange,
            )
        }
        Spacing(MARGIN_VIEW)
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.DiningRoom),
                title = stringResource(id = R.string.diningRoomTitle),
                isChecked = isHaveDiningRoom,
                onCheckedChange = onHaveDiningRoomChange,
            )
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Kitchen),
                title = stringResource(id = R.string.kitchenTitle),
                isChecked = isHaveKitchenRoom,
                onCheckedChange = onHaveKitchenChange,
            )
        }
        Spacing(MARGIN_VIEW)
        BorderLine()
        Spacing(MARGIN_VIEW)
        Text(
            text = stringResource(id = R.string.generalInfoTitle),
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditTextTrailingIconCustom(
                onTextChange = onTitleChange,
                text = title,
                label = stringResource(
                    id = R.string.mandatoryTitle,
                    stringResource(id = R.string.title)
                ),
                typeInput = KeyboardType.Text,
                hint = stringResource(
                    id = R.string.hintTitle,
                    stringResource(id = R.string.title)
                ),
                errorText = titleError,
                trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Title),
                textColor = RealEstateAppTheme.colors.primary,
                backgroundColor = Color.White,
                isShowErrorStart = true,
                isLastEditText = true,
                modifier = Modifier
                    .weight(1f)
            )
            if (title.isNotEmpty()) {
                ButtonUnRepeating(onClearTitle) {
                    IconButton(
                        onClick = it,
                        modifier = Modifier
                            .size(Constants.DefaultValue.ICON_ITEM_SIZE.dp)
                            .clip(RoundedCornerShape(Constants.DefaultValue.ROUND_DIALOG.dp))
                            .background(
                                color = Color.Transparent
                            )
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                            modifier = Modifier
                                .size(Constants.DefaultValue.TRAILING_ICON_SIZE.dp),
                            contentDescription = null,
                            tint = RealEstateAppTheme.colors.primary
                        )
                    }
                }
            }
        }
        Spacing(MARGIN_VIEW)
        TextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = stringResource(
                        id = R.string.mandatoryTitle,
                        stringResource(id = R.string.descriptionTitle)
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.limit300CharTitle,
                        stringResource(id = R.string.descriptionTitle)
                    ),
                    color = RealEstateAppTheme.colors.primary.copy(ALPHA_HINT_COLOR),
                    style = RealEstateTypography.body1.copy(
                        textAlign = TextAlign.Start
                    ),
                )
            },
            trailingIcon = {
                if (description.isNotEmpty()) {
                    IconButton(
                        onClick = onClearDescription,
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            },
            textStyle = RealEstateTypography.body1.copy(
                textAlign = TextAlign.Start,
                color = RealEstateAppTheme.colors.primary
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .heightIn(
                    min = (LocalConfiguration.current.screenHeightDp * 0.2f).dp,
                    max = (LocalConfiguration.current.screenHeightDp * 0.5f).dp
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = RealEstateAppTheme.colors.primary
                ),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = RealEstateAppTheme.colors.primary,
                backgroundColor = RealEstateAppTheme.colors.bgTextField,
                cursorColor = RealEstateAppTheme.colors.primary,
                leadingIconColor = RealEstateAppTheme.colors.primary,
                trailingIconColor = RealEstateAppTheme.colors.primary,
                focusedLabelColor = RealEstateAppTheme.colors.primary,
                unfocusedLabelColor = RealEstateAppTheme.colors.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorCursorColor = RealEstateAppTheme.colors.primary,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
        Spacing(PADDING_VIEW)
        Text(
            text = descriptionError,
            style = RealEstateTypography.caption.copy(
                color = Color.Red,
                fontSize = WARNING_TEXT_SIZE.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = MARGIN_VIEW.dp)
        )
        Spacing(MARGIN_VIEW)
        ItemUploadPhoto(
            isUpLoading = isUpLoading,
            title = stringResource(
                id = R.string.mandatoryTitle,
                stringResource(id = R.string.uploadImageTitle, MAX_IMAGE_POST)
            ),
            maxSize = MAX_IMAGE_POST,
            data = images,
            onAddImageClick = addImageClick,
            onItemClick = onImageClick,
            onItemDelete = deleteImage
        )
        Spacing(MARGIN_VIEW)
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
