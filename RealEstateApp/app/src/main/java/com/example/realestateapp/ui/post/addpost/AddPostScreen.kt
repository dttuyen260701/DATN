package com.example.realestateapp.ui.post.addpost

import android.Manifest
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
import com.example.realestateapp.data.enums.PostStatus
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.formatToUnit
import com.example.realestateapp.extension.makeToast
import com.example.realestateapp.extension.showFullNumber
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.ui.post.PostUiState
import com.example.realestateapp.ui.post.PostViewModel
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS_MAP
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DIRECTION
import com.example.realestateapp.util.Constants.DefaultField.FIELD_JURIDICAL
import com.example.realestateapp.util.Constants.DefaultField.FIELD_PREDICT_PRICE
import com.example.realestateapp.util.Constants.DefaultField.FIELD_STREET
import com.example.realestateapp.util.Constants.DefaultField.FIELD_TYPE
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_HINT_COLOR
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ID_POST
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MAX_IMAGE_POST
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.WARNING_TEXT_SIZE
import com.example.realestateapp.util.Constants.MessageErrorAPI.INTERNAL_SERVER_ERROR
import com.example.realestateapp.util.Constants.MessageErrorAPI.INVALID_INPUT_ERROR
import com.google.android.gms.maps.model.LatLng

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
    navigateToPickAddressMap: () -> Unit,
    navigateToMyRecord: (Boolean) -> Unit,
    navigateToRealEstateDetail: (Int) -> Unit,
    addressDetails: MutableList<String>
) {
    val context = LocalContext.current

    viewModel.run {
        val user by remember { getUser() }
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
        var priceSuggest by remember { priceSuggest }
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
        var comboOptionChosen by remember { comboOptionChosen }
        val comboOptionError by remember {
            derivedStateOf {
                if (comboOptionChosen == DEFAULT_ITEM_CHOSEN && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.choiceOptionTitle)
                    )
                } else ""
            }
        }
        val comboOptionsBronze = remember { comboOptionsBronze }
        val comboOptionsSilver = remember { comboOptionsSilver }
        val comboOptionsGold = remember { comboOptionsGold }
        val isEnableSubmit by remember {
            derivedStateOf {
                (
                        addressError.trim().isEmpty()
                                && typeError.trim().isEmpty()
                                && juridicalError.trim().isEmpty()
                                && directionError.trim().isEmpty()
                                && squareError.trim().isEmpty()
                                && floorError.trim().isEmpty()
                                && bedroomError.trim().isEmpty()
                                && streetInFrontError.trim().isEmpty()
                                && widthError.trim().isEmpty()
                                && lengthError.trim().isEmpty()
                                && titleError.trim().isEmpty()
                                && descriptionError.trim().isEmpty()
                                && priceError.trim().isEmpty()
                                && comboOptionError.trim().isEmpty()
                        )
            }
        }
        var postId by remember { postId }
        var postStatus by remember { postStatus }

        if (postId != idPost) postId = idPost

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is PostUiState.InitView -> {
                    if (postId != DEFAULT_ID_POST) {
                        getRealEstateDetail(postId)
                    } else {
                        comboOptionsBronze.clear()
                        comboOptionsSilver.clear()
                        comboOptionsGold.clear()
                        getComboOptions()
                    }
                }
                is PostUiState.GetComboOptionsDone -> {

                }
                is PostUiState.GetRealEstateDetailSuccess -> {
                    (uiState as PostUiState.GetRealEstateDetailSuccess).data.run {
                        typeChosen = ItemChoose(
                            id = propertyTypeId,
                            name = propertyTypeName
                        )
                        addressDetailDisplay = address
                        PickAddressViewModel.let {
                            it.districtChosen.value = ItemChoose(
                                id = districtId,
                                name = districtName
                            )
                            it.wardChosen.value = ItemChoose(
                                id = wardId,
                                name = wardName
                            )
                            it.streetChosen.value = ItemChoose(
                                id = streetId,
                                name = streetName
                            )
                            it.latitude = this.latitude
                            it.longitude = this.longitude
                            it.detailStreet.value = this.address.split(",")[0]
                        }
                        juridicalChosen = ItemChoose(
                            id = legalId,
                            name = legalName
                        )
                        directionChosen = ItemChoose(
                            id = directionId,
                            name = districtName
                        )
                        square = this.square.toString()
                        this.floors?.let { floor = it.toString() }
                        this.bedrooms?.let { bedroom = it.toString() }
                        this.streetInFront?.let { streetInFront = it.toString() }
                        width = this.width.toString()
                        length = this.length.toString()
                        isHaveCarParking = (carParking == true)
                        isHaveRooftop = (rooftop == true)
                        isHaveDiningRoom = (diningRoom == 1)
                        isHaveKitchenRoom = (kitchen == 1)
                        titlePost = title ?: ""
                        description = this.description ?: ""
                        images.addAll(this.images)
                        priceSuggest = (this.suggestedPrice / 1_000_000).showFullNumber()
                        price = (this.price / 1_000_000).showFullNumber()
                        comboOptionChosen = ItemChoose(
                            id = comboOptionId,
                            name = comboOptionName
                        )
                        postStatus = status
                    }

                    comboOptionsBronze.clear()
                    comboOptionsSilver.clear()
                    comboOptionsGold.clear()
                    getComboOptions()
                }
                is PostUiState.GetTypesSuccess -> {
                    types.run {
                        clear()
                        addAll((uiState as PostUiState.GetTypesSuccess).data)
                    }
                }
                is PostUiState.GetPredictPriceSuccess -> {
                    (uiState as PostUiState.GetPredictPriceSuccess).run {
                        data.toString().let {
                            priceSuggest = (it.toFloat() * square.toFloat()).toString()
                            if (isForSubmit) {
                                if (postId == DEFAULT_ID_POST) createPost()
                                else updatePost()
                            } else price = (it.toFloat() * square.toFloat()).toString()
                        }
                    }
                }
                is PostUiState.SubmitPostSuccess -> {
                    if ((uiState as PostUiState.SubmitPostSuccess).data) {
                        if (postId != DEFAULT_ID_POST) {
                            onBackClick()
                            onBackClick()
                            navigateToRealEstateDetail(postId)
                        } else navigateToMyRecord(true)
                    } else {
                        showDialog(
                            dialog = TypeDialog.ErrorDialog(
                                message = INTERNAL_SERVER_ERROR
                            )
                        )
                    }
                }
                else -> {}
            }
        }

        AddPostScreen(
            modifier = modifier,
            postStatus = postStatus,
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
                        FIELD_ADDRESS_MAP -> {
                            navigateToPickAddressMap()
                        }
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
                        FIELD_PREDICT_PRICE -> {
                            if (isValidateData()) getPredictPrice()
                            else context.makeToast(INVALID_INPUT_ERROR)
                        }
                        else -> {}
                    }
                    if (key != FIELD_ADDRESS && key != FIELD_PREDICT_PRICE && key != FIELD_ADDRESS_MAP) {
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
                    requestPermissionListener(
                        permission = mutableListOf(
                            Manifest.permission.CAMERA
                        )
                    ) { results ->
                        if (results.entries.all { it.value }) {
                            uploadImageAndGetURL(
                                onStart = {
                                    isUpLoading = true
                                }
                            ) {
                                isUpLoading = false
                                if (it.trim().isNotEmpty()) {
                                    images.add(
                                        Image(
                                            id = -1,
                                            url = it
                                        )
                                    )
                                }
                            }
                        }
                    }
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
            priceSuggest = priceSuggest,
            price = price,
            onPriceChange = remember {
                {
                    try {
                        if (it.toFloat() > 0) price = it.trim()
                    } catch (e: Exception) {
                        if (it.isEmpty()) {
                            price = it
                        }
                    }
                }
            },
            priceError = priceError,
            ownerName = user?.fullName ?: "",
            ownerPhone = user?.phoneNumber ?: "",
            comboOptionChosen = comboOptionChosen,
            comboOptionError = comboOptionError,
            comboOptionsBronze = comboOptionsBronze,
            comboOptionsSilver = comboOptionsSilver,
            comboOptionsGold = comboOptionsGold,
            onComboOptionItemClick = remember {
                {
                    if (it != comboOptionChosen) {
                        comboOptionChosen = it
                        onComboOptionChoice(it)
                    }
                }
            },
            btnSubmitTitle = stringResource(
                id = if (postId != DEFAULT_ID_POST) R.string.btnUpdateTitle
                else R.string.btnSubmitTitle
            ),
            isEnableSubmit = isEnableSubmit,
            onSubmitClick = remember {
                {
                    firstClick = false
                    if (isEnableSubmit) {
                        getPredictPrice(true)
                    }
                }
            },
        )
    }
}

