package com.example.realestateapp.ui.setting.profile

import android.Manifest
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestateapp.R
import com.example.realestateapp.data.enums.GenderOption
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.User
import com.example.realestateapp.designsystem.components.*
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.setVisibility
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.ui.base.BaseScreen
import com.example.realestateapp.ui.base.TypeDialog
import com.example.realestateapp.ui.pickaddress.PickAddressViewModel
import com.example.realestateapp.util.Constants.DefaultField.FIELD_ADDRESS
import com.example.realestateapp.util.Constants.DefaultField.FIELD_DATE
import com.example.realestateapp.util.Constants.DefaultField.FIELD_GENDER
import com.example.realestateapp.util.Constants.DefaultValue.DEFAULT_ITEM_CHOSEN
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_HORIZONTAL_SCREEN
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/11/2023.
 */

@Composable
internal fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToPickAddress: () -> Unit,
    addressDetails: MutableList<String>
) {
    val context = LocalContext.current

    viewModel.run {
        var user by remember { getUser() }
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
        var imgUrl by remember { imgUrl }
        var dateChosen by remember { dateChosen }
        var genderChosen by remember { genderChosen }
        val genderError by remember {
            derivedStateOf {
                if (genderChosen == DEFAULT_ITEM_CHOSEN && !firstClick) {
                    context.getString(
                        R.string.mandatoryError,
                        context.getString(R.string.genderTitle)
                    )
                } else ""
            }
        }
        var name by remember { name }
        val nameError by remember {
            derivedStateOf {
                if (name.isEmpty() && !firstClick)
                    context.getString(R.string.nameError)
                else ""
            }
        }
        var isUploading by remember { mutableStateOf(false) }

        if (addressDetailsScr[0].isNotEmpty()) {
            addressDetailDisplay = addressDetailsScr[0]
        }

        val isEnableSubmit by remember {
            derivedStateOf {
                nameError.isEmpty() && genderError.isEmpty() && addressError.isEmpty()
            }
        }

        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is ProfileUiState.InitView -> {
                    getIsLoading().value = true
                    getInformationUser()
                }
                is ProfileUiState.GetInformationUserSuccess -> {
                    user = (uiState as ProfileUiState.GetInformationUserSuccess).data
                    user?.let { u ->
                        imgUrl = u.imgUrl ?: ""
                        name = u.fullName
                        dateChosen = u.dateOfBirth
                        addressDetailDisplay = u.addressDetail ?: ""
                        genderChosen =
                            if (u.gender == "1") GenderOption.FEMALE.value else GenderOption.MALE.value
                        PickAddressViewModel.run {
                            districtChosen.value = ItemChoose(
                                id = u.districtId?.toInt() ?: -1,
                                name = u.districtName ?: ""
                            )
                            wardChosen.value = ItemChoose(
                                id = u.wardId?.toInt() ?: -1,
                                name = u.wardName ?: ""
                            )
                        }
                    }
                }
                is ProfileUiState.UpdateInformationUserSuccess -> {
                    onBackClick()
                }
                else -> {}
            }
        }

        user?.let { value ->
            ProfileScreen(
                modifier = modifier,
                onBackClick = remember { onBackClick },
                isUploading = isUploading,
                user = value,
                imgUrl = imgUrl,
                onImageUserClick = remember {
                    {
                        requestPermissionListener(
                            permission = mutableListOf(
                                Manifest.permission.CAMERA
                            )
                        ) { results ->
                            if (results.entries.all { it.value }) {
                                uploadImageAndGetURL(
                                    onStart = {
                                        isUploading = true
                                    }
                                ) {
                                    isUploading = false
                                    if (it.trim().isNotEmpty()) {
                                        imgUrl = it
                                    }
                                }
                            }
                        }
                    }
                },
                name = name,
                onNameChange = remember {
                    {
                        name = it
                    }
                },
                nameError = nameError,
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
                            FIELD_DATE -> {
                                val dateCurrent = try {
                                    value.dateOfBirth.split("/").map {
                                        it.toInt()
                                    }
                                } catch (e: Exception) {
                                    mutableListOf()
                                }
                                val isValid = dateCurrent.size == 3
                                val mDatePickerDialog = DatePickerDialog(
                                    context,
                                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                        dateChosen = "$mDayOfMonth/${mMonth + 1}/$mYear"
                                    },
                                    if (isValid) dateCurrent[2] else 2023,
                                    if (isValid) dateCurrent[1] else 7,
                                    if (isValid) dateCurrent[0] else 1
                                )
                                mDatePickerDialog.show()
                            }
                            FIELD_GENDER -> {
                                genderOptions.clear()
                                title = context.getString(R.string.genderTitle)
                                data = genderOptions
                                loadData = { _, onDone ->
                                    getDataChoice(
                                        key = key,
                                        onDone = onDone
                                    )
                                }
                                onItemClick = {
                                    genderChosen = it
                                    showDialog(dialog = TypeDialog.Hide)
                                }
                            }
                            else -> {}
                        }
                        if (key != FIELD_ADDRESS && key != FIELD_DATE) {
                            showDialog(
                                dialog = TypeDialog.ChoiceDataDialog(
                                    title = title,
                                    data = data,
                                    loadData = loadData,
                                    onItemClick = onItemClick,
                                    isEnableSearchFromApi = false
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
                            FIELD_GENDER -> {
                                genderChosen = DEFAULT_ITEM_CHOSEN
                            }
                            else -> {}
                        }
                    }
                },
                dateChosen = dateChosen,
                genderChosen = genderChosen,
                genderError = genderError,
                addressDetail = addressDetailDisplay,
                addressError = addressError,
                isEnableSubmit = isEnableSubmit,
                onSubmitClick = remember {
                    {
                        firstClick = false
                        if (isEnableSubmit) {
                            updateInformationUser()
                        }
                    }
                }
            )
        }
    }
}