@Composable
internal fun AddPostScreen(
    modifier: Modifier,
    postStatus: Int,
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
    priceSuggest: String,
    price: String,
    onPriceChange: (String) -> Unit,
    priceError: String,
    ownerName: String,
    ownerPhone: String,
    comboOptionChosen: ItemChoose,
    comboOptionError: String,
    comboOptionsBronze: MutableList<ItemChoose>,
    comboOptionsSilver: MutableList<ItemChoose>,
    comboOptionsGold: MutableList<ItemChoose>,
    onComboOptionItemClick: (ItemChoose) -> Unit,
    btnSubmitTitle: String,
    isEnableSubmit: Boolean,
    onSubmitClick: () -> Unit
) {
    val isReadOnlyInformation = (postStatus == PostStatus.Accepted.id)
    BaseScreen(
        modifier = modifier,
        bgColor = RealEstateAppTheme.colors.bgScrPrimaryLight,
        verticalArrangement = Arrangement.Top,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.addPostTitle),
                rightIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Reset),
                onRightIconClick = if (isReadOnlyInformation) { -> } else onResetData,
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
                    title = btnSubmitTitle,
                    enabled = isEnableSubmit,
                    bgColor = RealEstateAppTheme.colors.bgButtonGradient,
                    modifier = Modifier
                        .height(TOOLBAR_HEIGHT.dp)
                        .fillMaxWidth()
                )
            }
        }
    ) {
        Spacing(MARGIN_DIFFERENT_VIEW)
        TextNotification(
            title = stringResource(id = R.string.warningTitle),
            message = stringResource(id = R.string.messageTitle)
        )
        Spacing(MARGIN_VIEW)
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
            errorText = typeError,
            readOnly = isReadOnlyInformation
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
            errorText = addressError,
            readOnly = isReadOnlyInformation
        )
        PickAddressViewModel.run {
            if (latitude != 0.0 && longitude != 0.0) {
                Spacing(MARGIN_VIEW)
                Text(
                    text = stringResource(id = R.string.mapTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
                )
                MapviewShowMarker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp),
                    location = LatLng(latitude, longitude)
                )
            }
        }
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_ADDRESS_MAP) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Location),
            title = "",
            value = addressDetail,
            hint = stringResource(id = R.string.addressMapTitle),
            onClearData = { onClearData(FIELD_ADDRESS_MAP) },
            errorText = "",
            readOnly = isReadOnlyInformation
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
            errorText = juridicalError,
            readOnly = isReadOnlyInformation
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
            errorText = directionError,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
            isLastEditText = true,
            readOnly = isReadOnlyInformation
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
                readOnly = isReadOnlyInformation
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Rooftop),
                title = stringResource(id = R.string.rooftopTitle),
                isChecked = isHaveRooftop,
                onCheckedChange = onHaveRooftopChange,
                readOnly = isReadOnlyInformation
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
                readOnly = isReadOnlyInformation
            )
            Spacer(modifier = Modifier.weight(1f))
            CheckBoxWIconText(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Kitchen),
                title = stringResource(id = R.string.kitchenTitle),
                isChecked = isHaveKitchenRoom,
                onCheckedChange = onHaveKitchenChange,
                readOnly = isReadOnlyInformation
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
                            .size(ICON_ITEM_SIZE.dp)
                            .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                            .background(
                                color = Color.Transparent
                            )
                    ) {
                        BaseIcon(
                            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                            modifier = Modifier
                                .size(TRAILING_ICON_SIZE.dp),
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
                    shape = RoundedCornerShape(ROUND_RECTANGLE.dp),
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
        ComboBox(
            onItemClick = { onComboBoxClick(FIELD_PREDICT_PRICE) },
            leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Money),
            title = stringResource(id = R.string.priceSuggestTitle),
            value = priceSuggest,
            hint = stringResource(id = R.string.priceSuggestTitle),
            isAllowClearData = false,
            onClearData = { },
            errorText = typeError
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
            text = stringResource(id = R.string.contactInfoTitle),
            style = RealEstateTypography.body1.copy(
                fontSize = 17.sp,
                color = RealEstateAppTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        TextIcon(
            text = ownerName,
            icon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
            textColor = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
            size = 14,
            iconTint = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
            modifier = Modifier
                .fillMaxWidth()
        )
        TextIcon(
            text = ownerPhone,
            icon = AppIcon.ImageVectorIcon(RealEstateIcon.PhoneOutLine),
            textColor = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
            size = 14,
            iconTint = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacing(MARGIN_VIEW)
        BorderLine()
        Spacing(MARGIN_VIEW)
        Text(
            text = stringResource(id = R.string.choiceValueTitle),
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(MARGIN_VIEW.dp))
                Text(
                    text = stringResource(id = R.string.bronzeOptionTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 15.sp,
                        color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.weight(1f)
                )
                SwitchButton(
                    data = comboOptionsBronze,
                    onItemClick = onComboOptionItemClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(MARGIN_VIEW.dp))
                Text(
                    text = stringResource(id = R.string.silverOptionTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 15.sp,
                        color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.weight(1f)
                )
                SwitchButton(
                    data = comboOptionsSilver,
                    onItemClick = onComboOptionItemClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(MARGIN_VIEW.dp))
                Text(
                    text = stringResource(id = R.string.goldOptionTitle),
                    style = RealEstateTypography.body1.copy(
                        fontSize = 15.sp,
                        color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.weight(1f)
                )
                SwitchButton(
                    data = comboOptionsGold,
                    onItemClick = onComboOptionItemClick
                )
            }
            Spacing(MARGIN_VIEW)
            Text(
                text = stringResource(
                    id = R.string.feeTitle,
                    comboOptionChosen.score.formatToUnit()
                ),
                style = RealEstateTypography.body1.copy(
                    fontSize = 15.sp,
                    color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PADDING_HORIZONTAL_SCREEN.dp)
            )
            Spacing(PADDING_VIEW)
            Text(
                text = comboOptionError,
                style = RealEstateTypography.caption.copy(
                    color = Color.Red,
                    fontSize = WARNING_TEXT_SIZE.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = MARGIN_VIEW.dp)
            )
        }
        Spacing(MARGIN_VIEW)
        BorderLine()
        Spacing(MARGIN_DIFFERENT_VIEW)
    }
}