@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    isUploading: Boolean,
    user: User,
    imgUrl: String,
    onImageUserClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String,
    onComboBoxClick: (String) -> Unit,
    onClearData: (String) -> Unit,
    dateChosen: String,
    genderChosen: ItemChoose,
    genderError: String,
    addressDetail: String,
    addressError: String,
    isEnableSubmit: Boolean,
    onSubmitClick: () -> Unit
) {
    BaseScreen(
        modifier = modifier,
        toolbar = {
            ToolbarView(
                title = stringResource(id = R.string.settingUserTitle),
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
                    title = stringResource(id = R.string.updateTitle),
                    enabled = isEnableSubmit,
                    bgColor = RealEstateAppTheme.colors.bgButtonGradient,
                    modifier = Modifier
                        .height(TOOLBAR_HEIGHT.dp)
                        .fillMaxWidth()
                )
            }
        },
        verticalArrangement = Arrangement.Top
    ) {
        user.run {
            Spacing(MARGIN_DIFFERENT_VIEW)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (imgUser, icAdd, tvEmail, progressBar) = createRefs()
                ImageProfile(
                    size = TOOLBAR_HEIGHT * 2,
                    model = imgUrl,
                    modifier = Modifier
                        .constrainAs(imgUser) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                            )
                            linkTo(
                                top = parent.top,
                                bottom = tvEmail.top,
                                bottomMargin = MARGIN_VIEW.dp
                            )
                        }
                        .clickable {
                            onImageUserClick()
                        }
                )
                ButtonUnRepeating(onImageUserClick) {
                    IconButton(
                        onClick = if (isUploading) { -> } else it,
                        modifier = Modifier
                            .size(25.dp)
                            .padding(2.dp)
                            .aspectRatio(1f, false)
                            .constrainAs(icAdd) {
                                end.linkTo(imgUser.end)
                                bottom.linkTo(imgUser.bottom)
                            }
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .border(
                                BorderStroke(1.dp, RealEstateAppTheme.colors.primary),
                                CircleShape
                            ),
                    ) {
                        BaseIcon(
                            icon = AppIcon.ImageVectorIcon(RealEstateIcon.Add),
                            contentDescription = null,
                            tint = RealEstateAppTheme.colors.primary
                        )
                    }
                }
                Text(
                    text = user.email,
                    style = RealEstateTypography.h2.copy(
                        color = RealEstateAppTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .constrainAs(tvEmail) {
                            linkTo(imgUser.start, imgUser.end)
                            linkTo(
                                top = imgUser.top,
                                bottom = parent.bottom,
                                bottomMargin = 2.dp,
                                bias = 1F
                            )
                        }
                )
                CircularProgressIndicator(
                    color = RealEstateAppTheme.colors.progressBar,
                    modifier = Modifier
                        .constrainAs(progressBar) {
                            linkTo(imgUser.top, imgUser.bottom)
                            linkTo(imgUser.start, imgUser.end)
                            visibility = setVisibility(isUploading)
                        }
                )

            }
            Spacing(PADDING_VIEW)
            BorderLine()
            Spacing(MARGIN_VIEW)
            EditTextTrailingIconCustom(
                onTextChange = onNameChange,
                text = name,
                label = stringResource(id = R.string.nameTitle),
                typeInput = KeyboardType.Text,
                hint = stringResource(
                    id = R.string.hintTitle,
                    stringResource(id = R.string.nameTitle)
                ),
                errorText = nameError,
                trailingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.User),
                textColor = RealEstateAppTheme.colors.primary,
                backgroundColor = Color.White,
                isLastEditText = true,
            )
            Spacing(MARGIN_VIEW)
            ComboBox(
                onItemClick = { onComboBoxClick(FIELD_DATE) },
                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Calender),
                title = stringResource(id = R.string.birthDayTitle),
                value = dateChosen,
                hint = stringResource(
                    id = R.string.chooseHint,
                    stringResource(id = R.string.birthDayTitle)
                ),
                isAllowClearData = false,
                onClearData = { }
            )
            Spacing(MARGIN_VIEW)
            ComboBox(
                onItemClick = { onComboBoxClick(FIELD_GENDER) },
                leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Compass),
                title = stringResource(id = R.string.genderTitle),
                value = genderChosen.name,
                hint = stringResource(
                    id = R.string.chooseHint,
                    stringResource(id = R.string.genderTitle)
                ),
                onClearData = { onClearData(FIELD_GENDER) },
                errorText = genderError
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
                onTextChange = { },
                text = phoneNumber,
                label = stringResource(id = R.string.phoneTitle),
                typeInput = KeyboardType.Number,
                trailingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.PhoneOutLine),
                textColor = RealEstateAppTheme.colors.primary,
                backgroundColor = Color.White,
                readOnly = true
            )
        }
    }
}
